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
  var db = database.openDatabase();
  
  if (isValid(username)) {
    
     db.serialize(function() {
      username = database.escapeStringForSQL(username);
      db.get("SELECT * FROM Users WHERE username IS '" + username + "' LIMIT 1", function(err, row) {
        if (err) {
          console.log(err);
          res.send(400, { message: "Record not found for get request." });
        } else if (!isValid(row)) {
            res.send(400, { message: "Record does not exist." });
        } else {
          
          db.get("SELECT * FROM Profiles WHERE profileID IS '" + row.profile + "' LIMIT 1", function(err, row1) {
            if (err) {
              console.log(err);
              res.send(400, { message: "Record not found for get request." });
            } else if (!isValid(row1)) {
                res.send(400, { message: "Record does not exist." });
            } else {
              res.send(200, {
                username: row.username,
                password: row.password,
                profile: {
                  name: row1.name,
                  profileID: row1.profileID
                }
              });
            }
          });
        }
      });
    });
    
  } else {
    res.send(400, { message: "Sent an invalid username for get request." });
  }
}

function deleteUser(req, res) {
  var username = database.escapeStringForSQL(req.swagger.params.username.value);
  var db = database.openDatabase();
  
  if (isValid(username)) {
    
     db.serialize(function() {
      username = database.escapeStringForSQL(username);
      db.run("DELETE FROM Users WHERE username IS '" + username + "'", function(err) {
        if (err) {
          console.log(err);
          res.send(400, { message: "Unknown error." });
        } else if (this.changes == 0) {
          res.send(400, { message: "Record does not exist." });
        } else {
          res.send(204);
        }
      });
    });
    
  } else {
    res.send(400, { message: "Sent an invalid username for delete request." });
  }
}

function updateUser(req, res) {
  var username = database.escapeStringForSQL(req.swagger.params.username.value);
  var password = database.escapeStringForSQL(req.swagger.params.password.value);

  var profileID = "";
  var name = "";
  
  var profile = getProfileFromParams(req.swagger.params);
  
  if (profile) {
    profileID = profile.profileID;
    name = profile.name;
  } else {
    res.send(400, { message: "Sent an invalid profile for update request." });
  }
  
  var db = database.openDatabase();
  if (isValid(username)) {
    db.serialize(function() {
      db.get("SELECT * FROM Profiles WHERE profileID IS '" + profileID + "' LIMIT 1", function(err, row) {
        if (err) {
          console.log(err);
          res.send(400, { message: "Record not found for update request." });
        } else if (!isValid(row)) {
            res.send(400, { message: "Record does not exist." });
        } else {
          
          db.run("UPDATE * FROM Profiles SET name='" + name + "'WHERE profileID IS '" + profileID + "'", function(err) {
              if (err) {
                console.log(err);
                res.send(400, { message: "Record not found for update request." });
              } else if (this.changes == 0) {
                res.send(400, { message: "Record not changed." });
              } else {
                res.send(201);
              }
          });
          
        }
      });
    });
  } else {
    res.send(400, { message: "Sent an invalid username for update request." });
  }
}
