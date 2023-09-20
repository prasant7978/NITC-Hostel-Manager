const db = require("../db/db_connection");
const jwt = require("jsonwebtoken");
const config = require('config');

module.exports = async(req, res, next)=>{
    const token = req.headers["auth-token"];
    if(!token){
        console.log("No token sent");
        res.status(400).send(JSON.stringify(null));
    }else{
        console.log("Token = "+token);
        var decoded = jwt.verify(token,config.get("hostel_manager_private_key"));
        console.log("user = "+decoded);
        req.username = decoded.username;
        req.userType = decoded.userType;
        next();
    }
}