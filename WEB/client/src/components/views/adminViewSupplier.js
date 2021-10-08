// import React, {Component} from 'react';
// import swat from "sweetalert2";
// import axios from "axios";
// import {DropdownItem, DropdownMenu, DropdownToggle, UncontrolledDropdown} from "reactstrap";
// // import {ExportToCsv} from "export-to-csv";
// // import jsPDF from "jspdf";
// // import 'jspdf-autotable'
// import 'bootstrap/dist/css/bootstrap.min.css';
//
//
//
// const supplierAlert = () => {
//     swat.fire({
//         position: 'center',
//         icon: 'success',
//         title: ' Delete Successfully',
//         showConfirmButton: false,
//         timer: 3000
//     });
// }
//
// const supplierFail = () => {
//     swat.fire({
//         icon: 'error',
//         title: 'Oops...',
//         text: ' Delete Failed!'
//     })
// }
//
// class AdminViewSupplier extends Component {
//     constructor(props) {
//         super(props);
//         this.state = {
//             suppliers: [],
//             search: '',
//             filter:'',
//             filteredData:''
//         }
//         this.handleChange=this.handleChange.bind(this);
//     }
//
//     componentDidMount() {
//         axios.get('http://localhost:8080/api/suppliers')
//             .then(response => {
//                 this.setState({suppliers: response.data.data });
//             })
//     }
//
//     handleChange = event => {
//         this.setState({ filter: event.target.value });
//     };
//
//     deleteSuppliers(id){
//         axios.delete(`http://localhost:8080/api/supplier/${id}`)
//             .then(response => {
//                 this.setState({ suppliers: response.data.data });
//             }).then(response=> {
//             supplierAlert()})
//             .catch(err => {
//                 supplierFail()
//                 console.log(err.message);
//             })
//         // window.location.replace("/adminViewSuppliers");
//
//     }
//
//     // deleteSuppliers(id){
//     //     axios.delete(`http://localhost:5000/Suppliers/${id}`)
//     //         .then(response => {
//     //             this.setState({ suppliers: response.data.data });
//     //         })
//     //     window.location = `/adminViewSuppliers`
//     // }
//
//     navigateEditSupplierPage(e, supplierId) {
//         window.location = `/adminEditSuppliers/api/${supplierId}`
//     }
//
//     // ExportCSV = () => {
//     //     const options = {
//     //         fieldSeparator: ',',
//     //         quoteStrings: '"',
//     //         decimalSeparator: '.',
//     //         showLabels: true,
//     //         showTitle: true,
//     //         filename :'equinox all supplier report',
//     //         title: 'All Suppliers Details CSV ',
//     //         useTextFile: false,
//     //         useBom: true,
//     //         useKeysAsHeaders: false,
//     //         headers: [ 'Supplier Name','Email','Address', 'Contact Number'],
//     //     };
//     //
//     //     const data = this.state.suppliers.map(elt=> [elt.supplier_name, elt.supplier_email,elt.supplier_address, elt.supplier_phone]);
//     //     const csvExporter = new ExportToCsv(options);
//     //     csvExporter.generateCsv(data);
//     // }
//     // ExportPDF = () => {
//     //     const unit = "pt";
//     //     const size = "A4"; // Use A1, A2, A3 or A4
//     //     const orientation = "portrait"; // portrait or landscape
//     //
//     //     const marginLeft = 40;
//     //     const doc = new jsPDF(orientation, unit, size);
//     //     doc.setFontSize(25);
//     //
//     //
//     //     const title = "EQUINOX Gym All Suppliers Details Report";
//     //     const headers = [['Supplier Name','Email','Address', 'Contact Number']];
//     //
//     //     const data = this.state.suppliers.map(elt=> [elt.supplier_name, elt.supplier_email,elt.supplier_address, elt.supplier_phone]);
//     //
//     //     let content = {
//     //         startY: 50,
//     //         head: headers,
//     //         body: data
//     //     };
//     //
//     //     doc.setFont('helvetica')
//     //     doc.setTextColor(0, 0, 255)
//     //     doc.text(title, marginLeft, 40);
//     //     doc.autoTable(content);
//     //     doc.save("equinox all supplier report.pdf")
//     // }
//     onchange =(e)=>{
//         this.setState({
//             search:e.target.value
//         });
//     }
//
//     render() {
//
//         const { filter, suppliers } = this.state;
//         const lowerCasedFilter = filter.toLowerCase();
//         const upperCasedFilter = filter.toUpperCase();
//         this.state.filteredData = suppliers.filter(suppliers => {
//             return Object.keys(suppliers).some(key =>
//                 typeof suppliers[key] === "string" && suppliers[key].toLowerCase().includes(lowerCasedFilter) && suppliers[key].toUpperCase().includes(upperCasedFilter)
//             );
//         });
//
//         return (
//             <div className="container">
//                 <br/>
//                 <div className="navbar justify-content-between alert alert-primary"  role="alert">
//                     <h2>ALL SUPPLIER DETAILS</h2>
//                     <form className="form-inline">
//                         <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" value={filter} onChange={this.handleChange}/>
//                     </form>
//                 </div>
//                 <table className="table table-striped table-hover table-dark  table-bordered ">
//                     <thead className="thead-dark">
//                     <tr>
//                         <th scope="col">#</th>
//                         <th scope="col">Supplier Name</th>
//                         <th scope="col">Supplier Company</th>
//                         <th scope="col">supplier Speciality</th>
//
//                     </tr>
//                     </thead>
//                     <tbody>
//                     {this.state.suppliers.length > 0 && this.state.filteredData.map((suppliers, index)=>(
//                         <tr key={index}>
//                             <th scope="row" >{index + 1}</th>
//                             <td>{suppliers.supplierName}</td>
//                             <td>{suppliers.supplierCompany}</td>
//                             <td>{suppliers.supplierSpeciality}</td>
//                             {/*<td>{suppliers.supplier_phone}</td>*/}
//                             <td>
//                                 <a className="btn btn-warning" onClick={e => this.navigateEditSupplierPage(e, suppliers._id)}><i className="fa fa-edit">&nbsp;Edit</i>&nbsp;</a>
//                                 &nbsp;
//                                 <a className="btn btn-danger" onClick={e => this.deleteSuppliers(suppliers._id)}> <i className="fa fa-trash">&nbsp;Delete</i></a>
//                             </td>
//                         </tr>
//                     ))}
//                     </tbody>
//                 </table>
//                 {/*<div align="right" >*/}
//                 {/*    <UncontrolledDropdown >*/}
//                 {/*        <DropdownToggle  style={{color: 'white', backgroundColor:"blue",marginRight:'15px'}} className = "btn btn-lg">*/}
//                 {/*            <i class="fa fa-download"></i>&nbsp;Generate Report&nbsp;*/}
//                 {/*        </DropdownToggle>*/}
//                 {/*        <DropdownMenu right>*/}
//                 {/*            <DropdownItem onClick={this.ExportPDF}>*/}
//                 {/*                PDF File*/}
//                 {/*            </DropdownItem>*/}
//                 {/*            <DropdownItem divider/>*/}
//                 {/*            <DropdownItem onClick={this.ExportCSV}>*/}
//                 {/*                CSV File*/}
//                 {/*            </DropdownItem>*/}
//                 {/*        </DropdownMenu>*/}
//                 {/*    </UncontrolledDropdown>*/}
//                 {/*</div>*/}
//                 <br/>
//                 <br/>
//             </div>
//         );
//     }
// }
//
// export default AdminViewSupplier;
