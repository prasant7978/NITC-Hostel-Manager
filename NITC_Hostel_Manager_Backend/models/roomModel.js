var db = require("../db/db_connection")

module.exports = class Model{
    async getAllRooms(hostelID){
        return new Promise((resolve, reject) => {
            db.query('SELECT * from rooms WHERE hostelID = ?', [hostelID], async(err, result) => {
                if(err)
                    reject(err)
                else
                    resolve(result)
            })
        })
    }

    async getAvailableRooms(hostelID){
        return new Promise((resolve, reject) => {
            db.query('SELECT * from rooms WHERE hostelID=? AND studentRoll IS NULL', [hostelID], async(err, result) => {
                if(err)
                    reject(err)
                else{
                    console.log(result);
                    resolve(result)
                }
            })
        })
    }

    async getAllRoomsFromTo(hostelID,start,end){
        return new Promise((resolve, reject) => {
            db.query('SELECT * from rooms WHERE hostelID=? AND roomNumber>=? AND roomNumber<=?', [hostelID,start,end], async(err, result) => {
                if(err)
                    reject(err)
                else{
                    console.log(result);
                    resolve(result)
                }
            });
        });
    }
    async getAvailableRoomsFromTo(hostelID,start,end){
        return new Promise((resolve, reject) => {
            db.query('SELECT * from rooms WHERE hostelID=? AND studentRoll IS NULL AND roomNumber>=? AND roomNumber<=?', [hostelID,start,end], async(err, result) => {
                if(err)
                    reject(err)
                else{
                    console.log(result);
                    resolve(result)
                }
            })
        })
    }

    async allocateRoom(studentRoll, roomNumber, hostelID){
        return new Promise((resolve,reject)=>{
            console.log("\n\n\n studentROll="+studentRoll);
            db.query('UPDATE rooms SET studentRoll=? WHERE (roomNumber = ? AND hostelID = ? AND studentRoll IS NULL)', [studentRoll, roomNumber, hostelID], async(exc, result)=>{
                if(exc){
                    console.log("error in allocating to student");
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result)
                        resolve(true);
                    else 
                        reject("Not updated without any exception");
                }
            });

        });
    }

    async deallocateRoom(studentRoll, roomNumber, hostelID){
        return new Promise((resolve,reject)=>{
            if(roomNumber == -1) resolve(true);
            db.query('UPDATE rooms SET studentRoll=NULL WHERE (roomNumber = ? AND hostelID = ? AND studentRoll = ?)', [roomNumber, hostelID, studentRoll], async(exc, result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result)
                        resolve(true);
                    else 
                        reject("Not updated without any exception");
                }
            });

        });
    }

    async deleteRoom(roomNumber, hostelID){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM rooms WHERE roomNumber = ? AND hostelID = ?', [roomNumber, hostelID], async(exc, result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result)
                        resolve(true);
                    else 
                        reject("Not deleted without any exception");
                }
            })
        })
    }

    async deleteAllRooms(hostelID){
        return new Promise((resolve,reject)=>{
            db.query('DELETE FROM rooms WHERE hostelID = ?', [hostelID], async(exc, result)=>{
                if(exc){
                    console.log("Error : "+exc);
                    reject(exc);
                }else{
                    if(result){
                        console.log("rooms deleted");
                        resolve(true);
                    }
                    else 
                        reject("Not deleted without any exception");
                }
            })
        })
    }

    async createRoom(roomNumber, hostelID, roomType){
        return new Promise((resolve, reject)=>{
            db.query('INSERT INTO rooms(roomNumber,hostelID,roomType) VALUES(?,?,?)',[roomNumber, hostelID, roomType], async(exc, result)=>{
                if(exc){
                    console.log("Error: "+exc);
                    reject(exc);
                }else{
                    if(result)
                        resolve(true);
                    else{
                        console.log("Not inserted without any exception");
                        reject("Not inserted without any exception");
                    }
                }
            })
        })
    }

    async createRooms(hostelID, capacity){
        return new Promise((resolve,reject)=>{
            var query = "INSERT INTO rooms(roomNumber,hostelID) VALUES ";
            for(var i = 1;i<capacity;i++){
                query = query+"("+i+", '"+hostelID+"'),";
            }
            // query += "SELECT "+capacity+", "+hostelID;
            query = query + "("+capacity+",'"+hostelID+"')";
            console.log("Query to insert rooms = "+query);
            db.query(query,async(exc,result)=>{
                if(exc){
                    console.log(exc);
                    reject(exc);
                }else{
                    console.log("inserted rooms");
                    resolve(true);
                }
            });
        });
    }
}