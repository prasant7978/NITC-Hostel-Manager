var ComplaintModel = require("../models/complaintModel");

module.exports = async(req,res)=>{
    if(req.userType == "Admin" || req.userType == "Warden"){
        var complaintModel = new ComplaintModel();
        complaintModel.rejectComplaint(req.query.complaintID,req.username).then(function(result){
            if(result && result == true){
                res.status(200).send(true);
            }else{
                console.log("result is not true");
                res.status(500).send(false);
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(false);
        })
    }else{
        console.log("User is neither admin nor warden");
        res.status(400).send(false);
    }
}