const WardenModel = require('../../models/wardenModel')

module.exports = async(req, res) => {
    var wardenModel = new WardenModel()
    console.log("email: " + req.username);
    wardenModel.findWarden(req.username).then(function(warden){
        if(warden != null){
            console.log("warden profile found for dashboard");
            console.log(warden);
            res.status(200).send(JSON.stringify(warden));
        }
        else{
            console.log("warden profile not found for dashboard");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(err){
        console.log("Error in getting warden profile for dashboard: " + err.message);
        res.status(500).send(JSON.stringify(null));
    })
}