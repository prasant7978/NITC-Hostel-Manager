var express = require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");


router.get("/students/count",verifyToken,require("../controllers/warden/totalHostelOccupants"));

module.exports = router;