const express = require('express');
var router = express.Router();

const verifyToken = require('../middlewares/verifyToken');
const getProfile = require('../controllers/students/getProfile')

router.get('/profile', verifyToken, getProfile);

module.exports = router;