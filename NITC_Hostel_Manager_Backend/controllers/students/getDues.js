const StudentModel = require('../../models/studentModel')

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.getDues(req.username).then(function(result){
        if(result){
            console.log("Dues for the student found: " + result);
            res.status(200).send(JSON.stringify(result))
        }
        else{
            console.log("Error in getting dues for the student");
            res.status(500).send(null)
        }
    }).catch(function(err){
        console.log("Error: " + err.message);
        res.status(500).send(err.message)
    })
}