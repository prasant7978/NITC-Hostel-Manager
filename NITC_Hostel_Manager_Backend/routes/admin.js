const express =require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");

var addStudent = require("../controllers/admin/student/addStudent");
var deleteStudent = require("../controllers/admin/student/deleteStudent");
var updateStudent = require("../controllers/students/updateStudent");
var getAllStudents = require("../controllers/admin/student/getAllStudents")
var getBoys = require("../controllers/admin/student/getBoys")
var getGirls = require("../controllers/admin/student/getGirls")
var getStudent = require("../controllers/admin/student/getStudent")
var getStudentsCount = require("../controllers/admin/student/getStudentsCount")

var addWarden = require("../controllers/admin/warden/addWarden");
var deleteWarden = require("../controllers/admin/warden/deleteWarden");
var updateWarden = require("../controllers/admin/warden/updateWarden");
var getAllWardens = require("../controllers/admin/warden/getAllWardens");
var getWardenDetails = require("../controllers/admin/warden/getWardenDetails")
const getWardensCount = require("../controllers/admin/warden/getWardensCount");

router.get('/profile', verifyToken, require("../controllers/admin/getProfile"));
router.post("/students/add", verifyToken, addStudent);
router.get("/students/all", verifyToken, getAllStudents);
router.get("/students/boys", verifyToken, getBoys);
router.get("/students/girls", verifyToken, getGirls);
router.get("/students/count", verifyToken, getStudentsCount);
router.post("/students/update", verifyToken, updateStudent);
router.delete("/students/delete", verifyToken, deleteStudent);
router.get("/students/details", verifyToken, getStudent);
router.post("/wardens/add", verifyToken, addWarden);
router.get("/wardens/getDetails", verifyToken, getWardenDetails)
router.get("/wardens/count", verifyToken, getWardensCount);
router.get("/wardens/all", verifyToken, getAllWardens);
router.put("/wardens/update", verifyToken, updateWarden);
router.delete("/wardens/delete", verifyToken, deleteWarden);

module.exports = router;