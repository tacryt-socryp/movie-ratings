'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;
var getProfileFromParams = database.getProfileFromParams;

module.exports = {
  createUser: createUser
};

/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function createUser(req, res) {
  var user = req.swagger.params.user.value;
  var username = database.escapeStringForSQL(user.username);
  var password = database.escapeStringForSQL(user.password);

  var name = "";
  var profile = user.profile;
  console.log(user);
  
  if (isValid(profile) && isValid(profile.name) && isValid(username) && isValid(password)) {
    name = profile.name;

    var db = database.openDatabase();
    db.serialize(function() {
      username = database.escapeStringForSQL(username);

      db.run("INSERT INTO Profiles VALUES(null, '" + name + "')", function(err) {
        if (err) {
          console.log(err);
          res.json(400, { message: "Record not created." });
        } else {
          if (this.changes == 0) {
            res.json(400, { message: "Record does not exist." });
          } else {
            var profileID = this.lastID;

            db.run("INSERT INTO Users VALUES('" + username + "', '" + password + "', '" + profileID + "')", function(err) {
              if (err) {
                console.log(err);
                res.json(400, { message: "Record not created." });
              } else {
                console.log({
                  username: username,
                  password: password,
                  profile: {
                    name: name,
                    profileID: profileID
                  }
                });
                res.json(201, {
                  username: username,
                  password: password,
                  profile: {
                    name: name,
                    profileID: profileID
                  }
                });
              }
            });
      
          }
        }
      });
    });
  } else {
    res.json(400, { message: "Sent an invalid create request." });
  }
}
