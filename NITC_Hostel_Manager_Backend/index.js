const express = require('express');

//Create connection
const db= require('./db/db_connection');
const app = express();
app.get('/', (req, res) => {
  res.send('Hello, World!');
});
app.use(express.json());

app.use("/auth", require('./routes/auth'));

app.use("/admin", require('./routes/admin'));

app.use("/complaints", require("./routes/complaints"))

app.use("/notices", require("./routes/notices"))

app.use("/students", require("./routes/students"))

app.use("/profile",require("./routes/profile"));

app.use("/hostels", require("./routes/hostel"))

app.use("/rooms", require("./routes/room"))

app.use("/warden", require("./routes/warden"))

app.use("/payment", require("./routes/payment"))

app.use("/bill", require("./routes/bill"))
 app.listen(3002,'192.168.212.64',function(req,res){

});
