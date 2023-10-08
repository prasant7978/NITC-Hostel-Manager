var WardenModel = require("../../../models/wardenModel");

module.exports = async(req,res)=>{
    if(req.userType != "Admin"){
        res.status(400).send(JSON.stringify(null));
        console.log("User is not admin to get wardens");
    }else{
        var wardenModel = new WardenModel();
        wardenModel.get_all().then(function(wardens){
            if(wardens){
                res.status(200).send(JSON.stringify(wardens));
                console.log("Wardens are returned");
            }else{
                res.status(200).send(JSON.stringify([]));
                console.log("Wardens are undefined");
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(JSON.stringify(null));
        });
    }
}