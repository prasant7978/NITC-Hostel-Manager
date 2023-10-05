var ComplaintModel = require("../../models/complaintModel");


module.exports = async(req,res)=>{
    if(req.userType == "Admin" || req.userType == "Warden"){
        var complaintModel = new ComplaintModel();
        complaintModel.getComplaints(req.username).then(function(complaintsCount){
            if(complaintsCount){
                res.status(200).send(JSON.stringify(complaintsCount));
                console.log("ComplaintsCount = "+complaintsCount);
            }else{
                res.status(500).send(JSON.stringify(0));
                console.log("ComplaintsCount is undefined");
            }
        }).catch(function(exc){
            res.status(500).send(JSON.stringify(0));
            console.log("Exception = "+exc);
        });   
    }else{
        res.status(400).send(JSON.stringify(0));
    }
}