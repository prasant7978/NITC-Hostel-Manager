const WardenModel = require('../../../models/wardenModel')

module.exports = async(req, res) => {
    var wardenModel = new WardenModel()
    console.log("email: " + req.query.wardenEmail);
    wardenModel.findWarden(req.query.wardenEmail).then(function(warden){
        if(warden != null){
            console.log("warden details found for update");
            console.log(warden);
            res.status(200).send(JSON.stringify(warden));
        }
        else{
            console.log("warden details not found for update");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(err){
        console.log("Error in getting warden details for update: " + err.message);
        res.status(500).send(JSON.stringify(null));
    })
}