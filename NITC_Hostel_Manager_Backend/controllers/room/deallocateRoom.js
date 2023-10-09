const RoomModel = require('../../models/roomModel')

module.exports = async(req, res) => {
    const roomModel = new RoomModel()
    roomModel.deallocateRoom(req.username, req.query.roomID, req.query.hostelID).then(function(deallocateRoom){
        if(deallocateRoom == true){
            console.log("room deallocated")
            res.status(200).send(true)
        }
        else{
            console.log("room not deallocated");
            res.status(500).send(false)
        }
    }).catch(function(err){
        console.log("Error in deallocating room: " + err.message);
        res.status(500).send(false)
    })
}