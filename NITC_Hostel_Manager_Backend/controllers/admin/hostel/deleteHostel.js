const HostelModel = require('../../../models/hostelModel')

module.exports = async(req, res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        hostelModel.deleteHostel(req.query.hostelID).then(function(result){
            if(result == true){
                console.log("hostel is deleted");
                res.status(200).send(true)
            }else{
                console.log("Hostel is not deleted");
                res.status(500).send(false);
            }
        }).catch(function(error){
            console.log("Error in deleting hostel: " + error);
            res.status(500).send(false);
        });
    }else{
        console.log("User is not admin");
        res.status(400).send(false);
    }
}