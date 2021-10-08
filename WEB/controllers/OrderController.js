'use strict';

const firebase = require('../db');
const order = require('../models/orderModel');
const nodemailer = require("nodemailer");
const firestore = firebase.firestore();



const getAllOrders = async(req,res,next) => {
    try{
        const orders = await firestore.collection('orders');
        const data = await orders.get();
        const orderArray = [];
        if (data.empty){
            res.status(404).send('No order record found')
        }else{
            data.forEach(doc =>{
                const Order = new order(
                    doc.id,
                    doc.data().orderName,
                    doc.data().supplierName,
                    doc.data().status,
                    doc.data().deliveryStatus,
                    doc.data().confirmation,
                    doc.data().Total
                );
                orderArray.push(Order);
            });
            res.send(orderArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getAllItemsPending = async(req,res,next) => {
    try{
        const orders1 = await firestore.collection('orders').where('status','==',"Pending");
        const data1 = await orders1.get();
        console.log(data1)
        const orderArray1 = [];
        if (data1.empty){
            res.status(404).send('No order record found')
        }else{
            data1.forEach(doc =>{
                const Order1 = new order(
                    doc.id,
                    doc.data().orderName,
                    doc.data().supplierName,
                    doc.data().status,
                    doc.data().deliveryStatus,
                    doc.data().confirmation,
                    doc.data().Total,
                    doc.data().supplierId
                );
                orderArray1.push(Order1);
            });
            res.send(orderArray1);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getOneOrder = async(req,res,next) => {
    try{
        const id = req.params.id;
        const order = await firestore.collection('orders').doc(id);
        const data = await order.get();
        if (!data.exists){
            res.status(404).send('No order record found')
        }else{
            res.send(data.data());
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const updateOrder= async(req,res,next) => {
    try{
        const id = req.params.id;
        const data = req.body;

        const order = await firestore.collection('orders').doc(id);
        await order.update(data);
        res.status(200).send('Order data updated successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}

const lineCreate = async (items) => {
    let arrayItems = "";
    let n;
    for (n in items) {
        //let itemInfo = getOneItem(items[n].itemId);

        const item = await firestore.collection('items').doc(items[n].itemId);
        const data = await item.get();

        console.log(data.data());
        arrayItems += "<li>" + data.data().itemName +" "+ items[n].qty + "</li>";
    }

    return arrayItems;
}

const mailSend = async (req, res) => {

    try {
        let id = req.body.orderId;
        let name = req.body.orderName;
        let total = req.body.total;
        let items = req.body.items;

        var transporter = nodemailer.createTransport({

            service: 'Gmail',
            auth: {
                user: 'hugoproducts119@gmail.com',
                pass: '123hugo@12'
            },

            tls: {
                rejectUnauthorized: false
            },
        });



        // let arrayItems = "";
        // let n;
        // for (n in items) {
        //     let itemInfo = getOneItem(items[n].itemId);
        //
        //     arrayItems += "<li>" + itemInfo.itemName +" "+ items[n].qty + "</li>";
        // }

        let line = await lineCreate(items);

        var mailOptions = {

            from: 'hugoproducts119@gmail.com',
            to: 'yasoja44@gmail.com',
            subject: 'Supply Order',
            html: `
            <div style="max-width: 700px; margin:auto; border: 10px solid #ddd; padding: 50px 20px; font-size: 110%;">
            <h2 style="text-align: center; color: black;">ID:${id}.</h2>
            <h3 style="text-align: center; color: black;">Order:${name}.</h3>
            <h3 style="text-align: center; color: grey;">Total:${total}.</h3>
            
            <h3 style="text-align: center; color: grey;">Item:${line}.</h3>

             
            </div>`
        };

        await transporter.sendMail(mailOptions, function (error, info) {
            if (error) {
                console.log(error);
            } else {
                console.log('Email sent: ' + info.response);
            }
        });

        res.status(200).json({auth_token: 'token'})
    } catch (e) {
        console.log(e.message);
        return res.status(500).json({msg: "server Error..."});
    }
}




module.exports = {

    getAllOrders,
    getOneOrder,
    updateOrder,
    mailSend,
    getAllItemsPending

}