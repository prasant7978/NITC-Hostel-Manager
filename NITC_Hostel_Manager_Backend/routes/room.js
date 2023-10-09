var verifyToken = require("../middlewares/verifyToken");

const getAllRooms = require('../controllers/room/getAllRooms')
const getAvailableRooms = require('../controllers/room/getAvailableRooms')
const allocateRoom = require('../controllers/room/allocateRoom')
const deallocateRoom = require('../controllers/room/deallocateRoom')
const deleteRoom = require('../controllers/room/deleteRoom')
const addRoom = require('../controllers/room/addRoom')

router.get("/allRooms", verifyToken, getAllRooms)
router.get("/availableRooms", verifyToken, getAvailableRooms)
router.get("/allocateRoom", verifyToken, allocateRoom)
router.get("/deallocateRoom", verifyToken, deallocateRoom)
router.get("/deleteRoom", verifyToken, deleteRoom)
router.get("/addRoom", verifyToken, addRoom)