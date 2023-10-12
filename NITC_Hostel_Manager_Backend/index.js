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

app.use("/warden", require("./routes/warden"))

app.use("/payment", require("./routes/payment"))

var server = app.listen(3001,'192.168.217.62',function(req,res){
    var host= server.address().address;
    var port = server.address().port;
    console.log(`Server running at http://${host}:${port}/`);
});
