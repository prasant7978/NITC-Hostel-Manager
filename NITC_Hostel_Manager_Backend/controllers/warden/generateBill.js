var StudentModel = require("../../models/studentModel")

module.exports = async(req,res)=>{
    if(req.userType != "Warden"){
        var studentModel = new StudentModel();
        studentModel.generateBill()
    }else{
        res.status(400).send(false);
        console.log("User type is not warden");
    }
}