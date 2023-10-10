const WardenModel = require("../../../models/wardenModel")

module.exports = async(req, res) => {
    var wardenModel = new WardenModel()
    wardenModel.getWardensCount().then(function(wardens) {
        var wardensCount = wardens;
        res.status(200).send(JSON.stringify(wardensCount))
    }).catch(function(err){
        console.log(err);
        res.status(500).send(JSON.stringify(err))
    });
}