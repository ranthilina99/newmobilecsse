import React, {Component} from 'react';
import axios from 'axios';
import {Card, Col, Row} from "react-bootstrap";
import swat from "sweetalert2";


const SubmissionAlert1 = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Order Accepted!',
        showConfirmButton: false,
        timer: 3000
    });
}

const SubmissionAlert2 = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Order Declined!',
        showConfirmButton: false,
        timer: 3000
    });
}

const SubmissionFail = (message) => {
    swat.fire({
        icon: 'error',
        title: 'Oops...',
        text: message
    })
}

class viewOrderItems extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orderItems: [],
            itemsOfOrder: [],
            temp:'',
            name:'',
            total:'',
            supId:'',
            supplierEmail:''
        }
    }

    async componentDidMount() {
        await axios.get(`http://localhost:8080/api/getItemsByOrder/${this.props.match.params.id}`)
            .then(response => {
                this.setState({orderItems: response.data});
            })

        await this.getNames();

        await axios.get(`http://localhost:8080/api/order/${this.props.match.params.id}`)
            .then(response => {
                this.setState({
                    temp: response.data.status,
                    name: response.data.orderName,
                    total: response.data.Total,
                    supId: response.data.supplierId,
                });
            })

        await axios.get(`http://localhost:8080/api/order/${this.props.match.params.id}`)
            .then(response => {
                this.setState({order: response.data});
            })



    }

    async getNames(){
        for (let i = 0; i < this.state.orderItems.length; i++) {
            let id = this.state.orderItems[i].itemId;

            await axios.get(`http://localhost:8080/api/item/${id}`)
                .then(response=>{
                    this.setState({itemsOfOrder: [...this.state.itemsOfOrder,response.data]});
                }).then(()=>{

            })
        }
    }



    async acceptOrder(id,name,total) {

        let order = {
            status:'Accepted',
        };

        await axios.put(`http://localhost:8080/api/order/${this.props.match.params.id}`, order)
            .then(response => {
                SubmissionAlert1();

            })
            .catch(error => {
                console.log(error.message);
                SubmissionFail();
            })

        await this.getItems(this.props.match.params.id);


        await axios.get(`http://localhost:8080/api/supplier/${this.state.supId}`)
            .then(response => {
                this.setState({supplierEmail: response.data.supplierEmail});
            })

        let sent = {
            orderId: id,
            orderName: name,
            total: total,
            items: this.state.orderItems,
            email:this.state.supplierEmail
        };

        await axios.post('http://localhost:8080/api/order/mail', sent)
            .then(response => {
                alert('Email Sent');
                window.location.reload(false);
            })
            .catch(error => {
                console.log(error.message);
                alert(error.message)
            })
    }

    async getItems(orderId){
        await axios.get(`http://localhost:8080/api/getItemsByOrder/${orderId}`)
            .then(response => {
                this.setState({orderItems: response.data});
            })
    }

    declineOrder(id) {

        let order = {
            status:'Declined',
        };

        axios.put(`http://localhost:8080/api/order/${this.props.match.params.id}`, order)
            .then(response => {
                SubmissionAlert2();

            })
            .catch(error => {
                console.log(error.message);
                SubmissionFail();
            })
    }

    getForRenderName(index){
        if(this.state.itemsOfOrder[index] !== undefined){
            return this.state.itemsOfOrder[index].itemName;
        }

    }

    getForRenderPic(index){
        if(this.state.itemsOfOrder[index] !== undefined){
            return this.state.itemsOfOrder[index].itemPic;
        }

    }

    render() {
        return (
            <div>
                <div className=" container" style={{width: '80%'}}>
                    <div className="card" style={{width: '100%',position:"relative"}}>
                        <div className="container">
                            <h1 style={{textTransform:"uppercase",textAlign:"center"}} >Stock Items</h1>
                            <h3 style={{textTransform:"uppercase",textAlign:"right",textColor:'red'}} >{this.state.temp}</h3>
                            <Row xs={1} md={2} className="g-4">
                                {this.state.orderItems.length > 0 && this.state.orderItems.map((item, index) => (
                                    <Col>
                                        <Card className="category-card">
                                            <Card.Img variant="top" img src={this.getForRenderPic(index)} alt="Category"  className="center w3-card-4"/>
                                            <Card.Body>
                                                <Card.Title>
                                                    {/*<h2 className="item_title">{this.state.itemsOfOrder[1].itemName}</h2>&nbsp;*/}
                                                    <h2 className="item_title">{this.getForRenderName(index)}</h2>&nbsp;
                                                </Card.Title>
                                                <Card.Text>
                                                    <h4 style={{color:"darkblue"}}>QTY: {item.qty}</h4>
                                                </Card.Text>
                                                <Card.Subtitle className="mb-2 text-muted">
                                                    <h6>Sub Total:{item.subTotal}</h6>
                                                </Card.Subtitle>
                                            </Card.Body>
                                            <Card.Footer>

                                            </Card.Footer>
                                        </Card>
                                    </Col>
                                ))}


                            </Row>
                            <br/>
                            <button className="btn btn-warning "
                                    onClick={e => this.acceptOrder(this.props.match.params.id,this.state.name,this.state.total)}>Accept
                            </button>&nbsp;&nbsp;
                            <button className="btn btn-danger "
                                    onClick={e => this.declineOrder(this.props.match.params.id)}>Decline
                            </button>
                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

export default viewOrderItems;
