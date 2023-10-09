const RoomModel = require('../../models/roomModel')

module.exports = async(req, res) => {
    const roomModel = new RoomModel()
    roomModel.allocateRoom(req.username, req.query.roomID, req.query.hostelID).then(function(allocateRoom){
        if(allocateRoom == true){
            console.log("room allocated")
            res.status(200).send(true)
        }
        else{
            console.log("room not allocated");
            res.status(500).send(false)
        }
    }).catch(function(err){
        console.log("Error in allocating room: " + err.message);
        res.status(500).send(false)
    })
}