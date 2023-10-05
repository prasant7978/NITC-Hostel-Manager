var WardenModel = require("../../../models/wardenModel");

module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(false);
        console.log("User is not admin to update a warden");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.updateWarden(req.query.wardenEmail,req.body).then(function(result){
            if(result){
                console.log("After updating warden");
                console.log(result);
                res.status(200).send(true);
                console.log("Warden is updated");
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