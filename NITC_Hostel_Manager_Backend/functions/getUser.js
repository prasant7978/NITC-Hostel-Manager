var AdminModel = require("../models/adminModel");
var StudentModel = require("../models/studentModel");
var WardenModel = require("../models/wardenModel");

module.exports = async(username,userType)=>{
    if(userType == "Admin"){
        var adminModel = new AdminModel();
        var admin = null;
        await adminModel.findAdmin(username).then(function(result){
            if(result) admin = result;
        }).catch(function(exc){
            console.log(exc);
        });
        return admin;
    }else if(userType == "Warden"){
        var wardenModel = new WardenModel();
        var warden = null;
        await wardenModel.findWarden(username).then(function(result){
            if(result) warden = result;
        }).catch(function(exc){
            console.log(exc);
        });
        return warden;
    }else{
        var studentModel = new StudentModel();
        var student = null;
        await studentModel.find(username).then(function(result){
            if(result) student = result;
        }).catch(function(exc){
            console.log(exc);
        });
        return student;
    }
}
