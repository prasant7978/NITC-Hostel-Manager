const Admin = require('../models/adminModel')
const Student = require('../models/studentModel')

module.exports = async(userType,username,password) => {
    if(userType == "Admin"){
        const adminModel = new Admin()
        await adminModel.findAdminByEmailAndPassword(username, password).then(function(admin){
            
        }).catch(function(exc){
            console.log("Exception = "+exc);
            return null;
        });
        const token = await adminModel.generateAuthToken(username, userType);
        console.log(token);
        return token;
    }
    else if(userType == "Student"){
        const studentModel = new Student()
        const student = await studentModel.findStudentByRollAndPassword(username, password)
        if(student)
            return adminModel.generateAuthToken(username, userType)
        else
            return null
    }
}