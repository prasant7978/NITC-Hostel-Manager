const PaymentModel = require('../../models/paymentModel')
const BillModel = require("../../models/billModel");
const StudentModel = require("../../models/studentModel");


module.exports = async(req, res) => {
    var billID = req.body.billID;
    
    var paymentModel = new PaymentModel();
    var payment = req.body;
    delete payment['paymentID'];
    console.log(payment);
    paymentModel.issuePayment(payment).then(function(paymentAdded){
        if(paymentAdded){
            paymentModel.getPaymentID(billID,req.username).then(function(paymentID){
                if(paymentID){
                    var billModel = new BillModel();
                    billModel.payDues(req.username,billID,req.body.date,paymentID,req.body.time).then(function(result){
                        if(result == true) {
                            var studentModel = new StudentModel();
                            studentModel.payDues(req.body.amount,req.username).then(function(studentUpdated){
                                if(studentUpdated == true){
                                    res.status(200).send(true)
                                    console.log("\n\n\n\n\n student bill paid");
                                }else{
                                    res.status(500).send(false);
                                    console.log("Bill is not updated in student");        
                                }
                            }).catch(function(excStudent){
                                console.log(excStudent);
                                res.status(500).send(false);
                            });
                        }
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
            })
            
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