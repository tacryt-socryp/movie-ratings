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
  
  if (!isValid(other)) {
    res.json(400, { message: "Invalid parameters, missing (other)." });
    return;
  }
  
  var db = database.openDatabase();
  if (filterBy === "overview") {
    db.serialize(function () {
      overviewFilter(db, res);
    });
    
  } else if (filterBy === "major") {
    db.serialize(function () {
      majorFilter(db, res, other);
    });
  } else {
    res.json(400, { message: "Invalid parameters, invalid (other)." });
    return;
  }
}
