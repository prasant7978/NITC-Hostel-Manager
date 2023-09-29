const AdminModel = require('../models/adminModel');
const StudentModel = require('../models/studentModel');
const WardenModel = require('../models/wardenModel');

module.exports = async(req, res, next) => {
    if(req.body.userType == "Admin"){
        console.log("verifying for admin = "+req.body);
        var adminModel = new AdminModel()
        await adminModel.findAdmin(req.body.username).then(function(admin){
            console.log(admin);
            req.userType = req.body.userType;
            req.username = req.body.username;
            next(); 
        }).catch(function(exc){
            console.log(exc);
            res.status(400).send(false);
        });
    }
    else if(req.body.userType == "Student"){
        console.log("verifying for student = " + req.body);
        var studentModel = new StudentModel()
        await studentModel.find(req.body.username).then(function(student){
            console.log(student);
            req.userType = req.body.userType;
            req.username = req.body.username;
            next(); 
        }).catch(function(err){
            console.log("Error in verifying username: " + err.message);
            res.status(400).send(false);
        })
    }else{
        console.log("Verifying for warden");
        var wardenModel = new WardenModel();
        await wardenModel.findWarden(req.body.username).then(function(warden){
            console.log(warden);
            req.userType = req.body.userType;
            req.username = req.body.username;
        }).catch(function(exc){
            console.log("error in verifying warden"+exc);
            res.status(400).send(false);
        });
    }
}