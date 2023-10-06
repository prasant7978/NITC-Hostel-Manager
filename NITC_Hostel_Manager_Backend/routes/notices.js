var express = require("express");
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");

router.get("/countNotices",verifyToken,require("../controllers/notices/getNoticesCount"));

router.get("/getAllNotices",verifyToken,require("../controllers/notices/getNotices"));

router.post("/addNotice", verifyToken, require("../controllers/notices/addNotice"));

router.delete("/deleteNotice",verifyToken,require("../controllers/notices/deleteNotice"));

module.exports = router;