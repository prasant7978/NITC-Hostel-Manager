var db = require("../db/db_connection");

module.exports = class Model{
    
    async addHostel(hostel){
        return new Promise((resolve,reject)=>{
            db.query('INSERT INTO hostels SET ?',[hostel],async(exc,result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    resolve(true);
                }
            });
        });
    }

    async updateHostel(hostel){
        return new Promise((resolve, reject) => {
            db.query('UPDATE hostels SET ? WHERE hostelID = ?', [hostel, hostel.hostelID], async(err, hostel) => {
                if(err){
                    console.log(err);
                    reject(err);
                }
                else
                    resolve(true);
            })
        })
    }

    async getHostelDetails(hostelID){
        return new Promise((resolve, reject)=>{
            db.query('SELECT * FROM hostels WHERE hostelID = ?', [hostelID], async(err,hostel)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(hostel[0]);
                }
            });
        });
    }

    async deleteHostel(hostelID){
        return new Promise((resolve, reject)=>{
            db.query('DELETE FROM hostels WHERE hostelID = ?', [hostelID], async(err, hostel)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(true);
                }
            });
        });
    }

    async getAllHostel(){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM hostels',async(err, hostel)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(hostel);
                }
            });
        });
    }

    async assignWarden(wardenEmail,hostelID){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE hostels SET wardenEmail=? WHERE hostelID=',[wardenEmail,hostelID],async(exc,_)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    resolve(true);
                }
            });
        });
    }
    
    async decrementCapacity(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE hostels SET capacity=capacity-1 WHERE hostelID=?',[hostelID],async(exc,_)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    resolve(true);
                }
            });
        });
    }

    async addDues(amount,hostelID){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE hostels SET totalDues = totalDues+? WHERE hostelID=?',[amount,hostelID],async(exc,result)=>{
            if(exc){
                console.log("Error: "+exc);
                reject(exc);
            }else{
                resolve(true);
            }
        })
    });
    }

}