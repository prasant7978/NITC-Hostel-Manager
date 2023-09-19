const express = require('express');
const mysql = require('mysql2');

//Create connection
const db= require('./db/db_connection');
const { application } = require('express');
const app = express();

app.use(express.json());

app.use("/admin",require('./routes/admin'));
var server = app.listen(3001,'127.0.0.1',function(req,res){
    var host= server.address().address;
    var port = server.address().port;
    console.log(`Server running at http://${host}:${port}/`);
});
