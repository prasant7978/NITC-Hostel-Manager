const AdminModel = require('../models/adminModel');
const StudentModel = require('../models/studentModel');
// const Wardwn = require('../models/wardenModel');

module.exports = async(req,res,next) => {
    if(req.body.userType == "Admin"){
        console.log("verifying for admin = "+req.body);
        var adminModel = new AdminModel()
        await adminModel.findAdmin(req.body.username).then(function(admin){
            console.log("\n\n\n");
            console.log(admin);
            req.userType = req.body.userType;
            req.username = req.body.username;
            next(); 
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(false);
        });
    }
    else if(req.body.userType == "Student"){
        var studentModel = new StudentModel()
        var student = await studentModel.find(req.body.username)
        if(student){
            req.userType = req.body.userType;
            req.username = req.body.username;
            next();
        }
        else
            res.status(500).send(false);
    }
}