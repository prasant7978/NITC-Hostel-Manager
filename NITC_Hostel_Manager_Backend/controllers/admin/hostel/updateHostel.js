const HostelModel = require('../../../models/hostelModel')
const RoomModel = require('../../../models/roomModel')
var db = require("../../../db/db_connection");

module.exports = async(req, res)=>{
    if(req.userType == "Admin"){
        var hostelModel = new HostelModel();
        db.query('SET FOREIGN_KEY_CHECKS = 0', (error) => {
            if (error) {
              console.error('Error disabling foreign key checks:', error);
            } else {
              console.log('Foreign key checks disabled.');
            }
        });

        hostelModel.updateHostel(req.body, req.query.hostelID).then(function(updatedHostel){
            if(updatedHostel == true){
                console.log("hostel is updated");
                res.status(200).send(true)
            }else{
                console.log("Hostel is not created");
                res.status(500).send(false);
            }
        }).catch(function(error){
            console.log("Error in updating hostel: " + error);
            res.status(500).send(false);
        });

        db.query('SET FOREIGN_KEY_CHECKS = 1', (error) => {
            if (error) {
              console.error('Error disabling foreign key checks:', error);
            } else {
              console.log('Foreign key checks disabled.');
            }
        });
    }else{
        console.log("User is not admin");
        res.status(400).send(false);
    }
}