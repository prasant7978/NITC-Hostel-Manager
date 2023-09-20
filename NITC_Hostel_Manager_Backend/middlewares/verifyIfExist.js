const AdminModel = require('../models/adminModel');
const StudentModel = require('../models/studentModel');
// const Wardwn = require('../models/wardenModel');

module.exports = async(id, userType) => {
    if(userType == "Admin"){
        var adminModel = new AdminModel()
        var admin = await adminModel.findAdmin(id)
        if(admin == null)
            return false
        else
            return true
    }
    else if(userType == "Student"){
        var studentModel = new StudentModel()
        var student = await studentModel.find(id)
        if(student)
            return true
        else
            return false
    }
}