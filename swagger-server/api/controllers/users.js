'use strict';
var util = require('util');
var database = require('../helpers/sqlite');

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

  var db = database.openDatabase();
  if (typeof username !== 'undefined' && username !== null) {
     db.serialize(function() {
      username = database.escapeStringForSQL(username);
      db.run("INSERT INTO Users VALUES('" + username + "', '" + password + "')", function(err) {
        if (err) {
          console.log(err);
          res.send(400, { message: "Record not created." });
        } else {
          if (this.changes == 0) {
            res.send(400, { message: "Record does not exist." });
          } else {
            res.send(201, {username: username, password: password});
          }
        }
      });
    });
  } else {
    res.send(400, { message: "Sent an invalid create request." });
  }
}
