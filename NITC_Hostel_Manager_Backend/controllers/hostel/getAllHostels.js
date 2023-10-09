const HostelModel = require('../../models/hostelModel')

module.exports = async(req, res)=>{
    var hostelModel = new HostelModel();

    hostelModel.getAllHostel().then(function(hostels){
        if(hostels && hostels != null){
            console.log("all hostels retrieved");
            res.status(200).send(JSON.stringify(hostels));
        }else{
            console.log("no hostels found");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(error){
        console.log("Error in getting all hostels: " + error);
        res.status(500).send(JSON.stringify(null));
    });
}