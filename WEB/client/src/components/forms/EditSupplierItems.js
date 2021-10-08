import React, {Component} from 'react';
import axios from 'axios';
import FileBase from 'react-file-base64';
import swat from "sweetalert2";
import {FormGroup} from "@material-ui/core";
import {Form, FormFeedback, Input, Label} from "reactstrap";


const SubmissionAlert = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Item Updated Successfully!',
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

const initialState = {
    itemName :'',
    itemPrice :'',
    image:'',
    progress:0,
    item:{},
    touched: {
        itemName: false,
        itemPrice: false

    }
}

class EditSupplierItems extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.state = initialState;
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/api/item/${this.props.match.params.id}`)
            .then(response => {
                this.setState(
                    {
                        itemName: response.data.itemName,
                        itemPrice: response.data.itemPrice,
                    });
            })
            .catch(error => {
                alert(error.message)
            })
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }

    handleBlur = (field) => (evt) => {
        this.setState({
            touched: {...this.state.touched, [field]: true}
        });
    }

    validate = (itemName, itemPrice) => {
        const errors = {

            itemName: '',
            itemPrice: '',
        };
        if (this.state.touched.itemName && itemName.length <= 0)
            errors.itemName = 'Category Item should be filled';
        else if (this.state.touched.itemPrice && itemPrice.length <= 0)
            errors.itemPrice = 'Item Quantity should be >0';

        return errors;
    }


    onSubmit(e) {

        e.preventDefault();
        let item = {
            itemName: this.state.itemName,
            itemPrice: this.state.itemPrice,
        };
        this.setState({
            categoryID: this.props.match.params.id
        });
        if (this.state.itemName.length < 0 || this.state.itemPrice.length < 0 ) {
            this.validate(this.state.itemName, this.state.itemPrice)
        } else {
            console.log('DATA TO SEND', item);
            axios.put(`http://localhost:8080/api/item/${this.props.match.params.id}`,item)
                .then(response => {
                    SubmissionAlert();
                    window.location.replace("/getSuppliers");
                })
                .catch(error => {
                    SubmissionFail();
                })
        }
    }

    render() {

        const errors=this.validate(this.state.itemName,this.state.itemPrice);

        return (
            <div className="workout_wrapper" style={{ borderTop: "10px solid black"}}>
                <br/><br/>
                <Form onSubmit={this.onSubmit}>
                    <h1 className="workout_title">Edit Item</h1>
                    &nbsp;
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="workout_name">Item Name</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="itemName"
                                    id="itemName"
                                    size="100"
                                    value={this.state.itemName}
                                    onChange={this.onChange}
                                    valid={errors.itemName === ''}
                                    invalid={errors.itemName !== ''}
                                    onBlur={this.handleBlur('itemName')}
                                />
                                <FormFeedback>{errors.itemName}</FormFeedback>
                            </div>
                        </FormGroup>
                    </div>
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="workout_theme">Item Price</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="itemPrice"
                                    id="itemPrice"
                                    size="100"
                                    value={this.state.itemPrice}
                                    onChange={this.onChange}
                                    valid={errors.itemPrice === ''}
                                    invalid={errors.itemPrice !== ''}
                                    onBlur={this.handleBlur('itemPrice')}
                                />
                                <FormFeedback>{errors.itemPrice}</FormFeedback>
                            </div>
                        </FormGroup>
                    </div>

                    {/*<div className="row justify-content-center">*/}
                    {/*    <div>*/}
                    {/*        /!*<FileBase type="file" multiple={false} onDone={({base64}) => this.state.supplierPic = base64} />*!/*/}
                    {/*        <input type="file" onChange={this.handleChange} />*/}

                    {/*    </div>*/}
                    {/*</div>*/}
                    &nbsp;

                    <button className="workout_button btn btn-primary">SUBMIT</button>
                </Form>

            </div>
        )
    }
}

export default EditSupplierItems;
