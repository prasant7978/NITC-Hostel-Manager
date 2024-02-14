const db = require('../db/db_connection');
const jwt = require("jsonwebtoken")
const config = require("config")

module.exports = class Model{
    constructor(){
    }

    async removeHostel(wardenEmail){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE wardens set hostelID=NULL where email=?',[wardenEmail],async(err,_)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else resolve(true);
            });
        });
    }


    async updatePassword(newPassword,wardenEmail){
        return new Promise((resolve, reject) => {
            db.query('UPDATE wardens set password=? where email=?',[newPassword,wardenEmail],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err)
                }else{
                    resolve(true);
                }
            })
        })
    }
    async get_all(){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM wardens',async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                // console.log("wardens : \n");
                // console.log(result);
                resolve(result);
            }
        });
    });
    }

    
    async findWarden(email){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM wardens WHERE email = ?',[email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                // console.log("warden found : \n");
                // console.log(result);
                resolve(result[0]);
            }
        });
    });
    }

    async findWardenWithEmailAndPassword(email, password){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM wardens WHERE email = ? and password = ?',[email, password],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                // console.log("warden found : \n");
                // console.log(result);
                resolve(result[0]);
            }
        });
    });
    }

    async createWarden(data){
        return new Promise((resolve,reject)=>{
            db.query('INSERT INTO wardens SET ?',[data],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                // console.log("warden added : \n");
                // console.log(result);
                resolve(true);
            }
        });
    });
    }

    async updateWarden(email,data){
        return new Promise((resolve, reject)=>{
            db.query('UPDATE wardens SET ? WHERE email=?', [data, email], async(err, result) => {
                if(err){
                    console.log("Error: " + err);
                    reject
                }else{
                    // console.log("warden updated:");
                    // console.log(result);
                    resolve(true);
                }
            });
        });
    }

    async getWardensCount(){
        return new Promise((resolve, reject) => {
            db.query('SELECT count(*) FROM wardens',async(err,result)=>{
                if(err){
                    console.log("Error : "+err);
                    reject(err)
                }else{
                    // console.log("Wardens total :");
                    // console.log(result);
                    // console.log(result[0]["count(*)"]);
                    resolve(result[0]["count(*)"])
                }
            });
        })
    }
    async deleteWarden(email){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM wardens WHERE email=?',[email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                // console.log("warden deleted : \n");
                // console.log(true);
                resolve(true);
            }
        });
    });
    }

    async generateAuthToken(id, userType){
        const jwtGenerated = jwt.sign({username: id, userType: userType}, config.get('hostel_manager_private_key'))
        return jwtGenerated
    }
}