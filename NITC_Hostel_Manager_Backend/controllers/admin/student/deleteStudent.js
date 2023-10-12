const StudentModel = require('../../../models/studentModel')

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.deleteStudent(req.query.studentRoll).then(function(result){
        console.log("student deleted successfully");
        res.status(200).send(true)
    }).catch(function(err){
        console.log("Error in deleting student: " + err.message);
        res.status(500).send(false);
    })
}