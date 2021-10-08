import React, { Component} from 'react';
import axios from 'axios';
import '../css/commonViewsCSS.css';
import {Card, Col, Row} from 'react-bootstrap';

class viewSuppliersPolicy extends Component {
    constructor(props) {
        super(props);
        this.state = {
            suppliers: []
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/suppliers')

            .then(response => {
                this.setState({suppliers: response.data });
            }).then(()=>{

        })
    }

    navigateViewItemsPage(e, categoryStockId) {
        window.location = `/policyViewStockItem/${categoryStockId}`
    }


    render() {
        return (
            <div>
                <div className=" container" style={{width: '80%'}}>
                    <div className="card" style={{width: '100%'}}>
                        <div className="container ">
                            <h1 style={{textTransform:"uppercase",textAlign:"center"}}>Suppliers</h1>
                            <Row xs={1} md={2} className="g-4">
                                {this.state.suppliers.length > 0 && this.state.suppliers.map((item, index) => (
                                    <Col>
                                        <Card className="category-card">
                                            <div align="right">
                                            </div>
                                            <Card.Img variant="top" img src={item.supplierPic} alt="Category"  className="center card-img-top item_img-zoom w3-card-4"/>

                                            <Card.Body>
                                                <Card.Title>
                                                    <h3>{item.supplierName}</h3>
                                                </Card.Title>
                                                <Card.Text>
                                                    <h4>{item.supplierCompany}</h4>
                                                    <h6>{item.supplierSpeciality}</h6>
                                                </Card.Text>
                                            </Card.Body>
                                            <Card.Footer className="item-footer-button">
                                                <button className="btn btn-primary" onClick={e => this.navigateViewItemsPage(e,item.id)}>Go To Items</button>
                                            </Card.Footer>
                                        </Card>
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default viewSuppliersPolicy;
