var BillModel = require("../../models/billModel");
var HostelModel = require("../../models/hostelModel");
var StudentModel = require("../../models/studentModel");

module.exports = async(req,res)=>{
    if(req.userType == "Warden"){
        const charges = req.query.charges;
        const hostelID = req.query.hostelID;
        const billModel = new BillModel();
        var studentModel = new StudentModel();
        const monthForBill = req.query.monthForBill;
        const yearForBill = req.query.yearForBill;
        studentModel.getStudentsRollOfHostel(hostelID).then(function(studentsRoll){
            if(studentsRoll){
                var generatedFlag = true;
                
                for(let i = 0;i<studentsRoll.length;i++){
                    billModel.generateBill({
                        "studentRoll":studentsRoll[i],
                        "amount":charges,
                        "paid":false,
                        "month":monthForBill,
                        "year":yearForBill,
                        "hostelID":hostelID
                    }).then(function(generated){
                        if(!generated || generated == false){
                            generatedFlag = false;
                        }
                    }).catch(function(excGeneration){
                        console.log(excGeneration);
                        generatedFlag = false;
                    });
                    if(!generatedFlag){
                        console.log("Bill is not generated for studentsRoll[i] = "+studentsRoll[i]);
                        break;
                    }
                }
                if(!generatedFlag){
                    res.status(500).send(false);
                }else{
                    var hostelModel = new HostelModel();
                    hostelModel.addDues(charges*studentsRoll.length).then(function(duesAdded){
                        if(duesAdded && duesAdded == true){
                            console.log("Dues added");
                            res.status(200).send(true);
                        }else{
                            res.status(500).send(false);
                            console.log("Dues are not added");
                        }
                    });
                }

            }else{
                res.status(500).send(false);
                console.log("StudentsRoll of hostel not found");
            }
        })
        
    }else{
        console.log("User is not warden");
        res.status(400).send(false);
    }
}