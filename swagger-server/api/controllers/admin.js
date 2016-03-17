'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;
var getProfileFromParams = database.getProfileFromParams;

module.exports = {
  banOrUnbanUser: banOrUnbanUser
};


/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function banOrUnbanUser(req, res) {
  var username = req.swagger.params.username.value;
  var shouldBan = req.swagger.params.shouldBan.value;
  
  if (!isValid(username) || !isValid(shouldBan)) {
    res.json(400, { message: "Invalid parameters, missing (username or shouldBan)." });
    return;
  }
  var isActive = 0;
  if (shouldBan === false) {
    isActive = 1;
  } 
  
  var db = database.openDatabase();
  db.serialize(function () {
    db.get("SELECT * FROM Users where username LIKE '" + username + "' LIMIT 1", function (err0, row0) {
      if (err0 || !isValid(row0)) {
        res.json(400, { message: "Record not found for ban request." });
        return;
      }
      db.get("SELECT * FROM Profiles WHERE profileID IS '" + row0.profile + "' LIMIT 1", function(err1, row) {
        if (err1 || !isValid(row)) {
          res.json(400, { message: "Record not found for ban request." });
        } else {

          db.run("UPDATE Users SET isActive='" + isActive + "' WHERE username LIKE '" + username + "'", function(err) {
              console.log(err);
              if (err) {
                console.log(err);
                res.json(400, { message: "Something is fucked up." });
              } else if (this.changes == 0) {
                res.json(400, { message: "Record not changed." });
              } else {
                res.json(200, {
                  username: username,
                  password: row0.password,
                  isAdmin: row0.isAdmin === 1,
                  isActive: row0.isActive === 1,
                  profile: {
                    profileID: row.profileID,
                    name: row.name,
                    major: row.major
                  }
                });
              }
            
          });

        }
      });

    });
  });
}
