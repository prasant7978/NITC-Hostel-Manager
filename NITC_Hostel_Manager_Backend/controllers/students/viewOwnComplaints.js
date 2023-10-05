var ComplaintModel = require("../../models/complaintModel");


module.exports = async(req,res)=>{
    if(req.userType == "Student"){
        
        var complaintModel  = new ComplaintModel();
        complaintModel.getOwnComplaints(req.username).then(function(result){
            if(result != undefined){
                res.status(200).send(JSON.stringify(result));
                console.log("complaints are = "+result);
            }else{
                console.log("result is undefined on getting own complaints");
                res.status(500).send(JSON.stringify(null));
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(JSON.stringify(null));
        });
        
    }else{
        console.log("User is not student");
        res.status(400).send(JSON.stringify(null));
    }
}