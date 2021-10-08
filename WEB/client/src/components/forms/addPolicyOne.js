import React, { Component} from 'react';
import axios from 'axios';
import FileBase from 'react-file-base64';
import swat from "sweetalert2";
import {Form, FormGroup, Label, Input, FormFeedback} from 'reactstrap';
import '../css/workout.css';



const initialState = {
    id: '',
    policyOnePrice: '',
    policyOnes:[],
    current:'',

    touched: {
        policyOnePrice: false,
    }
}

const SubmissionAlert = () => {
    swat.fire({
        position: 'center',
        icon: 'success',
        title: 'Policy One Created Successfully!',
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

class addPolicyOne extends Component {
    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.state = initialState;


    }

    async componentDidMount() {


        await axios.get(`http://localhost:8080/api/policyOnes`)
            .then(response => {
                this.setState({ policyOnes: response.data });
            }).then(()=>{
                console.log(this.state.policyOnes);
        })

        if(this.state.policyOnes.length > 0){
            this.setState({ current: this.state.policyOnes[0].policyOnePrice});
        }
    }


    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    handleBlur = (field) => (evt) => {
        this.setState({
            touched: { ...this.state.touched, [field]: true }
        });
    }

    validate =(policyOnePrice)=> {
        const errors = {
            policyOnePrice: '',
        };
        if (this.state.touched.policyOnePrice && parseInt(policyOnePrice) < 0)
            errors.policyOnePrice = 'Price should be a valid number';

        return errors;
    }

    onSubmit(e) {
        e.preventDefault();
        let policyOne = {
            policyOnePrice:this.state.policyOnePrice,
        };
        if (this.state.policyOnePrice <= 0 ){
            this.validate(this.state.policyOnePrice)
            let message = "Policy One Creation Failed"
            SubmissionFail2(message);
        } else {

            if(this.state.policyOnes.length > 0){
                console.log('DATA TO SEND', policyOne)
                axios.put(`http://localhost:8080/api/policyOne/${this.state.policyOnes[0].id}`, policyOne)
                    .then(response => {
                        SubmissionAlert();
                        window.location.reload(false);
                    })
                    .catch(error => {
                        console.log(error.message);
                        SubmissionFail();
                    })
            } else{
                console.log('DATA TO SEND', policyOne)
                axios.post(`http://localhost:8080/api/policyOne`,policyOne)
                    .then(response => {
                        SubmissionAlert();
                        window.location.reload(false);
                    })
                    .catch(error => {
                        console.log(error.message);
                        SubmissionFail();
                    })
                }
            }
    }

    deletePolicyOne(id){
        axios.delete(`http://localhost:8080/api/policyOne/${this.state.policyOnes[0].id}`)
            .then(() =>{
                SubmissionAlert();
                window.location.reload(false);
            })
    }





    render() {
        const errors=this.validate(this.state.policyOnePrice);

        return (
            <div className="workout_wrapper" style={{ borderTop: "10px solid black"}}>
                <br/><br/>
                <Form onSubmit={this.onSubmit}>
                    <h1 className="workout_title">ADD POLICY ONE</h1>
                    &nbsp;
                    <div className="row justify-content-md-center">
                        <FormGroup >
                            <Label for="policyOnePrice">PRICE</Label>
                            <div>
                                <Input
                                    type="text"
                                    name="policyOnePrice"
                                    id="policyOnePrice"
                                    size="100"
                                    placeholder={this.state.current}
                                    value={this.state.policyOnePrice}
                                    onChange={this.onChange}
                                    valid={errors.policyOnePrice === ''}
                                    invalid={errors.policyOnePrice !== ''}
                                    onBlur={this.handleBlur('policyOnePrice')}
                                />
                                <FormFeedback>{errors.policyOnePrice}</FormFeedback>
                            </div>
                        </FormGroup>
                    </div>

                    &nbsp;
                    <button className="workout_button btn btn-primary">SUBMIT</button>
                    &nbsp;
                    <br/>
                    <br/>
                    <button className="btn btn-danger" onClick={() => this.deletePolicyOne(this.state.policyOnes[0].id)}>DISABLE</button>
                </Form>

            </div>
        )
    }
}

export default addPolicyOne;