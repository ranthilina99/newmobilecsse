'use strict';

const firebase = require('../db');
const items = require('../models/itemModel');
const firestore = firebase.firestore();

const addItem = async(req,res,next) => {
    try{
        const data = req.body;
        await firestore.collection('items').doc().set(data);
        res.send('Item saved successfully')
    }catch(error){
        res.status(400).send(error.message);
    }
}


const getAllItem = async(req,res,next) => {
    try{
        const supplierItems = await firestore.collection('items');
        const data = await supplierItems.get();
        const supplierItemsArray = [];
        if (data.empty){
            res.status(200).send('No supplier record found')
        }else{
            data.forEach(doc =>{
                const Item = new items(
                    doc.id,
                    doc.data().itemSupplierId,
                    doc.data().itemName,
                    doc.data().itemPrice,
                    doc.data().itemPic,
                    doc.data().itemPolicyFlag
                );
                supplierItemsArray.push(Item);
            });
            res.send(supplierItemsArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getOneItem = async(req,res,next) => {
    try{
        const id = req.params.id;
        const item = await firestore.collection('items').doc(id);
        const data = await item.get();
        if (!data.exists){
            res.status(200).send('No Item record found')
        }else{
            res.send(data.data());
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const updateItem= async(req,res,next) => {
    try{
        const id = req.params.id;
        const data = req.body;

        const item = await firestore.collection('items').doc(id);
        await item.update(data);
        res.status(200).send('Item data updated successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}

const deleteItem = async(req,res,next) => {
    try{
        const id = req.params.id;
        await firestore.collection('items').doc(id).delete();
        res.status(200).send('Item data deleted successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}


const getAllItemsBySuppliers = async(req,res,next) => {
    try{

        const supplierItems = await firestore.collection('items').where('itemSupplierId','==',req.params.id);
        console.log(supplierItems.get())
        const data = await supplierItems.get();
        const supplierItemsArray = [];
        if (data.empty){
            res.status(404).send('No supplier record found')
        }else{
            data.forEach(doc =>{
                const Item = new items(
                    doc.id,
                    doc.data().itemSupplierId,
                    doc.data().itemName,
                    doc.data().itemPrice,
                    doc.data().itemPic,
                    doc.data().itemPolicyFlag
                );
                supplierItemsArray.push(Item);
            });
            res.send(supplierItemsArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}


module.exports = {
    addItem,
    getAllItem,
    getOneItem,
    updateItem,
    deleteItem,
    getAllItemsBySuppliers
}
