const NoticeModel = require('../../models/noticeModel')

module.exports = async(req, res) => {
    var notice = req.body;
    delete notice['noticeID'];
    console.log("\n\n\n\n");
    console.log(notice);
    const noticeModel = new NoticeModel()
    await noticeModel.issueNotice(notice).then(function(result){
        if(result == true){
            console.log("Notice created successfully");
            res.status(200).send(true)
        }
        else{
            console.log("Error in creating notice");
            res.status(500).send(false)
        }
    }).catch(function(err){
        console.log(err);
        res.status(500).send(false);
    })
}