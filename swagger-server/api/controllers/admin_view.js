'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;
var getProfileFromParams = database.getProfileFromParams;

module.exports = {
  viewUserList: viewUserList
};


/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function viewUserList(req, res) {
  
  var db = database.openDatabase();
  db.serialize(function () {
    db.get("SELECT * FROM Users", function (err, rows) {
      var arrayRows = [];
      if (typeof rows == "object" && isValid(rows.username)) {
        var profile = {
          name: "",
          profileID: -1,
          major: ""
        };
        if (rows.profile) {
          profile = {
            name: rows.profile.name,
            profileID: rows.profile.profileID,
            major: rows.profile.major
          };
        }
        arrayRows.push({
          username: rows.username,
          password: rows.password,
          isActive: rows.isActive === 1,
          isAdmin: rows.isAdmin === 1,
          profile: profile
        });
      } else {
        for (var row in rows) {
          var profile = {
            name: "",
            profileID: -1,
            major: ""
          };
          if (rows.profile) {
            profile = {
              name: rows.profile.name,
              profileID: rows.profile.profileID,
              major: rows.profile.major
            };
          }
          arrayRows.push({
            username: rows.username,
            password: rows.password,
            isActive: rows.isActive === 1,
            isAdmin: rows.isAdmin === 1,
            profile: profile
          });
        }
      }
      
      console.log(err);
      if (err || !isValid(arrayRows)) {
        res.json(200, {
          users: []
        });
      } else {
        console.log(arrayRows);
        res.json(200, {
          users: arrayRows
        });
      }

    });
  });
}
