<<<<<<< HEAD
var express = require('express')
=======
const express = require('express');
>>>>>>> 942a7fb1fb92c1147531b22746dc60c1bf69d566
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");

var addHostel = require('../controllers/admin/hostel/addHostel')
var updateHostel = require('../controllers/admin/hostel/updateHostel')
var getHostelDetails = require('../controllers/hostel/getHostelDetails')
var getAllHostel = require('../controllers/hostel/getAllHostels')
var deleteHostel = require('../controllers/admin/hostel/deleteHostel')

router.post("/add", verifyToken, addHostel)
router.post("/update", verifyToken, updateHostel)
router.get("/details", verifyToken, getHostelDetails)
router.get("/all", verifyToken, getAllHostel)
router.delete("/delete", verifyToken, deleteHostel)

module.exports = router