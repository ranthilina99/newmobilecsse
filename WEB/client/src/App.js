import './App.css';
import React from "react";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import addSupplier from './components/forms/addSupplier';
import getSuppliers from './components/views/viewsSuppliers';
import addPolicyOne from './components/forms/addPolicyOne';
import addSupplierItems from "./components/forms/addSupplierItems";
import viewSupplierItem from "./components/views/viewSupplierItem";
import EditSupplierAdmin from "./components/forms/EditSupplierAdmin";
import EditSupplierItems from "./components/forms/EditSupplierItems";
import viewSuppliersPolicy from "./components/views/viewSuppliersPolicy";
import viewOrder from "./components/views/viewOrder";
import viewOrderItems from "./components/views/viewOrderItems";
import policyViewStockItem from "./components/views/viewSuppliersItemPolicy";
import viewOrderHistory from "./components/views/viewOrderHistory";
import viewOrderItemsHistory from "./components/views/viewOrderItemsHistory";

function App() {
  return (
      <div className="App">
        <div>
          <Router>
            <section>
              <Switch>
                  <Route path="/addSupplier" component={addSupplier}  />
                  <Route path="/getSuppliers" component={getSuppliers}  />
                  <Route path="/editSupplier/:id" component={EditSupplierAdmin}  />

                  <Route path="/addSupplierItems/:id" component={addSupplierItems}  />
                  <Route path="/getSupplierItems/:id" component={viewSupplierItem}  />
                  <Route path="/editItem/:id" component={EditSupplierItems}  />


                  <Route path="/addPolicyOne" component={addPolicyOne}  />
                  <Route path="/viewSuppliersPolicy" component={viewSuppliersPolicy}  />
                  <Route path="/policyViewStockItem/:id" component={policyViewStockItem}  />
                  <Route path="/viewOrder" component={viewOrder}  />
                  <Route path="/orderViewStockItem/:id" component={viewOrderItems}  />
                  <Route path="/viewOrderHistory" component={viewOrderHistory}  />
                  <Route path="/orderViewStockItemHistory/:id" component={viewOrderItemsHistory}  />
              </Switch>
            </section>
          </Router>
        </div>
      </div>
  );
}

export default App;
