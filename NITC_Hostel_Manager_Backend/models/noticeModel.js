const db = require('../db/db_connection');

module.exports = class Notice{
    async issueNotice(notice){
        return new Promise((resolve,reject)=>{
            // db.query('INSERT INTO complaints(referTo,status,studentRoll,message,date,time) VALUES(?,?,?,?,?,?)',)
            db.query('INSERT INTO notices SET ?',[notice],async(err,_)=>{
                if(err){
                    console.log(err);
                    reject(false);
                }else{
                    resolve(true);
                }
            });
        });
    }
    async deleteNotice(noticeID,userID){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM notices WHERE noticeID=? AND issuerID=?',[noticeID,userID],async(err,_)=>{
                if(err){
                    console.log(err);
                    reject(false);
                }else{
                    resolve(true);
                }
            });
        });
    }
    async getNoticesForStudents(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM notices WHERE IF NOT NULL hostelID=? and referTo=?',[hostelID,"Students"],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(result[0]);
                }
            })
        });
    }
    
    async getNoticesForWarden(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM notices WHERE IF NOT NULL hostelID=? and referTo=?',[hostelID,"Warden"],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(result[0]);
                }
            })
        });
    }
    
    async getNoticesForAdmin(){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM notices',async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(result[0]);
                }
            })
        });
    }
}