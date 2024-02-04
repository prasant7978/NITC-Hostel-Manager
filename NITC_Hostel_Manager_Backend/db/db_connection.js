require('dotenv').config()
var mysql = require("mysql2");
// let db = mysql.createConnection({
//     host:"mysql-nitc-hostel-manager-nitc-hostel-manager.a.aivencloud.com",
//     uri:"mysql-nitc-hostel-manager-nitc-hostel-manager.a.aivencloud.com",
//     user:"avnadmin",
//     password:"AVNS_jP4p9YCMvRhZ--pKzuq",
//     database:"defaultdb",
//     port:20480,
//     connectTimeout: 15000,
// });
let db = mysql.createConnection({
  host:"localhost",
  database:"nitc_hostel_manager",
  port:"3306",
  user:"root",
  password:"Jkl!1020"
});
// const fs = require('fs');
// const path = require("path");
// // console.log("dir -= "+__dirname);
// const cafilepath = path.resolve(__dirname,"./ca.pem");
// const ca = fs.readFileSync(cafilepath)

// let db = mysql.createConnection({
//     uri:"mysql-nitc-hostel-manager-nitc-hostel-manager.a.aivencloud.com:20480",
//     user:"avnadmin",
//     database:'defaultdb',
//     port:20480,
//     host:"mysql-nitc-hostel-manager-nitc-hostel-manager.a.aivencloud.com",
//     password:"AVNS_jP4p9YCMvRhZ--pKzuq",
//     ssl:{
//         ca:ca
//     }
// });
// let db = mysql.createConnection(process.env.FREE_DATABASE_URL)
// let db = mysql.createConnection({
//     host:"localhost",
//     user:"id21859963_hostelmanager",
//     password:"Jkl!10200",
// })

// uri:"http://sql6.freemysqlhosting.net/",
// let db = mysql.createConnection({
//     host:"mysql://sql6.freemysqlhosting.net",
//     user:"sql6681746",
//     password:"TGx54mt9TS",
//     database:"sql6681746",
//     port:"3306"
// });
db.connect((err) => {
    if (err) {
      console.error('Error connecting to MySQL:', err);
      return;
    }
    console.log('Connected to MySQL');
  });
module.exports = db;