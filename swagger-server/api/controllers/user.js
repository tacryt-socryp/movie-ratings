'use strict';
var util = require('util');
var db = require('../helpers/sqlite');

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
  var body = req.swagger.params.body;
  console.log(body);

  var database = db.openDatabase();
  console.log(database);
  // db.get('')
  // username.value || 'stranger';
  // var hello = util.format('Hello, %s!', name);

  // this sends back a JSON response which is a single string
  res.json(body);
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
