const db = require('../db/db_connection');

module.exports = class Complaint{
    async issueComplaint(complaint){
        return new Promise((resolve,reject)=>{
            // db.query('INSERT INTO complaints(referTo,status,studentRoll,message,date,time) VALUES(?,?,?,?,?,?)',)
            db.query('INSERT INTO complaints SET ?',[complaint],async(err,_)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(true);
                }
            });
        });
    }
    async resolveComplaint(complaintID,referToID){
        return new Promise((resolve,reject)=>{
            // db.query('INSERT INTO complaints(referTo,status,studentRoll,message,date,time) VALUES(?,?,?,?,?,?)',)
            db.query('UPDATE complaints SET status=? WHERE complaintID=? and referTo=?',["Resolved",complaintID,referToID],async(err,_)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(true);
                }
            });
        });
    }
    async rejectComplaint(complaintID,referToID){
        return new Promise((resolve,reject)=>{
            // db.query('INSERT INTO complaints(referTo,status,studentRoll,message,date,time) VALUES(?,?,?,?,?,?)',)
            db.query('UPDATE complaints SET status=? WHERE complaintID=? and referTo=?',["Rejected",complaintID,referToID],async(err,_)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(true);
                }
            });
        });
    }

    async getComplaints(userID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM complaints WHERE referTo=',[userID],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(result);
                }
            });
        });
    }
    
    async getOwnComplaints(studentRoll){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM complaints WHERE studentRoll=',[studentRoll],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(result);
                }
            });
        });
    }

    async deleteComplaint(complaintID){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM complaints WHERE complaintID=',[complaintID],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(true);
                }
            });
        });
    }

    
}