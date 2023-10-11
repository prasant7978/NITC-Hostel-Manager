const express = require('express')
const router = express.Router()

const loginAdmin = require('../controllers/authentication/loginAdmin')
const loginStudent = require('../controllers/authentication/loginStudent')
const loginWarden = require('../controllers/authentication/loginWarden')
const verifyIfExist = require('../middlewares/verifyIfExist')

router.post('/login/admin', verifyIfExist, loginAdmin)
router.post('/login/student', verifyIfExist, loginStudent)
router.post('/login/warden', loginWarden)

module.exports = router;