const express = require('express')
const router = express.Router()

const loginAdmin = require('../controllers/authentication/loginAdmin')
const loginStudent = require('../controllers/authentication/loginStudent')
const verifyIfExist = require('../middlewares/verifyIfExist')

router.post('/login/admin', verifyIfExist, loginAdmin)
router.post('/login/student', verifyIfExist, loginStudent)

module.exports = router;