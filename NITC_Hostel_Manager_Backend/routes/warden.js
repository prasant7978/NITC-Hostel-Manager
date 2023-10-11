var express = require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");
var getProfile = require("../controllers/warden/getProfile")

router.get("/students/count", verifyToken, require("../controllers/warden/totalHostelOccupants"));
router.get("/profile", verifyToken, getProfile);

module.exports = router;