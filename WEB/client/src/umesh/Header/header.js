import React, {Component} from 'react';
import Navbar from 'react-bootstrap/Navbar'
import logo from '../../images/logo.png'
import './nav.css'
import axios from "axios";
import {
    Nav,
    UncontrolledDropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem,
    NavbarToggler,
    Collapse,
    NavbarBrand,
} from 'reactstrap';
import Avatar from 'react-avatar';
import {Button} from "react-bootstrap";

class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: "",
            image: '',
            firstname: '',
            lastname: '',
            position: '',
            isLoggedIn:false,
            id:''
        }
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    logoutOnClick = e => {
        localStorage.removeItem('token');
        localStorage.removeItem('userPosition');
        this.setState({
            isLoggedIn: false,
            user: ''
        })

        window.location.replace('/login')
    }
    profileLink = e => {
        window.location.replace('/profile');
    }
    AddUser = e => {
        window.location.replace('/adminReg');
    }
    ViewUsers = e => {
        window.location.replace('/getAll');
    }
    AddStock = e => {
        window.location.replace('/adminCreateStockCategory');
    }
    ViewStock = e => {
        window.location.replace('/adminViewStockCategory');
    }
    AddSupplier = e => {
        window.location.replace('/adminCreateSuppliers');
    }
    ViewSupplier = e => {
        window.location.replace('/adminViewSuppliers');
    }
    // AddCategory = e => {
    //     window.location.replace('#');
    // }
    // ViewCategory = e => {
    //     window.location.replace('#');
    // }
    AddCWorkout = e => {
        window.location.replace('/workout');
    }
    ViewWorkout = e => {
        window.location.replace('/workoutEmployeeShow');
    }
    BuyWorkout = e => {
        window.location.replace('/question');
    }
    ViewWorkoutUser = e => {
        window.location.replace('/workoutUserShow/'+this.state.id);
    }
    ViewAllWorkoutUser= e => {
        window.location.replace('/workoutUserAllShow');
    }

    componentDidMount() {
        const token = localStorage.getItem('token');
        if (!token) {
            this.setState({
                user: null
            });
            return;
        }
        this.setState({
            token: token
        })
        axios({
            method: 'get',
            url: 'http://localhost:5000/users/',
            headers: {
                Authorization: token
            },
            data: {}
        }).then(res => {
            this.setState({
                image: res.data.imageUrl,
                firstname: res.data.firstName,
                lastname: res.data.lastName,
                position: res.data.position,
                id:res.data._id,
                isLoggedIn:true
            })
        }).catch(err => {
            console.log(err.message);
        });
    }

    render() {
        return (
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                <NavbarBrand href="/">
                    <img
                        alt=""
                        src={logo}
                        width="100"
                        height="90"
                    />{' '}
                    &nbsp;
                </NavbarBrand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        { localStorage.getItem('userPosition') !== "supervisor" &&
                        localStorage.getItem('userPosition') !== "manager" &&
                        localStorage.getItem('userPosition') !== "accountant" ||
                        localStorage.getItem('userPosition') === " "  ?
                            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                                <li className="nav-item">
                                    <a className="nav-link active" aria-current="page"
                                       href="/">Home</a>
                                </li>
                            </ul>
                            :
                            null
                        }
                        {localStorage.getItem('userPosition')=== "supervisor" ?
                            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                                <li className="nav-item">
                                    <a className="nav-link active" aria-current="page"
                                       href="/admin">Home</a>
                                </li>
                                <li className="nav-item dropdown">
                                    <UncontrolledDropdown nav>
                                        <DropdownToggle nav caret>
                                            Stock
                                        </DropdownToggle>
                                        <DropdownMenu>
                                            <DropdownItem onClick={this.AddStock}>
                                                Add Stock
                                            </DropdownItem>
                                            <DropdownItem divider/>
                                            <DropdownItem onClick={this.ViewStock}>
                                                View Stock
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </UncontrolledDropdown>
                                </li>
                                <li className="nav-item dropdown">
                                    <UncontrolledDropdown nav>
                                        <DropdownToggle nav caret>
                                            Users
                                        </DropdownToggle>
                                        <DropdownMenu>
                                            <DropdownItem onClick={this.AddUser}>
                                                Add User
                                            </DropdownItem>
                                            <DropdownItem divider/>
                                            <DropdownItem onClick={this.ViewUsers}>
                                                View Users Details
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </UncontrolledDropdown>
                                </li>
                                <li className="nav-item dropdown">
                                    <UncontrolledDropdown nav>
                                        <DropdownToggle nav caret>
                                            Supplier
                                        </DropdownToggle>
                                        <DropdownMenu>
                                            <DropdownItem onClick={this.AddSupplier}>
                                                Add Supplier
                                            </DropdownItem>
                                            <DropdownItem divider/>
                                            <DropdownItem onClick={this.ViewSupplier}>
                                                View Suppliers
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </UncontrolledDropdown>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" href="/createStore">Add Store</a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" href="/workoutAdminShow">View Workout</a>
                                </li>
                                {/*<li className="nav-item dropdown">*/}
                                {/*    <UncontrolledDropdown nav>*/}
                                {/*        <DropdownToggle nav caret>*/}
                                {/*            Item Category*/}
                                {/*        </DropdownToggle>*/}
                                {/*        <DropdownMenu>*/}
                                {/*            <DropdownItem onClick={this.AddCategory}>*/}
                                {/*                Add Item Category*/}
                                {/*            </DropdownItem>*/}
                                {/*            <DropdownItem divider/>*/}
                                {/*            <DropdownItem onClick={this.ViewCategory}>*/}
                                {/*                View Item Category*/}
                                {/*            </DropdownItem>*/}
                                {/*        </DropdownMenu>*/}
                                {/*    </UncontrolledDropdown>*/}
                                {/*</li>*/}
                            </ul>
                            :
                            null
                        }
                        {localStorage.getItem('userPosition') === "manager" ?
                            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                                <li className="nav-item">
                                    <a className="nav-link active" aria-current="page"
                                       href="/employee">Home</a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" href="/adminViewStockCategory">View Stock</a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" href="/empViewStore">View Store</a>
                                </li>
                                <li className="nav-item dropdown">
                                    <UncontrolledDropdown nav>
                                        <DropdownToggle nav caret>
                                            Workout
                                        </DropdownToggle>
                                        <DropdownMenu>
                                            <DropdownItem onClick={this.AddCWorkout}>
                                                Add Workout
                                            </DropdownItem>
                                            <DropdownItem divider/>
                                            <DropdownItem onClick={this.ViewWorkout}>
                                                View Workout
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </UncontrolledDropdown>
                                </li>
                            </ul>
                            :
                            null
                        }
                        {localStorage.getItem('userPosition') === "accountant" ?
                            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                                <li className="nav-item">
                                    <a className="nav-link active" aria-current="page"
                                       href="/user">Home</a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" href="/viewStore">Store</a>
                                </li>
                                <li className="nav-item dropdown">
                                    <UncontrolledDropdown nav>
                                        <DropdownToggle nav caret>
                                            Workout
                                        </DropdownToggle>
                                        <DropdownMenu>
                                            <DropdownItem onClick={this.BuyWorkout}>
                                                Buy Workout
                                            </DropdownItem>
                                            <DropdownItem divider/>
                                            <DropdownItem onClick={this.ViewWorkoutUser}>
                                                My Workout
                                            </DropdownItem>
                                            <DropdownItem divider/>
                                            <DropdownItem onClick={this.ViewAllWorkoutUser}>
                                                All Workouts
                                            </DropdownItem>
                                        </DropdownMenu>
                                    </UncontrolledDropdown>
                                </li>
                                {/*<li className="nav-item">*/}
                                {/*    <a className="nav-link" href="#">Cart</a>*/}
                                {/*</li>*/}
                            </ul>
                            :
                            null
                        }
                        {localStorage.getItem('userPosition') === "supervisor" || localStorage.getItem('userPosition') === "manager" ||
                        localStorage.getItem('userPosition') === "accountant" || localStorage.getItem('userPosition') === " " ||  !this.state.isLoggedIn ?
                            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                                <li className="nav-item">
                                    <a className="nav-link" aria-current="page"
                                       href="/about">About us</a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" aria-current="page"
                                       href="/contactUs">Contact us</a>
                                </li>
                            </ul>
                            :
                            null
                        }
                    </Nav>
                    {localStorage.getItem('userPosition') === "supervisor" || localStorage.getItem('userPosition') === "manager" ||
                    localStorage.getItem('userPosition') === "accountant" ?
                        <Nav className="ml-auto">
                            <UncontrolledDropdown nav>
                                <DropdownToggle nav caret style={{color: 'white'}}>
                                    {this.state.firstname+ " " +this.state.lastname}
                                    <Avatar style={{marginLeft: '20px'}} size="25" round={true}
                                            name={this.state.firstname+ " " +this.state.lastname}
                                            src={this.state.image}/>
                                </DropdownToggle>
                                <DropdownMenu right>
                                    <DropdownItem onClick={this.profileLink}>
                                        Profile
                                    </DropdownItem>
                                    <DropdownItem divider/>
                                    <DropdownItem onClick={this.logoutOnClick}>
                                        Logout
                                    </DropdownItem>
                                </DropdownMenu>
                            </UncontrolledDropdown>
                        </Nav>
                        :
                        null
                    }
                    { localStorage.getItem('userPosition') !== "supervisor" &&
                    localStorage.getItem('userPosition') !== "manager" &&
                    localStorage.getItem('userPosition') !== "accountant" ||
                    localStorage.getItem('userPosition') === " " ?
                        <Nav className="btn float-right" style={{marginLeft:"950px"}}>
                            <a href="/login"><Button className="button "
                                                     variant="outline-primary">Login</Button></a>
                            &nbsp;
                            &nbsp;
                            <a href="/register"><Button className="button"
                                                        variant="outline-primary">Register</Button></a>
                        </Nav>
                        :
                        null
                    }
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
export default Header;