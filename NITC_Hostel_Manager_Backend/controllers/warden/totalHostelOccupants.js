var StudentModel = require("../../models/studentModel");

module.exports = async(req,res)=>{
    console.log("counting students in hostel in totalHostelOccupants");
    if(req.userType != "Warden"){
        var studentModel = new StudentModel();
        studentModel.countHostelOccupants(req.username).then(function(result){
            if(result >= 0){
                res.status(200).send(JSON.stringify(result));
                console.log("total occupants = " + result);
            }else{
                res.status(500).send(JSON.stringify(null));
                console.log("result is undefined when getting occupants count");
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(JSON.stringify(null));
        })
    }else{
        res.status(400).send(JSON.stringify(null));
        console.log("User type is not warden");
    }
}