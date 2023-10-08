var mysql = require("mysql2");
let db = mysql.createConnection({
    host:"localhost",
    user:"root",
    password:"Prasant@7978",
    database:"nitc_hostel_manager"
});

module.exports =db;