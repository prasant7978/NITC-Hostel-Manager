const HostelModel = require('../../../models/hostelModel')
var RoomModel = require("../../../models/roomModel")

module.exports = async(req, res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        hostelModel.addHostel(req.body).then(function(addedHostel){
            if(addedHostel == true){
                console.log("hostel is created");
                var hostelID = req.body.hostelID;
                var capacity = req.body.capacity;
                var roomModel = new RoomModel();
                roomModel.createRooms(hostelID, req.body.roomType, capacity).then(function(addedRooms){
                    if(addedRooms == true){
                        res.status(200).send(true);
                        console.log("rooms are added to the hostel");
                    }
                }).catch(function(exc){
                    res.status(500).send(false);
                    console.log("rooms are not added to the hostel");
                    console.log(exc);
                });
            }else{
                console.log("Hostel is not created");
                res.status(500).send(false);
            }
        });
    }else{
        console.log("User is not admin");
        res.status(400).send(false);
    }
}