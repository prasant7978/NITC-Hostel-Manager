var NoticeModel = require("../models/noticeModel");

module.exports = async(req,res)=>{
    if(req.userType == "Admin" || req.userType == "Warden"){
        var noticeModel = new NoticeModel();
        await noticeModel.deleteNotice(req.query.noticeID).then(function(result){
            if(result && result == true){
                res.status(200).send(true);
            }else{
                console.log("Result is not true");
                res.status(500).send(false);
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(false);
        })
    }else{
        console.log("User is neither admin nor warden");
        res.status(400).send(false);
    }
}