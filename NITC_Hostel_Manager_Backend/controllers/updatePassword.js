var WardenModel = require('../models/wardenModel');
var StudentModel = require("../models/studentModel");

module.exports = async(req,res)=>{
    if(req.userType == "Warden"){
        var wardenModel = new WardenModel();
        wardenModel.updatePassword(req.query.newPassword,req.username).then(function(result){
            if(result == true){
                res.status(200).send(true);
                console.log("Updated password");
            }else{
                console.log("COuld not update password");
                res.status(500).send(false);
            }
        }).catch(function(errWarden){
            console.log(errWarden);
            res.status(500).send(false);
        });
    }else if(req.userType == "Student"){
        var studentModel = new StudentModel();
        studentModel.updatePassword(req.query.newPassword,req.username).then(function(resultStudent){
            if(resultStudent == true){
                res.status(200).send(true);
                console.log("Updated password");
            }else{
                console.log("COuld not update password");
                res.status(500).send(false);
            }
        }).catch(function(errStudent){
            console.log(errStudent);
            res.status(500).send(false);
        });
    }
}