var HostelModel = require("../../models/hostelModel");

module.exports = async(req,res)=>{
    console.log("students in hostel in HostelOccupants");
    if(req.userType == "Warden"){
        var hostelModel = new HostelModel();
        hostelModel.getOccupants(req.query.hostelID).then(function(result){
            if(result){
                res.status(200).send(JSON.stringify(result));
                console.log("all occupants = " + result);
            }else{
                res.status(500).send(JSON.stringify(null));
                console.log("result is undefined when getting occupants ");
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