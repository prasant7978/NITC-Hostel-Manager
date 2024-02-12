const db = require('../db/db_connection');
const jwt = require("jsonwebtoken")
const config = require("config")

module.exports = class Model{
    async updatePassword(newPassword,studentRoll){
        return new Promise((resolve, reject) => {
            db.query('UPDATE students set password=? where studentRoll=?',[newPassword,studentRoll],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err)
                }else{
                    resolve(true);
                }
            })
        })
    }

    async getAllStudents(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students',async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("students :");
                    // console.log(result);
                    resolve(result)
                }
            });
        })
    }

    async countHostelOccupants(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT count(*) FROM students WHERE hostelID = ?',[hostelID],async(exc,studentsCount)=>{
                if(exc){
                    console.log(exc);
                    reject(exc);
                }else{
                    // console.log("students count = ");
                    // console.log(studentsCount);
                    // console.log(studentsCount[0]['count(*)']);
                    resolve(studentsCount[0]['count(*)']);
                }
            });
        });
    }
    async getBoys(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE gender=?',["Male"],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("students :");
                    // console.log(result);
                    resolve(result)
                }
            });
        })
    }
    
    async getBoysCount(){
        return new Promise((resolve, reject) => {
            db.query('SELECT count(*) FROM students WHERE gender=?',["Male"],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("Boys total :");
                    // console.log(result[0]["count(*)"]);
                    resolve(result[0]["count(*)"])
                }
            });
        })
    }
    
    async getGirls(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE gender=?',["Female"],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("students :");
                    // console.log(result);
                    resolve(result)
                }
            });
        });
    }
    
    async getGirlsCount(){
        return new Promise((resolve, reject) => {
            db.query('SELECT count(*) FROM students WHERE gender=?',["Female"],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("Girls count :");
                    // console.log(result[0]["count(*)"]);
                    resolve(result[0]["count(*)"])
                }
            });
        });
    }
    async getStudentsRollOfHostel(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT studentRoll FROM students WHERE hostelID=?',[hostelID],async(exc,students)=>{
                if(exc){
                    console.log(exc);
                    reject(exc);
                }else{
                    resolve(students);
                }
            })
        })
    }
    async getHostelOccupants(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM students WHERE hostelID = ?',[hostelID],async(exc,students)=>{
                if(exc){
                    console.log(exc);
                    reject(exc);
                }else{
                    resolve(students[0]);
                }
            })
        })
    }
    async find(id){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE studentRoll=?', [id], async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("student found :");
                    // console.log(result);
                    resolve(result[0]);
                }
            });
        });
    }

    async getDues(studentRoll){
        return new Promise((resolve, reject) => {
            db.query('SELECT dues FROM students WHERE studentRoll = ?', [studentRoll], async(err, result) => {
                if(err){
                    reject(err)
                }
                else{
                    resolve(result[0]['dues']);
                }
            })
        })
    }

    async allocateRoom(roomID,hostelID,studentRoll){
        return new Promise((resolve,reject)=>{
            // console.log("\n\n\n\nstudent = "+studentRoll);
            // console.log("roomiD = "+roomID);
            db.query('UPDATE students SET roomNumber=?, hostelID=? WHERE studentRoll=?',[roomID,hostelID,studentRoll],async(err,result)=>{
                if(err) {
                    console.log("error in updating hostel and room number for student"+err);
                    reject(err);
                }
                else 
                    resolve(true);
            });
        });
    }

    async addDues(amount,studentRoll){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE students set dues=dues+? WHERE studentRoll=?',[amount,studentRoll],async(errDues,duesResult)=>{
                if(errDues){
                    console.log("Error : "+errDues);
                    reject(errDues);
                }else{
                    resolve(true)
                }
            });
        });
    }

    async payDues(amount,studentRoll){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE students set dues=dues-? WHERE studentRoll=?',[amount,studentRoll],async(errDues,duesResult)=>{
                if(errDues){
                    console.log("Error : "+errDues);
                    reject(errDues);
                }else{
                    resolve(true)
                }
            });
        });
    }

    async findStudentByRollAndPassword(studentRoll, password){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM students WHERE studentRoll = ? AND password = ?', [studentRoll.toUpperCase(), password], async(err, result) => {
                if(err || !result || result.length == 0){
                    console.log("Error : " + err)
                    reject(err)
                }
                else{
                    // console.log("\n\nstudent found :")
                    // console.log(result)
                    resolve(result[0])
                }
            });
        });
    }

    async createStudent(data){
        return new Promise((resolve, reject) => {
            db.query('INSERT INTO students SET ?', [data], async(err, result) => {
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("student added :");
                    // console.log(result[0]);
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
            //   console.log("student updated :");
            //   console.log(result);
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
                    // console.log("student deleted :");
                    // console.log(result);
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