const db = require('../db/db_connection');
const jwt = require("jsonwebtoken")
const config = require("config")

module.exports = class Model{
    async getAllAdmin(){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM admin', async(err, result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("admin : \n");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }

    async findAdmin(email){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM admin WHERE email=?', [email], async(err, result) => {
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("admin found :");
                    console.log(result);
                    resolve(result[0])
                }
            });
        })
    }

    async findAdminByEmailAndPassword(email, password){
        return new Promise((resolve, reject) => {
            db.query('SELECT * FROM admin WHERE email = ? AND password = ?', [email, password], async(err, result)=>{
                if(err || !result || result.length==0){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("admin found :");
                    console.log(result);
                    resolve(result[0])
                }
            });
        })
    }

    async createAdmin(data){
        return new Promise((resolve, reject) => {
            db.query('INSERT INTO admin SET ?', [data], async(err, result) => {
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("admin added :");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }

    async updateAdmin(id,data){
        return new Promise((resolve, reject) => {
            db.query('UPDATE admin SET ? WHERE id=?',[data,id],async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("student updated :");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }

    async deleteAdmin(id){
        return new Promise((resolve, reject) => {
            db.query('DELETE FROM admin WHERE id=?', [id], async(err, result) => {
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    console.log("admin deleted :");
                    console.log(result);
                    resolve(result)
                }
            });
        })
    }

    async generateAuthToken(email, userType){
        const jwtGenerated = jwt.sign({username: email, userType: userType}, config.get('hostel_manager_private_key'))
        return jwtGenerated
    }

}