import firebase from 'firebase/compat/app';
import "firebase/compat/storage";

const firebaseConfig = {
    apiKey: "AIzaSyBUb6DBvQ4TPYpopyiV0OTKVUSRMcW7-7Y",
    authDomain: "csseproject-cccb2.firebaseapp.com",
    databaseURL: "https://csseproject-cccb2-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "csseproject-cccb2",
    storageBucket: "csseproject-cccb2.appspot.com",
    messagingSenderId: "684499009729",
    appId: "1:684499009729:web:93424c62d11792e3235440"
};

firebase.initializeApp(firebaseConfig);

const storage = firebase.storage();

export { storage, firebase as default };