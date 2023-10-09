const HostelModel = require('../../../models/hostelModel')

module.exports = async(req, res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        hostelModel.updateHostel(req.body).then(function(updatededHostel){
            if(updatedHostel == true){
                console.log("hostel is updated");
                res.status(200).send(true)
            }else{
                console.log("Hostel is not created");
                res.status(500).send(false);
            }
        }).catch(function(error){
            console.log("Error in updating hostel: " + error);
            res.status(500).send(false);
        });
    }else{
        console.log("User is not admin");
        res.status(400).send(false);
    }
}