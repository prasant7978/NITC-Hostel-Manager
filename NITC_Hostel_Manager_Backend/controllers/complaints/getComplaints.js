var ComplaintModel = require("../../models/complaintModel");


module.exports = async(req,res)=>{
    if(req.userType == "Warden"){
        var complaintModel = new ComplaintModel();
        complaintModel.getComplaints(req.query.hostelID).then(function(complaints){
            if(complaints){
                res.status(200).send(JSON.stringify(complaints));
                console.log("complaints = "+complaints);
            }else{
                res.status(500).send(JSON.stringify(null));
                console.log("complaints is undefined");
            }
        }).catch(function(exc){
            res.status(500).send(JSON.stringify(null));
            console.log("Exception = "+exc);
        });   
    }else{
        res.status(400).send(JSON.stringify(null));
    }
}