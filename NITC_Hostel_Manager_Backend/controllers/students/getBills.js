var BillModel = require("../../models/billModel")

module.exports = async(req,res)=>{
    var billModel = new BillModel();
    billModel.getStudentsBill(req.query.studentRoll).then(function(bill){
        if(bill){
            res.status(200).send(JSON.stringify(bill));
            console.log("Bill generated");
        }else{
            res.status(500).send(JSON.stringify(null));
            console.log("Bill is undefined or null");
        }
    }).catch(function(exc){
        res.status(500).send(JSON.stringify(null));
        console.log(exc);
    });
}