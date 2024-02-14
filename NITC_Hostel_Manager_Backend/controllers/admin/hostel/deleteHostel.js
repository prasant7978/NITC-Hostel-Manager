const HostelModel = require('../../../models/hostelModel')
const RoomModel = require('../../../models/roomModel')
const WardenModel = require('../../../models/wardenModel')
const StudentModel = require("../../../models/studentModel");
module.exports = async(req, res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        hostelModel.deleteHostel(req.query.hostelID).then(function(result){
            if(result == true){
                console.log("hostel: " + req.query.hostelID + " is deleted");
                var roomModel = new RoomModel();
                roomModel.deleteAllRooms(req.query.hostelID).then(function(deleteAllRooms){
                    if(deleteAllRooms == true){
                        var studentModel = new StudentModel();
                        studentModel.removeHostel(req.query.hostelID).then(function(studentUpdated){
                            if(studentUpdated == true){
                                if(req.query.wardenEmail && req.query.wardenEmail != null){

                                    var wardenModel = new WardenModel();
                                    wardenModel.removeHostel(req.query.wardenEmail).then(function(removed){
                                        if(removed == true){
                                            console.log("hostel removed from warden");
                                            res.status(200).send(true)
                                        }else{
                                            console.log("error in removing hostel from warden");
                                            res.status(500).send(false);
                                        }
                                    }).catch(function(errRemoving){
                                        console.log(errRemoving);
                                        res.status(500).send(false);
                                    });
                                }else{
                                    console.log("All rooms deleted");
                                    res.status(200).send(true)
                                }
                            }    
                        }).catch(function(excStudent){
                            console.log(excStudent);
                            res.status(500).send(false);
                        });
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