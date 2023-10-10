const checkIfExist = require('../../middlewares/verifyIfExist')
const verifyUser = require('../../middlewares/verifyUser')

module.exports = async(req, res) => {
    console.log(req.body);
    if(req.userType && req.username){
        console.log(req.userType);
        console.log(req.username.toUpperCase());
        const token = await verifyUser(req.userType, req.username, req.body.password)
        if(token == null){
            console.log("Wrong Credentials...")
            res.status(401).send(false)
        }
        else{
            console.log("token generated: " + token)
            res.header('auth-token', token).status(200).send(true)
        }
    }
    else{
        res.status(400).send(false)
    }
}