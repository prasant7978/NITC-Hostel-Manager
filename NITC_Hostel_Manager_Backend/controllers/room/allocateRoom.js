const RoomModel = require('../../models/roomModel')
var HostelModel = require('../../models/hostelModel')
var StudentModel = require('../../models/studentModel');
var getUser = require("../../functions/getUser")


module.exports = async(req, res) => {
    var hostelModel = new HostelModel();
    var studentDetails = await getUser(req.username,req.userType);
    hostelModel.decrementOccupants(studentDetails['hostelID']).then(function(decremented){
        if(decremented == true){
               
    hostelModel.incrementOccupants(req.query.hostelID).then(function(incremented){
        if(incremented == true){
            const roomModel = new RoomModel();
            roomModel.deallocateRoom(req.username,studentDetails['roomNumber'],studentDetails['hostelID']).then(function(deallocated){
                if(deallocated == true){
            roomModel.allocateRoom(req.username, req.query.roomNumber, req.query.hostelID).then(function(allocateRoom){
        if(allocateRoom == true){
                    var studentModel = new StudentModel();
                    studentModel.allocateRoom(req.query.roomNumber,req.query.hostelID,req.username).then(function(allocateToStudent){
                        if(allocateToStudent == true){
                            console.log("room allocated")
                            res.status(200).send(true)
                        }else{
                            console.log("room not allocated");
                            res.status(500).send(false)
                        }
                    }).catch(function(errStudent){
                        console.log(errStudent);
                        res.status(500).send(false);
                    });
                }else{
                    console.log("room not allocated");
                    res.status(500).send(false)
                }
            }).catch(function(err){
                console.log("Error in allocating room: " + err.message);
                res.status(500).send(false)
            })     
        }
    }).catch(function(excDeallocated){
        console.log("Error in deallocating room: " + excDeallocated.message);
        res.status(500).send(false)
    })
        }
    }).catch(function(excHostelIncremented){
        console.log("Error in incrementing hostel occupants: " + excHostelIncremented.message);
        res.status(500).send(false)
    })
        }
        else{
            console.log("room not allocated");
            res.status(500).send(false)
        }
    }).catch(function(excHostel){
        console.log("Error in decrementing occupants: " + excHostel.message);
        res.status(500).send(false)
    })
}