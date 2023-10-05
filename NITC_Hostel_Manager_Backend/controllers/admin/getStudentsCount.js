const StudentModel = require("../../models/studentModel")

module.exports = async(req, res) => {
    var studentModel = new StudentModel()
    studentModel.getBoysCount().then(function(boys) {
        var boysCount = boys;
        studentModel.getGirlsCount().then(function(girls){
            var girlsCount = girls;
            var result = {
                "BoysCount":boysCount,
                "GirlsCount":girlsCount
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