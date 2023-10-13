const BillModel = require('../../models/billModel')

module.exports = async(req, res) => {
    var billModel = new BillModel()

    billModel.getBillsCount().then(function(count){
        console.log("Bills count retrieved: " + count);
        res.status(200).send(JSON.stringify(count))
    }).catch(function(err){
        console.log("Error in getting bills count: " + err.message)
        res.status(500).send(JSON.stringify(0));
    })
}