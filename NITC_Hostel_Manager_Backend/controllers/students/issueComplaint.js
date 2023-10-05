var ComplaintModel = require("../../models/complaintModel");


module.exports = async(req,res)=>{
    if(req.userType == "Student"){
        if(req.body){
            var complaintModel  = new ComplaintModel();
            complaintModel.issueComplaint(req.body).then(function(result){
                if(result != undefined && result == true){
                    res.status(200).send(true);
                    console.log("Complaint issued");
                }else{
                    console.log("result is undefined in issueing complaint");
                    res.status(500).send(false);
                }
            }).catch(function(exc){
                console.log(exc);
                res.status(500).send(false);
            });
        }else{
            res.status(500).send(false);
            console.log("Complaint is undefined");
        }
    }else{
        console.log("User is not student");
        res.status(400).send(JSON.stringify(false));
    }
}