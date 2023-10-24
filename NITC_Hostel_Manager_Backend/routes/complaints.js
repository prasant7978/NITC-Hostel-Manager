var express = require('express');
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");
var issueComplaint = require("../controllers/students/issueComplaint");
var viewOwnComplaints = require("../controllers/students/viewOwnComplaints")
var getPendingComplaints = require("../controllers/complaints/getPendingComplaints");
var getPendingComplaintsCount = require("../controllers/complaints/getComplaintsCount");
const rejectComplaint = require('../controllers/complaints/rejectComplaint');
const resolveComplaint = require('../controllers/complaints/resolveComplaint');

router.post("/issue", verifyToken, issueComplaint);
router.get("/own", verifyToken, viewOwnComplaints);
router.get("/all", verifyToken, getPendingComplaints);
router.put("/reject", verifyToken, rejectComplaint);
router.put("/resolve", verifyToken, resolveComplaint);
router.get("/count", verifyToken, getPendingComplaintsCount);

module.exports = router;