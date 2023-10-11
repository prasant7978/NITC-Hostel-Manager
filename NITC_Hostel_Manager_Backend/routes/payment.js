const express = require('express');
var router = express.Router();

const verifyToken = require('../middlewares/verifyToken');
const payDues = require('../controllers/payment/payDues')

router.post("/student/payDues", verifyToken, payDues);

module.exports = router;