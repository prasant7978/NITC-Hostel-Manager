var WardenModel = require("../../../models/wardenModel");

module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(false);
        console.log("User is not admin to delete warden");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.deleteWarden(req.query.wardenEmail).then(function(result){
            if(result == true){
                res.status(200).send(true);
                console.log("Warden is deleted");
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