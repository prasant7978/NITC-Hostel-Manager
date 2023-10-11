const checkExist = require('../../middlewares/verifyIfExist')
const verifyUser = require('../../middlewares/verifyUser')

module.exports = async(req, res) => {
    console.log(req.body);
    if(req.body.userType && req.body.username){
        const token = await verifyUser(req.body.userType, req.body.username, req.body.password)
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
        res.status(401).send(false);
    }
}