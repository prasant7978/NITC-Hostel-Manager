const express =require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");
var addStudent = require("../controllers/admin/addStudent");
var deleteStudent = require("../controllers/admin/deleteStudent");
var updateStudent = require("../controllers/students/updateStudent");
var getAllStudents = require("../controllers/admin/getAllStudents")
var getStudentsCount = require("../controllers/admin/getStudentsCount")

router.post("/students/add", verifyToken, addStudent);
router.get("/students/all", verifyToken, getAllStudents);
router.get("/students/count", verifyToken, getStudentsCount);
router.put("/students/update", verifyToken, updateStudent);
router.delete("/students/delete", verifyToken, deleteStudent);

module.exports = router;