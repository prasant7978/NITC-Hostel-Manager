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

    async updateHostel(hostel, hostelID){
        return new Promise((resolve, reject) => {
            db.query('UPDATE hostels SET ? WHERE hostelID = ?', [hostel, hostelID], async(err, hostel) => {
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

    async decrementOccupants(hostelID){
        return new Promise((resolve,reject)=>{
            if(hostelID == null) resolve(true);
            db.query('UPDATE hostels set occupants=occupants-1 WHERE hostelID=?',[hostelID],async(err,result)=>{
                if(err){
                    console.log(err);
                    reject(err)
                }else{
                    resolve(true);
                }
            })
        })
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
    
    async getHostelsWithGender(gender){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM hostels where occupantsGender=?',[gender],async(err,hostels)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    resolve(hostels);
                }
            });
        });
    }

    
    async getHostelsNames(gender){
        return new Promise((resolve,reject)=>{
            db.query('SELECT hostelID FROM hostels where occupantsGender=?',[gender],async(err, hostelNames)=>{
                if(err){
                    console.log(err);
                    reject(err);
                }else{
                    var names = [];
                    for(let i = 0;i<hostelNames.length;i++){
                        names.push(hostelNames[i]['hostelID']);
                    }
                    resolve(names);
                }
            });
        });
    }

    async assignWarden(wardenEmail,hostelID){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE hostels SET wardenEmail=? WHERE hostelID=?',[wardenEmail,hostelID],async(exc,_)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    console.log("assigning for hostel"+hostelID);
                    resolve(true);
                }
            });
        });
    }
    
    async removeWarden(hostelID){
        return new Promise((resolve,reject)=>{
            console.log("hostelID for removing warden = "+hostelID);
            db.query('UPDATE hostels SET wardenEmail=NULL WHERE hostelID=?',[hostelID],async(exc,hostel)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    console.log("new hostel"+hostel);
                    resolve(true);
                }
            });
        });
    }
    
    async incrementOccupants(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE hostels SET occupants=occupants+1 WHERE hostelID=? AND occupants<capacity',[hostelID],async(exc,_)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    console.log("hostel updateds");
                    resolve(true);
                }
            });
        });
    }

    async getOccupants(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('SELECT * FROM students WHERE hostelID = ?',[hostelID],async(exc,occupants)=>{
                if(exc){
                    console.log(exc);
                    reject(exc);
                }else{
                    // console.log("students count = ");
                    console.log(occupants);
                    resolve(occupants);
                }
            });
        });
    }

    async addDues(amount,hostelID){
        return new Promise((resolve,reject)=>{
            db.query('UPDATE hostels SET totalDues = totalDues + ? WHERE hostelID = ?',[amount,hostelID],async(exc,result)=>{
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