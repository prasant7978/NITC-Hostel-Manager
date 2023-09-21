const express =require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");
var addStudent = require("../controllers/admin/addStudent");
var deleteStudent = require("../controllers/admin/deleteStudent");
var updateStudent = require("../controllers/student/updateStudent");
var getAllStudents = require("../controllers/admin/getAllStudents")

router.post("/student/add", verifyToken, addStudent);
router.get("/student/allStudent", verifyToken, getAllStudents);
router.put("/student/update", verifyToken, updateStudent);
router.delete("/student/delete", verifyToken, deleteStudent);

module.exports = router;