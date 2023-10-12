const express = require('express')
var router = express.Router()

const verifyToken = require("../middlewares/verifyToken")
const generateBill = require("../controllers/bill/generateBill")
const getOwnBills = require("../controllers/bill/getOwnBills")
const getAllBills = require("../controllers/bill/getAllBills")

router.post("/generateBill", verifyToken, generateBill)
router.get("/getAllOwnBills", verifyToken, getOwnBills)
router.get("/getAllBills", verifyToken, getAllBills)

module.exports = router