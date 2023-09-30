const db = require('../db/db_connection');

module.exports = class Model{
    constructor(){
    }
    async get_all(){
        db.query('SELECT * FROM wardens',async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("wardens : \n");
                console.log(result);
                return result;
            }
        });
    }
    async findWarden(email){
        db.query('SELECT * FROM wardens WHERE email=?',[email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("student found : \n");
                console.log(result);
                return result;
            }
        });
    }
    async createWarden(data){
        db.query('INSERT INTO wardens SET ?',[data],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("student added : \n");
                console.log(result);
                return result;
            }
        });
    }
    async updateWarden(email,data){
        db.query('UPDATE wardens SET ? WHERE email=?',[data,email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("student updated : \n");
                console.log(result);
                return result;
            }
        });
    }
    async deleteWarden(email){
        db.query('DELETE FROM wardens WHERE email=?',[email],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("student deleted : \n");
                console.log(result);
                return result;
            }
        });
    }

}