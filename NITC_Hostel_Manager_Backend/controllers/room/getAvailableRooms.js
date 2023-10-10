const RoomModel = require('../../models/roomModel')

module.exports = async(req, res) => {
    const roomModel = new RoomModel()
    console.log(req.query.hostelID);
    roomModel.getAvailableRooms(req.query.hostelID).then(function(rooms){
        if(rooms){
            console.log("all available rooms are retrieved")
            res.status(200).send(JSON.stringify(rooms))
        }
        else{
            console.log("no available rooms are found");
            res.status(500).send(JSON.stringify(null))
        }
    }).catch(function(err){
        console.log("Error in getting all available rooms: " + err.message);
        res.status(500).send(JSON.stringify(null))
    })
}