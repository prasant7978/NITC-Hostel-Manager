var AdminModel = require("../../models/adminModel");

module.exports = async(req,res)=>{
    if(req.userType == "Admin"){
        var adminModel = new AdminModel();
        adminModel.findAdmin(req.username).then(function(admin){
            if(admin){
                res.status(200).send(JSON.stringify(admin));

            }else{
                res.status(500).send(JSON.stringify(null));
                console.log("Could not get admin");
            }
        })
    }else{
        res.status(400).send(JSON.stringify(null));
        console.log("user type is not admin");
    }
}