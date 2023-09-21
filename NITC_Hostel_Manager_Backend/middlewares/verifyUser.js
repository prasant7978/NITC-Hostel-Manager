const Admin = require('../models/adminModel')
const Student = require('../models/studentModel')

module.exports = async(userType,username,password) => {
    if(userType == "Admin"){
        const adminModel = new Admin()
        var authenticated = false
        await adminModel.findAdminByEmailAndPassword(username, password).then(function(admin){
            authenticated = true
        }).catch(function(exc){
            console.log("Email or Password doesn't match: " + exc);
            return null;
        });

        if(authenticated){
            const token = await adminModel.generateAuthToken(username, userType);
            console.log("token = "+token);
            return token;
        }
        else
            return null
    }
    else if(userType == "Student"){
        const studentModel = new Student()
        var authenticated = false
        await studentModel.findStudentByRollAndPassword(username, password).then(function(student){
            authenticated = true
        }).catch(function(error){
            console.log("Rollno or Password doesn't match: " + error);
            return null
        })
        if(authenticated){
            const token = await studentModel.generateAuthToken(username, userType);
            console.log("token = "+token);
            return token;
        }
        else
            return null
    }
}