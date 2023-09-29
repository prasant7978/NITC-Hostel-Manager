const db = require('../db/db_connection');
const jwt = require("jsonwebtoken")
const config = require("config")

module.exports = class Model{
    async getAllStudents(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students',async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("students :");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }

    async getBoys(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE GENDER=?',["Male"],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("students :");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }
    
    async getGirls(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE GENDER=?',["Female"],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("students :");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }
    async find(id){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE studentRoll=?', [id], async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("student found :");
                    console.log(result);
                    resolve(result)
                }
            })
        })
    }

    async findStudentByRollAndPassword(studentRoll, password){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE studentRoll = ? AND password = ?', [studentRoll, password], async(err, result) => {
                if(err || !result || result.length == 0){
                    console.log("Error : " + err)
                    reject(err)
                }
                else{
                    console.log("student found :")
                    console.log(result)
                    resolve(result[0])
                }
            })
        })
    }

    async createStudent(data){
        return new Promise((resolve, reject) => {
            db.query('INSERT INTO students SET ?', [data], async(err, result) => {
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("student added :");
                    console.log(result[0]);
                    resolve(result)
                }
            });
        })
    }

    async updateStudent(id, data) {
        return new Promise((resolve, reject) => {
          db.query('UPDATE students SET ? WHERE studentRoll=?', [data, id], (err, result) => {
            if (err) {
              console.log("Error : " + err);
              reject(err); // Reject the promise with the error
            } else {
              console.log("student updated :");
              console.log(result);
              resolve(result); // Resolve the promise with the result
            }
          });
        });
      }

    async deleteStudent(id){
        return new Promise((resolve, reject) => {
            db.query('DELETE FROM students WHERE studentRoll = ?', [id], async(err, result) => {
                if(err){
                    console.log("Error : "+err);
                    reject(err);
                }else{
                    console.log("student deleted :");
                    console.log(result);
                    resolve(result);
                }
            })
        })
    }

    async generateAuthToken(id, userType){
        const jwtGenerated = jwt.sign({username: id, userType: userType}, config.get('hostel_manager_private_key'))
        return jwtGenerated
    }

}