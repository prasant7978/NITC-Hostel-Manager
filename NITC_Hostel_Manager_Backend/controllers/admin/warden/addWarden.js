var WardenModel = require("../../../models/wardenModel");
var HostelModel = require("../../../models/hostelModel");


module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(false);
        console.log("User is not admin to add a new warden");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.createWarden(req.body).then(function(result){
            if(result == true){
                var hostelModel = new HostelModel();
                hostelModel.assignWarden(req.body.email,req.body.hostelID).then(function(assigned){
                    if(assigned == true){
                        res.status(200).send(true);
                        console.log("Warden is added");
                    }else{
                        res.status(500).send(false);
                        console.log("Warden is not assigned");
                    }
                }).catch(function(excAssigned){
                    console.log(excAssigned);
                    res.status(500).send(false);
                });
            }else{
                res.status(500).send(false);
                console.log("Warden is not added");
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(false);
        });
    }
}