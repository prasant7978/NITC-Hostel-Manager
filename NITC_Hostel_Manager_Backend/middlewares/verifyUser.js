const Admin = require('../models/adminModel')
const Student = require('../models/studentModel')
const WardenModel = require('../models/wardenModel')

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
    }else{
        const wardenModel = new WardenModel()
        var authenticated = false
        await wardenModel.findWarden(username, password).then(function(_){
            authenticated = true
        }).catch(function(error){
            console.log("Email or password does not match: " + error);
            return null
        });
        if(authenticated){
            const token = await wardenModel.generateAuthToken(username, userType);
            console.log("token = "+token);
            return token;
        }
    }
}