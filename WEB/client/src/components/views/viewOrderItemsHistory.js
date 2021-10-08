import React, {Component} from 'react';
import axios from 'axios';
import {Card, Col, Row} from "react-bootstrap";
import swat from "sweetalert2";

class viewOrderItemsHistory extends Component {
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
                })
        }
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
                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

export default viewOrderItemsHistory;
