var express = require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");


router.get("/students/count",verifyToken,require("../controllers/warden/totalHostelOccupants"));
router.get("/students/all",verifyToken,require("../controllers/warden/getOccupants"));
router.get('/profile',verifyToken,require("../controllers/warden/getProfile"));
module.exports = router;