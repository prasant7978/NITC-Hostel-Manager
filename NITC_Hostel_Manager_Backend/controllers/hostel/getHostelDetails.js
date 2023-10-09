const HostelModel = require('../../models/hostelModel')

module.exports = async(req, res)=>{
    var hostelModel = new HostelModel();

    hostelModel.getHostelDetails(req.query.hostelID).then(function(hostel){
        if(hostel && hostel != null){
            console.log("hostel details found");
            res.status(200).send(JSON.stringify(hostel));
        }else{
            console.log("hostel details not found");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(error){
        console.log("Error in getting hostel details: " + error);
        res.status(500).send(JSON.stringify(null));
    });
}