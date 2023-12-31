var StudentModel = require('../../../models/studentModel');

module.exports = async(req,res)=>{
    console.log("user = " + req.body);
    var username = req.username;
    if(!username){
        console.log("user not found in addstudent");
        res.status(500).send(false);
    }else{
        var studentModel = new StudentModel();
        var newStudent = req.body;
        // delete newStudent.roomNumber;
        // console.log(newStudent);
        // newStudent = newStudent.toObject();
        studentModel.createStudent(newStudent).then(function(result){
            console.log("student created");
            res.status(200).send(true);
        }).catch(function(errStudent){
            console.log("Error when adding student");
            console.log(errStudent);
            res.status(500).send(false);
        });
    }
}