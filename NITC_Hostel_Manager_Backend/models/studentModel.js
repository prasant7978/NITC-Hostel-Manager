const db = require('../db/db_connection');

module.exports = class Model{
    constructor(){
    }
    async get_all(){
        db.query('SELECT * FROM students',async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("students : \n");
                console.log(result);
                return result;
            }
        });
    }
    async find(id){
        db.query('SELECT * FROM students WHERE studentRoll=?',async(err,result)=>{
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
        db.query('INSERT INTO students SET ?',[data],async(err,result)=>{
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
        db.query('UPDATE students SET ? WHERE id=?',[data,id],async(err,result)=>{
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
        db.query('DELETE FROM students WHERE id=?',[id],async(err,result)=>{
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