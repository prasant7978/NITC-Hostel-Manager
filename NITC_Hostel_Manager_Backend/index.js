const express = require('express');

//Create connection
const db= require('./db/db_connection');
const app = express();

app.use(express.json());

app.use("/auth", require('./routes/auth'));

app.use("/admin",require('./routes/admin'));
app.use("/complaints",require("./routes/complaints"))
app.use("/notices",require("./routes/notices"))
app.use("/students",require("./routes/students"))

<<<<<<< HEAD
var server = app.listen(3001,'192.168.217.62',function(req,res){
=======
app.use("/student", require('./routes/student'));

var server = app.listen(3001,'192.168.50.134',function(req,res){
>>>>>>> 6d2163d9ef74e76c289ab7d91626da90de0721e5
    var host= server.address().address;
    var port = server.address().port;
    console.log(`Server running at http://${host}:${port}/`);
});
