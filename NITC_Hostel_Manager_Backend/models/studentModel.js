const db = require('../db/db_connection');

module.exports = class Model{
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
        db.query('SELECT * FROM students WHERE studentRoll=?', [id], async(err,result)=>{
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

    async findStudentByRollAndPassword(studentRoll, password){
        db.query('SELECT * FROM students WHERE studentRoll = ? AND password = ?', [studentRoll, password], async(err, result) => {
            if(err){
                console.log("Error : " + err)
                return null
            }
            else{
                console.log("student found : \n")
                console.log(result)
                return result
            }
        })
    }

    async createStudent(data){
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
    async updateStudent(id,data){
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

    async generateAuthToken(id, userType){
        const jwtGenerated = jwt.sign({username: id, userType: userType}, config.get('hostel_manager_private_key'))
        return jwtGenerated
    }

}