var NoticeModel = require("../../models/noticeModel");
const getUser = require("../../functions/getUser");

module.exports = async(req,res)=>{
    var user = await getUser(req.username,req.userType);

    var noticeModel = new NoticeModel();

    // if(req.userType == "Students") {
    //     noticeModel.getNoticesCountForStudents(user.hostelID).then(function(result){
    //     if(result){
    //         res.status(200).send(JSON.stringify(result['count(*)']));
    //     }else{
    //         console.log("result is undefined");
    //         res.status(500).send(JSON.stringify(null));
    //     }
    //     }).catch(function(exc){
    //         console.log(exc);
    //         res.status(500).send(JSON.stringify(null));
    //     });
    // }else if(req.userType == "Warden"){
    //     noticeModel.getNoticesCountForWarden(user.hostelID).then(function(result){
    //         if(result){
    //             res.status(200).send(JSON.stringify(result["count(*)"]));
    //         }else{
    //             console.log("result is undefined");
    //             res.status(500).send(JSON.stringify(null));
    //         }
    //     }).catch(function(exc){
    //         console.log(exc);
    //         res.status(500).send(JSON.stringify(null));
    //     })
    // }else{
        noticeModel.getNoticesCount().then(function(result){
            if(result != undefined){
                res.status(200).send(JSON.stringify(result));
            }else{
                console.log("result is undefined");
                res.status(500).send(JSON.stringify(0));
            }
        }).catch(function(exc){
            console.log(exc);
            res.status(500).send(JSON.stringify(0));
        });
    // }
}