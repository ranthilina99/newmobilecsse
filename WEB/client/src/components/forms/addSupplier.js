import React, { Component} from 'react';
import axios from 'axios';
import FileBase from 'react-file-base64';
import swat from "sweetalert2";
import {Form, FormGroup, Label, Input, FormFeedback} from 'reactstrap';
import { render } from "react-dom";
//////
import { storage } from "../firebase";
//////
import '../css/workout.css';



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
        supplierSpeciality:false,
        supplierEmail:false,
        supplierPic:false,
    }
}

const SubmissionAlert = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Supplier Created Successfully!',
        showConfirmButton: false,
        timer: 3000
    });
}

const SubmissionFail = () => {
    swat.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Submission Error!'
    })
}

const SubmissionFail2 = (message) => {
    swat.fire({
        icon: 'error',
        title: 'Oops...',
        text: message
    })
}

class addSupplier extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.state = initialState;


    }

    componentDidMount() {
        // const token = localStorage.getItem('token');
        // if (!token) {
        //     this.setState({
        //         user: null
        //     });
        //     return;
        // }
        // this.setState({
        //     token: token
        // })
        // axios({
        //     method: 'get',
        //     url: 'http://localhost:5000/users/',
        //     headers: {
        //         Authorization: token
        //     },
        //     data: {}
        // }).then(res => {
        //     this.setState({
        //         workout_creator:res.data._id,
        //         fName:res.data.firstName,
        //         lName:res.data.lastName,
        //         isLoggedIn:true
        //     })
        // }).catch(err => {
        //     console.log(err.message);
        // });
    }

    //////////////////
    handleChange = e => {
        if (e.target.files[0]) {
            this.setState({
                image: e.target.files[0]
            })



        }
    };
    //////////////////////

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

    async onSubmit(e) {
        e.preventDefault();



        const uploadTask = storage.ref(`supplierImages/${this.state.image.name}`).put(this.state.image);
        await uploadTask.on(
            "state_changed",
            snapshot => {
                const progress = Math.round(
                    (snapshot.bytesTransferred / snapshot.totalBytes) * 100
                );

                this.setState({
                    progress: progress
                });

            },
            error => {
                console.log(error);
            },
            () => {
                storage
                    .ref("supplierImages")
                    .child(this.state.image.name)
                    .getDownloadURL()
                    .then(url => {
                        console.log(url);
                        this.setState({
                            supplier:{
                                supplierName: this.state.supplierName,
                                supplierCompany: this.state.supplierCompany,
                                supplierSpeciality: this.state.supplierSpeciality,
                                supplierEmail: this.state.supplierEmail,
                                supplierPic: url,
                            }

                        });

                    }).then(()=>{this.submit2();});

            }
        )

    }

    submit2(){
        if (this.state.supplierName.length < 3 || this.state.supplierSpeciality.length < 3 ||
            this.state.supplierCompany.length < 3 || this.state.supplierCompany.length < 3 ) {
            this.validate(this.state.supplierName, this.state.supplierCompany, this.state.supplierSpeciality,this.state.supplierEmail)
            let message = "Supplier Creation Failed"
            SubmissionFail2(message);
        } else {

            // let supplier = {
            //     supplierName: this.state.supplierName,
            //     supplierCompany: this.state.supplierCompany,
            //     supplierSpeciality: this.state.supplierSpeciality,
            //     supplierPic: this.state.supplierPic
            // };

            console.log("asdasd    "+this.state.supplier.supplierPic);

            axios.post('http://localhost:8080/api/supplier',this.state.supplier)
                .then(response => {
                    console.log('DATA TO SEND', this.state.supplier);
                    SubmissionAlert();
                    window.location.replace("/getSuppliers");

                })
                .catch(error => {
                    console.log(error.message);
                    SubmissionFail();
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

                    <div className="row justify-content-center">
                        <div>
                            {/*<FileBase type="file" multiple={false} onDone={({base64}) => this.state.supplierPic = base64} />*/}
                            <input type="file" onChange={this.handleChange} />

                        </div>
                    </div>
                    &nbsp;

                    <button className="workout_button btn btn-primary">SUBMIT</button>
                </Form>

            </div>
        )
    }
}

export default addSupplier;
