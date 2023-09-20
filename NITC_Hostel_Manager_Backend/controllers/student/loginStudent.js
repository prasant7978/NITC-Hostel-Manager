const checkIfExist = require('../../middlewares/verifyIfExist')
const verifyUser = require('../../middlewares/verifyUser')

module.exports = async(req, res) => {
    const isPresent = await checkIfExist(req.body.username, req.body.userType)
    if(!isPresent){
        console.log("Student not found...")
        res.status(400).send(false)
    }
    else{
        const token = verifyUser(req.body)
        if(token == null){
            console.log("Wrong Credentials...")
            res.status(401).send(false)
        }
        else{
            console.log("token generated: " + token)
            res.header('auth-token', token).status(200).send(true)
        }
    }
}