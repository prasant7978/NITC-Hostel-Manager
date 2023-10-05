const StudentModel = require('../../models/studentModel')

module.exports = async (req, res) => {
    var studentModel = new StudentModel()
    studentModel.updateStudent(req.body.username, req.body).then(function(result){
        console.log("Student updated successfully")
        res.status(200).send(JSON.stringify(result))
    }).catch(function(err){
        console.log("Error while updating student: " + err)
        res.status(500).send(JSON.stringify("Error while updating student: " + err))
    })
}