var express =require('express');
var router = express.Router();

var verifyToken = require("../middlewares/verifyToken");

router.put('/updatePassword', verifyToken, require("../controllers/updatePassword"));

module.exports = router;