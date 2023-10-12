var BillModel = require("../../models/billModel");
var HostelModel = require("../../models/hostelModel");
var StudentModel = require("../../models/studentModel");

module.exports = async(req, res)=>{
    if(req.userType == "Warden"){
        const amount = req.body.amount;
        const billMonth = req.body.billMonth;
        const billYear = req.body.billYear;
        const billType = req.body.billType;
        const paid = req.body.paid;
        const hostelID = req.body.hostelID;

        const billModel = new BillModel();
        var studentModel = new StudentModel();

        studentModel.getStudentsRollOfHostel(hostelID).then(function(studentsRoll){
            if(studentsRoll){
                var generatedFlag = true;
                
                for(let i = 0;i<studentsRoll.length;i++){
                    billModel.generateBill({
                        "studentRoll":studentsRoll[i].studentRoll,
                        "amount":amount,
                        "billType":billType,
                        "paid":paid,
                        "billMonth":billMonth,
                        "billYear":billYear,
                        "hostelID":hostelID
                    }).then(function(generated){
                        if(!generated || generated == false){
                            generatedFlag = false;
                        }else{
                            studentModel.addDues(amount,studentsRoll[i].studentRoll).then(function(added){
                                if(!added || added == false){
                                    generatedFlag = false;
                                }
                            }).catch(function(errStudentAdd){
                                generatedFlag = false;
                                console.log(errStudentAdd);
                            })
                        }
                    }).catch(function(excGeneration){
                        console.log(excGeneration);
                        generatedFlag = false;
                    });

                    if(!generatedFlag){
                        console.log("Bill is not generated for studentsRoll = "+studentsRoll[i]);
                        break;
                    }
                }

                if(!generatedFlag){
                    res.status(500).send(false);
                }
                else{
                    var hostelModel = new HostelModel();

                    hostelModel.addDues(amount*studentsRoll.length, hostelID).then(function(duesAdded){
                        if(duesAdded && duesAdded == true){
                            console.log("Dues added to the hostel");
                            res.status(200).send(true);
                        }else{
                            res.status(500).send(false);
                            console.log("Dues are not added to the hostel");
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