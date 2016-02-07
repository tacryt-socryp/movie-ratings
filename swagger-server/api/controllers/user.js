'use strict';
var util = require('util');
var database = require('../helpers/sqlite');

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
  var username = req.swagger.params.username.value;
  var db = database.openDatabase();
  console.log(db);
  if (typeof username !== 'undefined' && username !== null) {
     db.serialize(function() {
      db.get("SELECT * FROM Users WHERE username IS " + username, function(err, row) {
        console.log(err, row);
        if (!err) {
          res.send(400, { message: "Record not found." });
        } else {
          // TODO: use row information
          res.send({username: 'user', password: 'pass'});
        }
      });
    });
  } else {
    res.send(400, { message: "Sent an invalid username." });
  }
  // db.get('')
  // username.value || 'stranger';
  // var hello = util.format('Hello, %s!', name);

  // this sends back a JSON response which is a single string
  
}

function deleteUser(req, res) {
  // variables defined in the Swagger document can be referenced using req.swagger.params.{parameter_name}
  var body = req.swagger.params.body;
  console.log(body);

  // username.value || 'stranger';
  // var hello = util.format('Hello, %s!', name);

  // this sends back a JSON response which is a single string
  res.json(body);
}

function updateUser(req, res) {
  // variables defined in the Swagger document can be referenced using req.swagger.params.{parameter_name}
  var body = req.swagger.params.body;
  console.log(body);

  // username.value || 'stranger';
  // var hello = util.format('Hello, %s!', name);

  // this sends back a JSON response which is a single string
  res.json(body);
}
