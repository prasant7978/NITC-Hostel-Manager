const express =require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");

var addStudent = require("../controllers/admin/student/addStudent");
var deleteStudent = require("../controllers/admin/student/deleteStudent");
var updateStudent = require("../controllers/students/updateStudent");
var getAllStudents = require("../controllers/admin/student/getAllStudents")
var getStudentsCount = require("../controllers/admin/student/getStudentsCount")

var addWarden = require("../controllers/admin/warden/addWarden");
var deleteWarden = require("../controllers/admin/warden/deleteWarden");
var updateWarden = require("../controllers/admin/warden/updateWarden");
var getAllWardens = require("../controllers/admin/warden/getAllWardens")
// var getWardensCount = require("../controllers/admin/getWardensCount")

var addHostel = require('../controllers/admin/hostel/addHostel')
var updateHostel = require('../controllers/admin/hostel/updateHostel')
var getHostelDetails = require('../controllers/hostel/getHostelDetails')
var getAllHostel = require('../controllers/hostel/getAllHostels')
var deleteHostel = require('../controllers/admin/hostel/deleteHostel')

router.post("/students/add", verifyToken, addStudent);
router.get("/students/all", verifyToken, getAllStudents);
router.get("/students/count", verifyToken, getStudentsCount);
router.put("/students/update", verifyToken, updateStudent);
router.delete("/students/delete", verifyToken, deleteStudent);

router.post("/wardens/add", verifyToken, addWarden);
router.get("/wardens/all", verifyToken, getAllWardens);
// router.get("/wardens/count", verifyToken, getStudentsCount);
router.put("/wardens/update", verifyToken, updateWarden);
router.delete("/wardens/delete", verifyToken, deleteWarden);

router.post("hostels/add", verifyToken, addHostel)
router.post("hostels/update", verifyToken, updateHostel)
router.get("hostels/details", verifyToken, getHostelDetails)
router.get("hostels/all", verifyToken, getAllHostel)
router.delete("hostels/delete", verifyToken, deleteHostel)

module.exports = router;