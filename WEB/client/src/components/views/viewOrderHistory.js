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



class viewOrderHistory extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orders: [],
            orderItems:[]
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/orders')
            .then(response => {
                this.setState({orders: response.data });
            })
    }

    navigateViewItemsPage(e, categoryStockId) {
        window.location = `/orderViewStockItemHistory/${categoryStockId}`
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

export default viewOrderHistory;
