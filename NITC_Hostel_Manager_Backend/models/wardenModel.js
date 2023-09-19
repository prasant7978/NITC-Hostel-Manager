const db = require('../db-config/db_connection');

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
    async find(id){
        db.query('SELECT * FROM wardens WHERE studentRoll=?',async(err,result)=>{
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
    async create(data){
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
    async update(id,data){
        db.query('UPDATE wardens SET ? WHERE id=?',[data,id],async(err,result)=>{
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
    async deleteStudent(id){
        db.query('DELETE FROM wardens WHERE id=?',[id],async(err,result)=>{
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