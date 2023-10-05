var express = require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");


router.get("/count",verifyToken,require("../controllers/notices/getNoticesCount"));
router.get("/all",verifyToken,require("../controllers/notices/getNotices"));
router.post("/delete",verifyToken,require("../controllers/notices/deleteNotice"));
module.exports = router;