const express = require('express');
var router = express.Router();

const deleteStudent = require('../controllers/student/deleteStudent');
const updateStudent = require('../controllers/student/updateStudent');
const issueBill = require('../controllers/student/issueBill');
const getDues = require('../controllers/student/getDues');
const verifyToken = require('../middlewares/verifyToken');

// router.post("/add",verifyToken,addStudent);
// router.post("/delete",verifyToken,deleteStudent);
// router.post("/update",verifyToken,updateStudent);
router.post("/payBill", verifyToken, issueBill);
router.get("/getBill", verifyToken, getDues);

module.exports = router;