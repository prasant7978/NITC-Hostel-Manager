var db = require("../db/db_connection");

module.exports = class Model{
    

    async getAllPayments(){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM payments',async(exc,result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result){
                        resolve(result[0]);
                    }else reject("result is undefined");
                }
            });
        });
    }

    async getStudentPayments(studentRoll){
        db.query('SELECT * FROM payments WHERE studentRoll=?',[studentRoll],async(exc,result)=>{
            if(exc){
                console.log("Error : "+exc);
                reject(exc);
            }else{
                if(result){
                    resolve(result[0]);
                }else reject("result is undefined");
            }
        });
    }

    async issuePayment(payment){
        return new Promise((resolve,reject)=>{
            db.query('INSERT INTO payments SET ?',[payment],async(exc,result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result){
                        resolve(true);
                    }else reject("Not inserted without any exception");
                }
            });
        });
    }

}