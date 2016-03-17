'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;
var getProfileFromParams = database.getProfileFromParams;

module.exports = {
  getUser: getUser,
  deleteUser: deleteUser,
  updateUser: updateUser
};

/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function getUser(req, res) {
  // variables defined in the Swagger document can be referenced using req.swagger.params.{parameter_name}
  var username = database.escapeStringForSQL(req.swagger.params.username.value);
  var password = database.escapeStringForSQL(req.swagger.params.password.value);
  var db = database.openDatabase();
  
  if (isValid(username)) {
    
     db.serialize(function() {
      username = database.escapeStringForSQL(username);
      db.get("SELECT * FROM Users WHERE username IS '" + username + "' LIMIT 1", function(err, row) {
        console.log(row.isActive);
        if (err) {
          console.log(err);
          res.json(400, { message: "Record not found for get request." });
        } else if (!isValid(row)) {
            console.log(row);
            res.json(400, { message: "Record does not exist." });
        } else if (row.password !== password) {
            res.json(403, { message: "Incorrect password." });
        } else if (row.isActive !== 1) {
            res.json(403, { message: "Account is locked or banned."});
        } else {
          db.get("SELECT * FROM Profiles WHERE profileID IS '" + row.profile + "' LIMIT 1", function(err, row1) {
            if (err) {
              console.log(err);
              res.json(400, { message: "Record not found for get request." }); 
            } else if (!isValid(row1)) {
              if (row.isAdmin) {
                res.json(200, {
                  username: row.username,
                  password: row.password,
                  isActive: row.isActive === 1,
                  isAdmin: row.isAdmin === 1,
                  profile: {
                    name: "",
                    profileID: -1,
                    major: ""
                  }
                });
              } else {
                res.json(400, { message: "Record does not exist." });
              }
            } else {
              res.json(200, {
                username: row.username,
                password: row.password,
                isActive: row.isActive === 1,
                isAdmin: row.isAdmin === 1,
                profile: {
                  name: row1.name,
                  profileID: row1.profileID,
                  major: row1.major
                }
              });
            }
          });
        }
      });
    });
    
  } else {
    res.json(400, { message: "Sent an invalid username for get request." });
  }
}

function deleteUser(req, res) {
  var username = database.escapeStringForSQL(req.swagger.params.username.value);
  var password = database.escapeStringForSQL(req.swagger.params.password.value);
  var db = database.openDatabase();
  
  if (isValid(username)) {
    
     db.serialize(function() {
      username = database.escapeStringForSQL(username);
      db.run("DELETE FROM Users WHERE username IS '" + username + "' AND password IS '" + password + "'", function(err) {
        if (err) {
          console.log(err);
          res.json(400, { message: "Unknown error." });
        } else if (this.changes == 0) {
          res.json(400, { message: "Record does not exist." });
        } else {
          res.json(204);
        }
      });
    });
    
  } else {
    res.json(400, { message: "Sent an invalid username for delete request." });
  }
}

function updateUser(req, res) {
  var username = database.escapeStringForSQL(req.swagger.params.username.value);
  var password = database.escapeStringForSQL(req.swagger.params.password.value);

  var profileID = "";
  var name = "";
  var major = "";
  
  var profile = getProfileFromParams(req.swagger.params);
  
  if (profile) {
    profileID = profile.profileID;
    name = profile.name;
    major = profile.major;
  } else {
    res.json(400, { message: "Sent an invalid profile for update request." });
  }
  
  var db = database.openDatabase();
  if (isValid(username)) {
    db.serialize(function() {
      db.get("SELECT * FROM Users where username IS '" + username + "' LIMIT 1", function (err0, row0) {
        
      db.get("SELECT * FROM Profiles WHERE profileID IS '" + profileID + "' LIMIT 1", function(err1, row) {
        if (err1 || err0) {
          console.log(err1);
          res.json(400, { message: "Record not found for update request." });
        } else if (!isValid(row) || !isValid(row0)) {
            res.json(400, { message: "Record does not exist." });
        } else if (row0.password !== password) {
            res.json(403, { message: "Incorrect password." });
        } else {
          
          db.run("UPDATE Profiles SET name='" + name + "', major='" + major + "' WHERE profileID IS '" + profileID + "'", function(err) {
              if (err) {
                console.log(err);
                res.json(400, { message: "Record not found for update request." });
              } else if (this.changes == 0) {
                res.json(400, { message: "Record not changed." });
              } else {
                res.json(201, {
                  username: username,
                  password: password,
                  isAdmin: row0.isAdmin === 1,
                  isActive: row0.isActive === 1,
                  profile: {
                    profileID: profileID,
                    name: name,
                    major: major
                  }
                });
              }
          });
          
        }
      });
        
      });
    });
  } else {
    res.json(400, { message: "Sent an invalid username for update request." });
  }
}
