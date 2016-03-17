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
    db.all("SELECT * FROM Users", function (err, rows) {
      var arrayRows = [];
      for (var x in rows) {
        var row = rows[x];
        if (isValid(row.username)) {
          var profile = {
            name: "",
            profileID: -1,
            major: ""
          };
          if (rows.profile) {
            profile = {
              name: "",
              profileID: rows.profile,
              major: ""
            };
          }
          arrayRows.push({
            username: row.username,
            password: row.password,
            isActive: row.isActive === 1,
            isAdmin: row.isAdmin === 1,
            profile: profile
          });
        }
      }
      
      res.json(200, {
        users: arrayRows
      });

    });
  });
}
