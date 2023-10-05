const PaymentModel = require('../../models/paymentModel')

module.exports = async(req, res) => {
    var payment = {
        "studentRoll": req.username,
        "status": "success",
        "amount": req.body.amount,
        "date": req.body.date,
        "time": req.body.time
    }
    
    var paymentModel = new PaymentModel();
    paymentModel.issuePayment(payment).then(function(result){
        if(result == true){
            res.status(200).send(true)
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