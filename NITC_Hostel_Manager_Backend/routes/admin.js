const express =require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");
var addStudent = require("../controllers/admin/addStudent");

router.post("/student/add",verifyToken,addStudent);

module.exports = router;