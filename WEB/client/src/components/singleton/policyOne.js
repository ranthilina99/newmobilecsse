// import axios from "axios";
//
// var mySingleton = (function (length,price,id) {
//
//     let instance;
//
//     function init() {
//
//         async function privateMethod(){
//
//             let policyOnes;
//             let current;
//
//             await axios.get(`http://localhost:8080/api/policyOnes`)
//                 .then(response => {
//                      policyOnes = response.data ;
//                 }).then(()=>{
//                     console.log(this.state.policyOnes);
//                 })
//
//             if(this.state.policyOnes.length > 0){
//                 current = policyOnes[0].policyOnePrice;
//             }
//         }
//
//
//         return {
//
//             getRandomNumber: function() {
//                 return privateRandomNumber;
//             }
//
//         };
//
//     };
//
//     return {
//
//         // Get the Singleton instance if one exists
//         // or create one if it doesn't
//         getInstance: function () {
//
//             if ( !instance ) {
//                 instance = init();
//             }
//
//             return instance;
//         }
//
//     };
//
// })();