const StudentModel = require("../../../models/studentModel")

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.getBoys().then(function(result) {
        console.log("Successfully retrieved all students...")
        console.log(result);
        res.status(200).send(JSON.stringify(result))
    }).catch(function(err){
        console.log(err);
        res.status(500).send(JSON.stringify(err))
    });
}