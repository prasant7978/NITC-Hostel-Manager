const express = require('express')
var router = express.Router()

const verifyToken = require("../middlewares/verifyToken")
const generateBill = require("../controllers/bill/generateBill")
const getOwnBills = require("../controllers/bill/getOwnBills")
const getUnpaidBills = require("../controllers/bill/getUnpaidBills")
const getAllBills = require("../controllers/bill/getAllBills")
const getBillsCount = require("../controllers/bill/getBillsCount")

router.post("/generateBill", verifyToken, generateBill)
router.get("/ownBills", verifyToken, getOwnBills)
router.get("/unpaidBills", verifyToken, getUnpaidBills)
router.get("/getAllBills", verifyToken, getAllBills)
router.get("/billCount", verifyToken, getBillsCount)

module.exports = router