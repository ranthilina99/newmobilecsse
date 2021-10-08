'use strict';

const firebase = require('../db');
const policyOnes = require('../models/policyOneModel');
const firestore = firebase.firestore();

const addPolicy = async(req,res,next) => {
    try{
        const data = req.body;
        await firestore.collection('policyOne').doc().set(data);
        res.send('Policy one saved successfully')
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getAllPolicyOne = async(req,res,next) => {
    try{
        const policyOne = await firestore.collection('policyOne');
        const data = await policyOne.get();
        const policyOneArray = [];
        if (data.empty){
            res.status(404).send('No policy one record found')
        }else{
            data.forEach(doc =>{
                const PolicyOne = new policyOnes(
                    doc.id,
                    doc.data().policyOnePrice
                );
                policyOneArray.push(PolicyOne);
            });
            res.send(policyOneArray);
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const getOnePolicyOne= async(req,res,next) => {
    try{
        const id = req.params.id;
        const policyOne = await firestore.collection('policyOne').doc(id);
        const data = await policyOne.get();
        if (!data.exists){
            res.status(404).send('No policy one record found')
        }else{
            res.send(data.data());
        }
    }catch(error){
        res.status(400).send(error.message);
    }
}

const updatePolicyOne= async(req,res,next) => {
    try{
        const id = req.params.id;
        const data = req.body;

        const policyOne = await firestore.collection('policyOne').doc(id);
        await policyOne.update(data);
        res.status(200).send('Policy one data updated successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}

const deletePolicyOne = async(req,res,next) => {
    try{
        const id = req.params.id;


        await firestore.collection('policyOne').doc(id).delete();
        res.status(200).send('Policy one data deleted successfully');

    }catch(error){
        res.status(400).send(error.message);
    }
}


module.exports = {
    addPolicy,
    getAllPolicyOne,
    updatePolicyOne,
    deletePolicyOne,
    getOnePolicyOne

}