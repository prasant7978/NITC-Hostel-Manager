var mysql = require("mysql2");
let db = mysql.createConnection({
    host:"localhost",
    user:"root",
    password:"Jkl!1020",
    database:"nitc_hostel_manager"
});

module.exports =db;