const db = require('../db/db_connection');

module.exports = class Model{
    constructor(){
    }
    async get_all(){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM wardens',async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                console.log("wardens : \n");
                console.log(result);
                resolve(result[0]);
            }
        });
    });
    }
    async findWarden(email){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM wardens WHERE email=?',[email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                console.log("student found : \n");
                console.log(result);
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
                console.log("student added : \n");
                console.log(result);
                resolve(result[0]);
            }
        });
    });
    }
    async updateWarden(email,data){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE wardens SET ? WHERE email=?',[data,email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject
            }else{
                console.log("student updated : \n");
                console.log(result);
                resolve(result[0]);
            }
        });
    });
    }
    async deleteWarden(email){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM wardens WHERE email=?',[email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                reject(err);
            }else{
                console.log("student deleted : \n");
                console.log(result);
                resolve(true);
            }
        });
    });
    }

}