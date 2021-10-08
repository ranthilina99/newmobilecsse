'use strict';

const firebase = require('../db');
const supplier = require('../models/supplierModel');
const firestore = firebase.firestore();

const addSupplier = async(req,res,next) => {
    try{
        const data = req.body;
        await firestore.collection('suppliers').doc().set(data);
        res.send('Supplier saved successfully')
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getAllSuppliers = async(req,res,next) => {
    try{
        const suppliers = await firestore.collection('suppliers');
        const data = await suppliers.get();
        const supplierArray = [];
        if (data.empty){
            res.status(404).send('No supplier record found')
        }else{
            data.forEach(doc =>{
                const Supplier = new supplier(
                    doc.id,
                    doc.data().supplierName,
                    doc.data().supplierCompany,
                    doc.data().supplierSpeciality,
                    doc.data().supplierEmail,
                    doc.data().supplierPic
                );
                supplierArray.push(Supplier);
            });
            res.send(supplierArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getOneSupplier = async(req,res,next) => {
    try{
        const id = req.params.id;
        const supplier = await firestore.collection('suppliers').doc(id);
        const data = await supplier.get();
        if (!data.exists){
            res.status(404).send('No supplier record found')
        }else{
            res.send(data.data());
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const updateSupplier= async(req,res,next) => {
    try{
        const id = req.params.id;
        const data = req.body;

        const supplier = await firestore.collection('suppliers').doc(id);
        await supplier.update(data);
        res.status(200).send('Supplier data updated successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}

const deleteSupplier = async(req,res,next) => {
    try{
        const id = req.params.id;


        await firestore.collection('suppliers').doc(id).delete();
        res.status(200).send('Supplier data deleted successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}

module.exports = {
    addSupplier,
    getAllSuppliers,
    getOneSupplier,
    updateSupplier,
    deleteSupplier
}
