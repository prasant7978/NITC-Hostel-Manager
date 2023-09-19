var StudentModel = require('../../models/studentModel');

module.exports = async(req,res)=>{
    console.log("user = "+req.user);
    var user = req.user;
    if(!user){
        console.log("user not found in addstudent");
        res.status(500).send(JSON.stringify("Some error occurred"));
    }else{
        var studentModel = new StudentModel();
        studentModel.create(req.body).then(function(_){
            console.log("student created");
            res.status(200).send(JSON.stringify("Student created"));
        }).catch(function(errStudent){
            console.log("Error when adding student");
            console.log(errStudent);
            res.status(500).send(JSON.stringify("Error when adding student"+errStudent));
        });
    }
}