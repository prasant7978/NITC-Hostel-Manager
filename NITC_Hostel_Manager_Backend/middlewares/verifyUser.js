const Admin = require('../models/adminModel')
const Student = require('../models/studentModel')

module.exports = async(user) => {
    if(user.userType == "Admin"){
        const adminModel = new Admin()
        const admin = await adminModel.findAdminByEmailAndPassword(user.email, user.password)
        if(admin)
            return adminModel.generateAuthToken(user.email, user.userType)
        else
            return null
    }
    else if(user.userType == "Student"){
        const studentModel = new Student()
        const student = await studentModel.findStudentByRollAndPassword(user.studentRoll, user.password)
        if(student)
            return adminModel.generateAuthToken(user.studentRoll, user.userType)
        else
            return null
    }
}