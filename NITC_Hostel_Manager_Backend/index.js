const express = require('express');

//Create connection
const db= require('./db/db_connection');
const app = express();

app.use(express.json());

app.use("/auth", require('./routes/auth'));

app.use("/admin",require('./routes/admin'));

app.use("/student", require('./routes/student'));

var server = app.listen(3001,'192.168.50.134',function(req,res){
    var host= server.address().address;
    var port = server.address().port;
    console.log(`Server running at http://${host}:${port}/`);
});
