var StudentModel = require('../../../models/studentModel');

module.exports = async(req,res)=>{
    console.log("user = " + req.body);
    var username = req.username;
    if(!username){
        console.log("user not found in addstudent");
        res.status(500).send(JSON.stringify("Some error occurred"));
    }else{
        var studentModel = new StudentModel();
        studentModel.createStudent(req.body).then(function(result){
            console.log("student created");
            res.status(200).send(JSON.stringify(result));
        }).catch(function(errStudent){
            console.log("Error when adding student");
            console.log(errStudent);
            res.status(500).send(JSON.stringify("Error when adding student"+errStudent));
        });
    }
}