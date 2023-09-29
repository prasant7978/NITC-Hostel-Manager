const StudentModel = require("../../models/studentModel")

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.getAllStudents().then(function(boys) {
        var boysCount = boys.length;
        studentModel.getGirls().then(function(girls){
            var girlsCount = girls.length;
            var result = {
                boysCount:boysCount,
                girlsCount:girlsCount
            };
            res.status(200).send(JSON.stringify(result));
        }).catch(function(err){
            
        console.log(err);
        res.status(500).send(JSON.stringify(err))
        }) 
    }).catch(function(err){
        console.log(err);
        res.status(500).send(JSON.stringify(err))
    })
}