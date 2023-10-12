const db = require("./db_connection");

db.connect(function(err){
    if(err){
        throw err;
    }else{
        console.log("Connected");

        db.query('SET FOREIGN_KEY_CHECKS = 0', (error) => {
            if (error) {
              console.error('Error disabling foreign key checks:', error);
            } else {
              console.log('Foreign key checks disabled.');
            }
        });

        var databasequery = `CREATE DATABASE nitc_hostel_manager`;
        
        // db.query(databasequery,function(errDatabase,result){
        //     if(errDatabase) console.log(errDatabase);
        //     else console.log("Database created");
        // });

        var studentquery = `CREATE TABLE students(
            studentRoll VARCHAR(10),
            email VARCHAR(255),
            password VARCHAR(255),
            name VARCHAR(455),
            phone VARCHAR(55),
            parentPhone VARCHAR(255),
            gender VARCHAR(25),
            dob VARCHAR(255),
            dues DOUBLE(9,2) DEFAULT 0.00,
            address VARCHAR(755),
            course VARCHAR(255),
            hostelID VARCHAR(255),
            roomNumber int,
            FOREIGN KEY(hostelID) REFERENCES hostels(hostelID) ON DELETE CASCADE,
            PRIMARY KEY(studentRoll)
        )`;

        db.query(studentquery,function(errStudent,result){
            if(errStudent) console.log(errStudent);
            else console.log("Student created");
        });

        var adminquery = `CREATE TABLE admin(
            email VARCHAR(255),
            password VARCHAR(255),
            name VARCHAR(255),
            phone VARCHAR(255),
            PRIMARY KEY(email)
        )`;
        
        // db.query(adminquery,function(erradmin,result){
        //     if(erradmin) console.log(erradmin);
        //     else console.log("admin created");
        // });

        var wardenquery = `CREATE TABLE wardens(
            email VARCHAR(255),
            password VARCHAR(255),
            name VARCHAR(255),
            phone VARCHAR(255),
            gender VARCHAR(200),
            hostelID VARCHAR(255),
            FOREIGN KEY(hostelID) REFERENCES hostels(hostelID) ON DELETE CASCADE,
            PRIMARY KEY(email)
        )`;

        db.query(wardenquery,function(errwarden,result){
            if(errwarden) console.log(errwarden);
            else console.log("warden created");
        });

        var billquery = `CREATE TABLE bills(
            billID int NOT NULL AUTO_INCREMENT,
            studentRoll VARCHAR(10),
            amount DOUBLE(9,2),
            paid BOOLEAN,
            billType VARCHAR(300), 
            billMonth VARCHAR(10),
            billYear VARCHAR(10),
            paymentID int,
            paymentDate DATE,
            paymentTime TIME,
            hostelID VARCHAR(255),
            FOREIGN KEY(studentRoll) REFERENCES students(studentRoll) ON DELETE CASCADE,
            FOREIGN KEY(paymentID) REFERENCES payments(paymentID) ON DELETE CASCADE,
            FOREIGN KEY(hostelID) REFERENCES hostels(hostelID) ON DELETE CASCADE,
            PRIMARY KEY(billID)
        )`;

        db.query(billquery,function(errbill,result){
            if(errbill) console.log(errbill);
            else console.log("Bill created");
        });

        var hostelquery = `CREATE TABLE hostels(
            hostelID VARCHAR(255),
            capacity int,
            charges int,
            occupants int,
            occupantsGender VARCHAR(20),
            totalDues DOUBLE(11,2) DEFAULT 0.00,
            wardenEmail VARCHAR(255),
            FOREIGN KEY(wardenEmail) REFERENCES wardens(email) ON DELETE SET NULL,
            PRIMARY KEY(hostelID)
        )`;
        
        db.query(hostelquery,function(errhostel,result){
            if(errhostel) console.log(errhostel);
            else console.log("hostel created");
        });

        var roomquery = `CREATE TABLE rooms(
            roomNumber int,
            hostelID VARCHAR(255),
            studentRoll VARCHAR(10),
            FOREIGN KEY(hostelID) REFERENCES hostels(hostelID) ON DELETE CASCADE,
            FOREIGN KEY(studentRoll) REFERENCES students(studentRoll) ON DELETE SET NULL,
            PRIMARY KEY(roomNumber,hostelID)
        )`;

        db.query(roomquery,function(errroom,result){
            if(errroom) console.log(errroom);
            else console.log("room created");
        });

        var complaintquery = `CREATE TABLE complaints(
            complaintID int NOT NULL AUTO_INCREMENT,
            status VARCHAR(25),
            studentRoll VARCHAR(10),
            roomNumber int,
            message VARCHAR(2000),
            date VARCHAR(255),
            time VARCHAR(255),
            hostelID VARCHAR(255),
            FOREIGN KEY(roomNumber,hostelID) REFERENCES rooms(roomNumber,hostelID) ON DELETE CASCADE,
            FOREIGN KEY(studentRoll) REFERENCES students(studentRoll) ON DELETE CASCADE,
            PRIMARY KEY(complaintID)
        )`;

        db.query(complaintquery,function(errcomplaint,result){
            if(errcomplaint) console.log(errcomplaint);
            else console.log("complaint created");
        });

        var noticequery = `CREATE TABLE notices(
            noticeID int NOT NULL AUTO_INCREMENT,
            date VARCHAR(255),
            issuerID VARCHAR(200),
            heading VARCHAR(500),
            message VARCHAR(2000),
            hostelID VARCHAR(255),
            FOREIGN KEY(hostelID) REFERENCES hostels(hostelID) ON DELETE CASCADE,
            PRIMARY KEY(noticeID)
        )`;

        // db.query(noticequery,function(errnotice,result){
        //     if(errnotice) console.log(errnotice);
        //     else console.log("notice created");
        // });

        var paymentquery = `CREATE TABLE payments(
            paymentID int NOT NULL AUTO_INCREMENT,
            status VARCHAR(25),
            studentRoll VARCHAR(10),
            date DATE,
            time TIME,
            amount DOUBLE(9,2),
            billID VARCHAR(255),
            FOREIGN KEY(studentRoll) REFERENCES students(studentRoll) ON DELETE CASCADE,

            PRIMARY KEY(paymentID)
        )`;

        db.query(paymentquery,function(errpayment,result){
            if(errpayment) console.log(errpayment);
            else console.log("payment created");
        });
    
        db.query('SET FOREIGN_KEY_CHECKS = 1', (error) => {
            if (error) {
              console.error('Error enabling foreign key checks:', error);
            } else {
              console.log('Foreign key checks enabled.');
            }
          }
        );
    }
});
