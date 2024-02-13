const RoomModel = require('../../models/roomModel')

module.exports = async(req, res) => {
    const roomModel = new RoomModel()
    roomModel.getAllRoomsFromTo(req.query.hostelID,req.query.start,req.query.end).then(function(rooms){
        if(rooms.length > 0 && rooms != null){
            console.log("all rooms are retrieved")
            res.status(200).send(JSON.stringify(rooms))
        }
        else{
            console.log("no rooms are found");
            res.status(500).send(JSON.stringify(null))
        }
    }).catch(function(err){
        console.log("Error in getting all rooms: " + err.message);
        res.status(500).send(JSON.stringify(null))
    })
}