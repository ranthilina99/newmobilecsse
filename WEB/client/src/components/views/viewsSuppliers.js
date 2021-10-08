import React, { Component} from 'react';
import axios from 'axios';
import '../css/commonViewsCSS.css';
import {Card, Col, Row} from 'react-bootstrap'
import swat from "sweetalert2";

const SubmissionAlert = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Category Deleted Successfully!',
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

class viewsSuppliers extends Component {
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

    deleteCategory(id){
        axios.delete(`http://localhost:8080/api/supplier/${id}`)
            .then(response => {
                SubmissionAlert();
                window.location.replace("/getSuppliers");
            })

    }

    navigateEditStockCategoryPage(e, categoryStockId) {
        window.location = `/editSupplier/${categoryStockId}`
    }


    navigateAddStockCategoryItemsPage(e, categoryStockId) {
        window.location = `/addSupplierItems/${categoryStockId}`

    }

    navigateViewItemsPage(e, categoryStockId) {
        window.location = `/getSupplierItems/${categoryStockId}`


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
                                                <button className="btn btn-outline-danger" onClick={e => this.deleteCategory(item.id)}><i
                                                    className="fas fa-times"></i></button>
                                            </div>
                                            <Card.Img variant="top" img src={item.supplierPic} alt="Category"  className="center card-img-top item_img-zoom w3-card-4"/>

                                            <Card.Body>
                                                <Card.Title>
                                                    <h3>{item.supplierName}</h3>
                                                </Card.Title>
                                                <Card.Text>
                                                    <h4>{item.supplierCompany}</h4>
                                                    <h5>{item.supplierSpeciality}</h5>
                                                    <h5>{item.supplierEmail}</h5>
                                                </Card.Text>
                                            </Card.Body>
                                            <Card.Footer className="item-footer-button">
                                                <button className="btn btn-success add-item" onClick={e => this.navigateAddStockCategoryItemsPage(e, item.id)}>Add an Item</button>
                                                &nbsp; &nbsp;
                                                <button className="btn btn-primary" onClick={e => this.navigateViewItemsPage(e,item.id)}>Go To Items</button>
                                                &nbsp; &nbsp;
                                                <button className="btn btn-warning edit-item" onClick={e => this.navigateEditStockCategoryPage(e, item.id)}>
                                                    <i className="fas fa-edit">&nbsp;</i>Edit</button>
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

export default viewsSuppliers;
