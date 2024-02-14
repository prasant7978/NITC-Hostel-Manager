var WardenModel = require("../../../models/wardenModel");
var HostelModel = require("../../../models/hostelModel")
module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(false);
        console.log("User is not admin to delete warden");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.deleteWarden(req.query.wardenEmail).then(function(result){
            if(result == true){
                if(req.query.hostelID && req.query.hostelID != null ){
                    var hostelModel = new HostelModel();
                    hostelModel.removeWarden(req.body.hostelID).then(function(assigned){
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
                    res.status(200).send(true);
                    console.log("Warden is deleted");
                }
            }else{
                res.status(500).send(false);
                console.log("Warden is not deleted");
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(false);
        });
    }
}