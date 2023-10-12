var db = require("../db/db_connection");

module.exports = class Model{
    async getAllBill(){
        return new Promise((resolve,reject) => {
            db.query('SELECT * FROM bills', async(err, result) => {
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    console.log("All Bills = ");
                    console.log(result);
                    resolve(result);
                }
            });
        });
    }

    async getStudentBills(studentRoll){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM bills WHERE studentRoll = ?', [studentRoll], async(err, result) => {
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    console.log("All Own Bills = ");
                    console.log(result);
                    resolve(result);
                }
            });
        });
    }

    async getStudentUnpaidBills(studentRoll){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM bills WHERE studentRoll = ? AND paid=0', [studentRoll], async(err, result) => {
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    console.log("All Own Bills = ");
                    console.log(result);
                    resolve(result);
                }
            });
        });
    }

    async generateBill(bill){
        return new Promise((resolve,reject)=>{
            db.query('INSERT INTO bills SET ?', [bill], async(err, added) => {
                if(err || !added){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(true);
                }
            });
        });
    }

    

    async payDues(studentRoll,billID,paymentDate,paymentID,paymentTime){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE bills SET paid = ?,paymentDate=?,paymentID=? WHERE studentRoll = ? AND billID = ?', [true,paymentDate,paymentID,studentRoll, billID], async(err, paid) => {
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    console.log("dues paid and bill updated");
                    resolve(true);
                }
            });
        });
    }
}