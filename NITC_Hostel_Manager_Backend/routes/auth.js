const epress = require('express')
const router = express.Router()

const loginAdmin = require('../controllers/admin/loginAdmin')
const loginStudent = require('../controllers/student/loginStudent')

router.post('/login/admin', loginAdmin)
router.post('/login/student', loginStudent)