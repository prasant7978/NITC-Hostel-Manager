var HostelModel = require("../../models/hostelModel");

module.exports = async(req,res)=>{
    if(req.username){
        var hostelModel = new HostelModel();
        hostelModel.getHostelsNames().then(function(hostelsNames){
            res.status(200).send(JSON.stringify(hostelsNames));
            console.log(hostelsNames);
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(JSON.stringify(null));
        });
    }else{
        res.status(400).send(JSON.stringify(null));
    }
}