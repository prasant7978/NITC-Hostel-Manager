const StudentModel = require('../../../models/studentModel')

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.find(req.query.studentRoll).then(function(student){
        if(student != null){
            console.log("student found");
            console.log(student);
            res.status(200).send(JSON.stringify(student));
        }
        else{
            console.log("student not found");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(err){
        console.log("Error in getting student: " + err.message);
        res.status(500).send(JSON.stringify(null));
    })
}