var HostelModel = require("../../models/hostelModel");

module.exports = async(req,res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        hostelModel.addHostel(req.body).then(function(result){
            if(result == true){
                console.log("hostel is added");
                var hostelID = req.body.hostelID;
                
            }else{
                console.log("Hostel is not created");
                res.status(500).send(false);
            }
        })
    }else{
        console.log("User is not admin");
        res.status(400).send(false);
    }
}