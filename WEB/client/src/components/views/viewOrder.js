import React, { Component} from 'react';
import axios from 'axios';
import '../css/commonViewsCSS.css';
import {Card, Col, Row} from 'react-bootstrap';
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

class viewOrder extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orders: [],
            orderItems:[]
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/orderPending')
            .then(response => {
                this.setState({orders: response.data });
            })
    }

    navigateViewItemsPage(e, categoryStockId) {
        window.location = `/orderViewStockItem/${categoryStockId}`
    }

    async acceptOrder(e,id,name,total,sID) {

        await axios.get(`http://localhost:8080/api/supplier/${sID}`)
            .then(response => {
                this.setState({supplierEmail: response.data.supplierEmail});
            })

        let order = {
            status:'Accepted',
        };

        await axios.put(`http://localhost:8080/api/order/${id}`, order)
            .then(response => {
                SubmissionAlert1();
                //window.location.reload(false);
            }).catch(error => {
                console.log(error.message);
                SubmissionFail();
            })

        await this.getItems(id);

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

    declineOrder(e,id) {

        let order = {
            status:'Declined',
        };

        axios.put(`http://localhost:8080/api/order/${id}`, order)
            .then(response => {
                SubmissionAlert2();
                window.location.reload(false);
            })
            .catch(error => {
                console.log(error.message);
                SubmissionFail();
            })
    }


    render() {
        return (
            <div>
                <div className="container" style={{width: '80%'}}>
                    <div className="card" style={{width: '100%'}}>
                        <div className="container ">
                            <h1 style={{textTransform:"uppercase",textAlign:"center"}}>Orders</h1>
                            <Col>
                                {this.state.orders.length > 0 && this.state.orders.map((item, index) => (

                                    <Row>
                                        <Card className="category-card">
                                            <Card.Header>
                                                <h3>{item.orderName}</h3>
                                            </Card.Header>
                                            <Card.Body>

                                                <Card.Text>
                                                    <h4>{item.supplierName}</h4>
                                                    <h5>{item.status}</h5>
                                                    <h5>{item.deliveryStatus}</h5>
                                                </Card.Text>
                                                <Card.Subtitle className="mb-2 text-muted">
                                                    <h6>Total:{item.Total}</h6>
                                                </Card.Subtitle>

                                            </Card.Body>
                                            <Card.Footer className="item-footer-button">
                                                <button className="btn btn-primary" onClick={e => this.navigateViewItemsPage(e,item.id)}>Items</button>&nbsp;&nbsp;
                                                <button className="btn btn-warning" onClick={e => this.acceptOrder(e,item.id,item.orderName,item.Total,item.supplierId)}>Accept</button>&nbsp;&nbsp;
                                                <button className="btn btn-danger" onClick={e => this.declineOrder(e,item.id)}>Decline</button>
                                            </Card.Footer>
                                        </Card>
                                        <br/>
                                    </Row>
                                ))}
                            </Col>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default viewOrder;
