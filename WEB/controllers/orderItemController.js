'use strict';

const firebase = require('../db');
const orderItems = require('../models/orderItemModel');
const firestore = firebase.firestore();



const getAllItemsByOrder = async(req,res,next) => {
    try{
        const orderItem = await firestore.collection('orderItems').where('orderId','==',req.params.id);
        const data = await orderItem.get();
        const orderItemArray = [];
        if (data.empty){
            res.status(404).send('No item record found')
        }else{
            data.forEach(doc =>{
                const OrderItem = new orderItems(
                    doc.id,
                    doc.data().orderId,
                    doc.data().itemId,
                    doc.data().qty,
                    doc.data().subTotal
                );
                orderItemArray.push(OrderItem);
            });
            res.send(orderItemArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getAllItemsByOrderForOne = async(req,res,next) => {
    try{
        const orderItem = await firestore.collection('items').where('orderId','==',req.params.id);
        const data = await orderItem.get();
        const orderItemArray = [];
        if (data.empty){
            res.status(404).send('No item record found')
        }else{
            data.forEach(doc =>{
                const OrderItem = new orderItems(
                    doc.id,
                    doc.data().orderId,
                    doc.data().itemId,
                    doc.data().qty,
                    doc.data().subTotal
                );
                orderItemArray.push(OrderItem);
            });
            res.send(orderItemArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}




module.exports = {

    getAllItemsByOrder,
    getAllItemsByOrderForOne

}