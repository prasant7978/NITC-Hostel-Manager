require('dotenv').config()
var mysql = require("mysql2");

let db = mysql.createConnection(process.env.DATABASE_URL);

db.connect((err) => {
    if (err) {
      console.error('Error connecting to MySQL:', err);
      return;
    }
    console.log('Connected to MySQL');
  });
module.exports = db;