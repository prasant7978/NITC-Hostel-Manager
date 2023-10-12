var BillModel = require("../../models/billModel")

module.exports = async(req,res)=>{
    var billModel = new BillModel();
    billModel.getAllBill().then(function(bill){
        if(bill){
            res.status(200).send(JSON.stringify(bill));
            console.log("Bill fetched");
        }else{
            res.status(500).send(JSON.stringify(null));
            console.log("Bill is undefined or null");
        }
    }).catch(function(exc){
        res.status(500).send(JSON.stringify(null));
        console.log(exc);
    });
}