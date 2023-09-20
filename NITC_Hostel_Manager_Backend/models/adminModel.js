const db = require('../db/db_connection');

module.exports = class Model{
    async getAllAdmin(){
        db.query('SELECT * FROM admin',async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("admin : \n");
                console.log(result);
                return result;
            }
        });
    }
    async findAdmin(email){
        db.query('SELECT * FROM admin WHERE email=?', [email], async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("admin found : \n");
                console.log(result);
                return result;
            }
        });
    }
    async findAdminByEmailAndPassword(email, password){
        db.query('SELECT * FROM admin WHERE email = ? AND password = ?', [email, password], async(err, result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("admin found : \n");
                console.log(result);
                return result;
            }
        });
    }
    async createAdmin(data){
        db.query('INSERT INTO admin SET ?',[data],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("admin added : \n");
                console.log(result);
                return result;
            }
        });
    }
    async updateAdmin(id,data){
        db.query('UPDATE admin SET ? WHERE id=?',[data,id],async(err,result)=>{
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
    async deleteAdmin(id){
        db.query('DELETE FROM admin WHERE id=?',[id],async(err,result)=>{
            if(err){
                console.log("Error : "+err);
                return null;
            }else{
                console.log("admin deleted : \n");
                console.log(result);
                return result;
            }
        });
    }

    async generateAuthToken(email, userType){
        const jwtGenerated = jwt.sign({username: email, userType: userType}, config.get('hostel_manager_private_key'))
        return jwtGenerated
    }

}