const HostelModel = require('../../../models/hostelModel')
const RoomModel = require('../../../models/roomModel')

module.exports = async(req, res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        hostelModel.deleteHostel(req.query.hostelID).then(function(result){
            if(result == true){
                console.log("hostel: " + req.query.hostelID + " is deleted");
                var roomModel = new RoomModel();
                roomModel.deleteAllRooms(req.query.hostelID).then(function(deleteAllRooms){
                    if(deleteAllRooms == true){
                        console.log("All rooms deleted");
                        res.status(200).send(true)
                    }
                    else{
                        console.log("Rooms are not deleted");
                        res.status(200).send(false)
                    }
                }).catch(function(error){
                    console.log("Error in deleting rooms: " + error);
                    res.status(500).send(false);
                })
            }
            else{
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