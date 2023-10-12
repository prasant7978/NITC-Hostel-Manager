const express = require('express');
var router = express.Router();

const verifyToken = require('../middlewares/verifyToken');
const getProfile = require('../controllers/students/getProfile')
const getDues = require('../controllers/students/getDues')

router.get('/profile', verifyToken, getProfile);
router.get('/getDue', verifyToken, getDues);

module.exports = router;