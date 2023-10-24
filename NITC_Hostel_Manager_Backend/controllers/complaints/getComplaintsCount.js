var ComplaintModel = require("../../models/complaintModel");

module.exports = async(req,res)=>{
    if(req.userType == "Admin" || req.userType == "Warden"){
        var complaintModel = new ComplaintModel();
        complaintModel.getPendingComplaintsCount(req.query.hostelID).then(function(complaintsCount){
            if(complaintsCount){
                console.log(complaintsCount['COUNT(*)']);
                res.status(200).send(JSON.stringify(complaintsCount['COUNT(*)']));
                console.log("ComplaintsCount = "+complaintsCount['COUNT(*)']);
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