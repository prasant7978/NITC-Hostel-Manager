const express = require('express')
const router = express.Router()

const loginAdmin = require('../controllers/admin/loginAdmin')
const loginStudent = require('../controllers/student/loginStudent')
const verifyIfExist = require('../middlewares/verifyIfExist')

router.post('/login/admin', verifyIfExist, loginAdmin)
router.post('/login/student', loginStudent)

module.exports = router;