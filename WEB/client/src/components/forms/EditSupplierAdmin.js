import React, {Component} from 'react';
import axios from 'axios';
import FileBase from 'react-file-base64';
// import './stock.css'
import swat from "sweetalert2";
import {FormGroup} from "@material-ui/core";
import {Form, FormFeedback, Input, Label} from "reactstrap";
import {storage} from "../firebase";
import {Card} from "react-bootstrap";


const SubmissionAlert = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Category Updated Successfully!',
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
    id: '',
    supplierName: '',
    supplierCompany: '',
    supplierSpeciality:'',
    supplierEmail:'',
    supplierPic:null,
    image:'',
    progress:0,
    supplier:{},
    touched: {
        supplierName: false,
        supplierCompany: false,
        supplierSpeciality: false,
        supplierEmail:false,
    }
}

class EditSupplierAdmin extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.state = initialState;
    }

    componentDidMount() {

            axios.get(`http://localhost:8080/api/supplier/${this.props.match.params.id}`)
            .then(response => {
                this.setState(
                    {

                        supplierName: response.data.supplierName,
                        supplierCompany: response.data.supplierCompany,
                        supplierSpeciality: response.data.supplierSpeciality,
                        supplierEmail: response.data.supplierSpeciality,


                    });
            })
            .catch(error => {
                alert(error.message)
            })

    }

    handleChange = e => {
        if (e.target.files[0]) {
            this.setState({
                image: e.target.files[0]
            })

        }
    };

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    handleBlur = (field) => (evt) => {
        this.setState({
            touched: { ...this.state.touched, [field]: true }
        });
    }

    validate =(supplierName,supplierCompany,supplierSpeciality,supplierEmail)=> {
        const errors = {
            supplierName: '',
            supplierCompany: '',
            supplierSpeciality:'',
            supplierEmail:'',
        };
        if (this.state.touched.supplierName && supplierName.length < 3)
            errors.supplierName = 'Name should be >= 3 characters';

        if (this.state.touched.supplierCompany && supplierCompany.length < 3)
            errors.supplierCompany = 'Company should be >= 3 characters';

        if (this.state.touched.supplierSpeciality && supplierSpeciality.length < 3)
            errors.supplierSpeciality = 'Speciality should be >= 3 characters';

        if (this.state.touched.supplierEmail && supplierEmail.length < 3)
            errors.supplierEmail = 'Speciality should be >= 3 characters';

        return errors;
    }


    onSubmit(e) {
        e.preventDefault();
      let  supplier={
                supplierName: this.state.supplierName,
                supplierCompany: this.state.supplierCompany,
                supplierSpeciality: this.state.supplierSpeciality,
                supplierEmail: this.state.supplierEmail,

        };


    if (this.state.supplierName.length < 3 || this.state.supplierSpeciality.length < 3 || this.state.supplierCompany.length < 3 || this.state.supplierEmail.length < 3) {
    this.validate(this.state.supplierName, this.state.supplierCompany, this.state.supplierSpeciality,this.state.supplierEmail)
    }
        else
        {
            axios.put(`http://localhost:8080/api/supplier/${this.props.match.params.id}`,supplier)
                .then(response => {
                    SubmissionAlert();
                    window.location.replace("/getSuppliers");
                })
                .catch(error => {
                    console.log(error.message);
                    alert(error.message)
                })
        }
    }

    render() {
        const errors=this.validate(this.state.supplierName,this.state.supplierCompany,this.state.supplierSpeciality,this.state.supplierEmail);

        return (
            <div className="workout_wrapper" style={{ borderTop: "10px solid black"}}>
                <br/><br/>
                <Form onSubmit={this.onSubmit}>
                    <h1 className="workout_title">ADD SUPPLIER</h1>
                    &nbsp;
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="workout_name">Name</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="supplierName"
                                    id="supplierName"
                                    size="100"
                                    value={this.state.supplierName}
                                    onChange={this.onChange}
                                    valid={errors.supplierName === ''}
                                    invalid={errors.supplierName !== ''}
                                    onBlur={this.handleBlur('supplierName')}
                                />
                                <FormFeedback>{errors.supplierName}</FormFeedback>
                            </div>
                        </FormGroup>
                    </div>
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="workout_theme">Company</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="supplierCompany"
                                    id="supplierCompany"
                                    size="100"
                                    value={this.state.supplierCompany}
                                    onChange={this.onChange}
                                    valid={errors.supplierCompany === ''}
                                    invalid={errors.supplierCompany !== ''}
                                    onBlur={this.handleBlur('supplierCompany')}
                                />
                                <FormFeedback>{errors.supplierCompany}</FormFeedback>
                            </div>
                        </FormGroup>
                    </div>
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="workout_description">Speciality</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="supplierSpeciality"
                                    id="supplierSpeciality"
                                    size="160"
                                    value={this.state.supplierSpeciality}
                                    onChange={this.onChange}
                                    valid={errors.supplierSpeciality === ''}
                                    invalid={errors.supplierSpeciality !== ''}
                                    onBlur={this.handleBlur('supplierSpeciality')}
                                />
                                <FormFeedback>{errors.supplierSpeciality}</FormFeedback>
                            </div>
                        </FormGroup>
                    </div>
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="workout_description">Supplier Email</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="supplierEmail"
                                    id="supplierEmail"
                                    size="160"
                                    value={this.state.supplierEmail}
                                    onChange={this.onChange}
                                    valid={errors.supplierEmail === ''}
                                    invalid={errors.supplierEmail !== ''}
                                    onBlur={this.handleBlur('supplierEmail')}
                                />
                                <FormFeedback>{errors.supplierEmail}</FormFeedback>
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

export default EditSupplierAdmin;
