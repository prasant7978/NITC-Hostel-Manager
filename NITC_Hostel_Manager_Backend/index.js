const express = require('express');

//Create connection
const db= require('./db/db_connection');
const app = express();

app.use(express.json());

app.use("/auth", require('./routes/auth'));

app.use("/admin", require('./routes/admin'));

app.use("/complaints", require("./routes/complaints"))

app.use("/notices", require("./routes/notices"))

app.use("/students", require("./routes/students"))

app.use("/hostels", require("./routes/hostel"))

app.use("/rooms", require("./routes/room"))

<<<<<<< HEAD
var server = app.listen(3001,'192.168.217.62',function(req,res){
=======
app.use("/warden", require("./routes/warden"))

var server = app.listen(3001,'192.168.50.134',function(req,res){
>>>>>>> 0178d6f3b9805b462e7beb4b211f32ece70ce270
    var host= server.address().address;
    var port = server.address().port;
    console.log(`Server running at http://${host}:${port}/`);
});
