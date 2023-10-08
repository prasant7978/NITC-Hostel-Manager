var WardenModel = require("../../../models/wardenModel");

module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(false);
        console.log("User is not admin to add a new warden");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.createWarden(req.body).then(function(result){
            if(result){

                res.status(200).send(true);
                console.log("Warden is added");
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