var express = require('express');
var router = express.Router();

var verifyIfExist = require("../middlewares/verifyIfExist");
var issueComplaint = require("../controllers/student/issueComplaint");
var viewOwnComplaints = require("../controllers/student/viewOwnComplaints")
var getComplaints = require("../controllers/getComplaints");

router.post("/issue",verifyIfExist,issueComplaint);
router.get("/getOwn",verifyIfExist,viewOwnComplaints);
router.get("/getAll",verifyIfExist,getComplaints);
router.post("/resolve",verifyIfExist,)