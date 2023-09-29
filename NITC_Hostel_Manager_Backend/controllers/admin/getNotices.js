const getUser = require("../../functions/getUser");
var NoticeModel = require("../../models/noticeModel");

module.exports = async(req,res)=>{
    var noticeModel = new NoticeModel();
    noticeModel.getNoticesForAdmin().then(function(result){
        if(result){
            res.status(200).send(JSON.stringify(result));
        }else{
            console.log("result is undefined");
            res.status(500).send(JSON.stringify(null));
        }
    }).catch(function(exc){
        console.log(exc);
        res.status(500).send(JSON.stringify(null));
    });
}