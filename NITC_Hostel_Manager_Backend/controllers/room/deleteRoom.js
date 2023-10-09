const RoomModel = require('../../models/roomModel')

module.exports = async(req, res) => {
    const roomModel = new RoomModel()
    roomModel.deleteRoom(req.query.roomID, req.query.hostelID).then(function(deleteRoom){
        if(deleteRoom == true){
            console.log("room deleted")
            res.status(200).send(true)
        }
        else{
            console.log("room not deleted");
            res.status(500).send(false)
        }
    }).catch(function(err){
        console.log("Error in deleting room: " + err.message);
        res.status(500).send(false)
    })
}