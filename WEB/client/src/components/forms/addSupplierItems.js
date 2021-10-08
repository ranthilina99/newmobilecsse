
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
    id :'',
    itemSupplierId :'',
    itemName :'',
    itemPrice :'',
    itemPic : null,
    itemPolicyFlag :'false',
    image:'',
    progress:0,
    item:{},
    touched: {
        itemName: false,
        itemPrice: false

    },
}

const SubmissionAlert = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Item Created Successfully!',
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

    validate =(itemName,itemPrice)=> {
        const errors = {
            itemName: '',
            itemPrice: '',
        };
        if (this.state.touched.itemName && itemName.length < 3)
            errors.itemName = 'Name should be >= 3 characters';

        if (this.state.touched.itemPrice && itemPrice.length < 1)
            errors.itemPrice = 'Company should be >= 3 characters';


        return errors;
    }

    async onSubmit(e) {
        e.preventDefault();

        const uploadTask = storage.ref(`itemImages/${this.state.image.name}`).put(this.state.image);
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
                    .ref("itemImages")
                    .child(this.state.image.name)
                    .getDownloadURL()
                    .then(url => {
                        console.log(url);
                        this.setState({
                            item:{
                                itemSupplierId: this.props.match.params.id,
                                itemName: this.state.itemName,
                                itemPrice: this.state.itemPrice,
                                itemPic: url,
                                itemPolicyFlag:this.state.itemPolicyFlag
                            }

                        });

                    }).then(()=>{this.submit2();});

            }
        )

    }

    submit2(){
        if ( this.state.itemName.length < 3 || this.state.itemPrice.length < 1) {
            this.validate( this.state.itemName, this.state.itemPrice)
            let message = "Item Creation Failed"
            SubmissionFail2(message);
        } else {

            axios.post('http://localhost:8080/api/item',this.state.item)
                .then(response => {
                    console.log('DATA TO SEND', this.state.item);
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
        const errors=this.validate(this.state.itemName,this.state.itemPrice);

        return (
            <div className="workout_wrapper" style={{ borderTop: "10px solid black"}}>
                <br/><br/>
                <Form onSubmit={this.onSubmit}>
                    <h1 className="workout_title">ADD Item</h1>
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
