const RoomModel = require('../../models/roomModel')

module.exports = async(req, res) => {
    const roomModel = new RoomModel()
    roomModel.createRoom(req.body.roomNumber, req.body.hostelID, req.body.roomType).then(function(addRoom){
        if(addRoom == true){
            console.log("room added")
            res.status(200).send(true)
        }
        else{
            console.log("room not added");
            res.status(500).send(false)
        }
    }).catch(function(err){
        console.log("Error in adding room: " + err.message);
        res.status(500).send(false)
    })
}