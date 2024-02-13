const express = require('express');

var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");

var addHostel = require('../controllers/admin/hostel/addHostel')
var updateHostel = require('../controllers/admin/hostel/updateHostel')
var getHostelDetails = require('../controllers/hostel/getHostelDetails')
var getAllHostel = require('../controllers/hostel/getAllHostels')
var getHostelsNames = require('../controllers/hostel/getHostelsNames')
var deleteHostel = require('../controllers/admin/hostel/deleteHostel');
const getHostelsWithGender = require('../controllers/hostel/getHostelsWithGender');

router.post("/add", verifyToken, addHostel)
router.post("/update", verifyToken, updateHostel)
router.get("/details", verifyToken, getHostelDetails)
router.get("/all", verifyToken, getAllHostel)
router.get("/gender", verifyToken,getHostelsWithGender)
router.delete("/delete", verifyToken, deleteHostel)
router.get("/allNames",verifyToken,getHostelsNames);

module.exports = router