var db = require("../db/db_connection")

module.exports = class Model{
    async allocateRoom(studentRoll,roomNumber,hostelID){
        return new Promise((resolve,reject)=>{
            
            db.query('UPDATE rooms SET studentRoll=? WHERE (roomNumber=? AND hostelID=? AND studentRoll=NULL)',[studentRoll,roomNumber,hostelID],async(exc,result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result){
                        resolve(true);
                    }else reject("Not updated without any exception");
                }
            });

        });
    }

    async deleteRoom(roomNumber,hostelID){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM rooms WHERE roomNumber=? AND hostelID=?',[roomNumber,hostelID],async(exc,result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result){
                        resolve(true);
                    }else reject("Not deleted    without any exception");
                }
            })
        })
    }

    async createRoom(roomNumber,hostelID,roomType){
        return new Promise((resolve,reject)=>{
            db.query('INSERT INTO rooms(roomNumber,hostelID,roomType) VALUES(?,?,?)',[roomNumber,hostelID,roomType],async(exc,result)=>{
                if(exc){
                    console.log("Error: "+exc);
                    reject(exc);
                }else{
                    if(result){
                        resolve(true);
                    }else{
                        console.log("Not inserted without any exception");
                        reject("Not inserted without any exception");
                    }
                }
            })
        })
    }

    async createRooms(hostelID,capacity){
        return new Promise((resolve,reject)=>{
            db.query('')
        })
    }


}