const PaymentModel = require('../../models/paymentModel')
const BillModel = require("../../models/billModel");

module.exports = async(req, res) => {
    var payment = {
        "studentRoll": req.username,
        "status": "success",
        "amount": req.body.amount,
        "date": req.body.date,
        "time": req.body.time
    }
    var billID = req.body.billID;
    
    var paymentModel = new PaymentModel();
    paymentModel.issuePayment(payment).then(function(result){
        if(result == true){
            var billModel = new BillModel();
            billModel.payDues(req.username,billID).then(function(result){
                if(result) res.status(200).send(true)
                else {
                    res.status(500).send(false);
                    console.log("Bill is not paid");
                }
            }).catch(function(exc){
                console.log(exc);
                res.status(500).send(false);
            });
            console.log("Payment done");
        }
        else{
            res.status(500).send(false)
            console.log("Error in issuing payment");
        }
    }).catch(function(err){
        console.log("Error: " + err.message);
        res.status(500).send(false)
    })
}