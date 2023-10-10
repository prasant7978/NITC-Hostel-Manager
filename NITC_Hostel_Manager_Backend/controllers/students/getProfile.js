const StudentModel = require('../../models/studentModel')

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.find(req.username).then(function(student){
        if(student != null){
            console.log("student profile found for dashboard");
            console.log(student);
            res.status(200).send(JSON.stringify(student));
        }
        else{
            console.log("student profile not found for dashboard");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(err){
        console.log("Error in getting student profile for dashboard: " + err.message);
        res.status(500).send(JSON.stringify(null));
    })
}