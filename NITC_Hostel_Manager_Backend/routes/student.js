const express = require('express');
var router = express.Router();

const deleteStudent = require('../controllers/student/deleteStudent');
const updateStudent = require('../controllers/student/updateStudent');
const verifyToken = require('../middlewares/verifyToken');

// router.post("/add",verifyToken,addStudent);
// router.post("/delete",verifyToken,deleteStudent);
// router.post("/update",verifyToken,updateStudent);


module.exports = router;