'use strict';
var util = require('util');

module.exports = {
  createUser: createUser
};

/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function createUser(req, res) {
  // variables defined in the Swagger document can be referenced using req.swagger.params.{parameter_name}
  var body = req.swagger.params.body;
  console.log(body);

  // username.value || 'stranger';
  // var hello = util.format('Hello, %s!', name);

  // this sends back a JSON response which is a single string
  res.json(body);
}
