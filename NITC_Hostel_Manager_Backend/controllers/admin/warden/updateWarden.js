var WardenModel = require("../../../models/wardenModel");
var HostelModel = require("../../../models/hostelModel");

module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(false);
        console.log("User is not admin to update a warden");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.updateWarden(req.query.wardenEmail, req.body).then(function(result){
            if(result == true){
                // if(req.body.hostelID != req.query.hostelID){
                    console.log("hostelID are not same");
                    var hostelModel = new HostelModel();
                        hostelModel.removeWarden(req.query.hostelID).then(function(removed){
                            if(removed == true){
                                hostelModel.assignWarden(req.query.wardenEmail,req.body.hostelID).then(function(assigned){
                                    if(assigned == true){
                                        res.status(200).send(true);
                                        console.log("Warden is removed");
                                    }else{
                                        res.status(500).send(false);
                                        console.log("Warden is not removed");
                                    }
                                }).catch(function(excAssigned){
                                    console.log(excAssigned);
                                    res.status(500).send(false);
                                });
                            }else{
                                res.status(500).send(false);
                                console.log("Warden is not removed");
                            }
                        }).catch(function(excAssigned){
                            console.log(excAssigned);
                            res.status(500).send(false);
                        });
                // }else{
                //     console.log("hostelID are  same");
                //     console.log("Sending updated warden");
                //     // console.log(result);
                //     res.status(200).send(true);
                // }
            }else{
                res.status(500).send(false);
                console.log("Warden is not updated");
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(false);
        });
    }
}