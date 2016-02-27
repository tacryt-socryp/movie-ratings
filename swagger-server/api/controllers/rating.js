'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;
var getProfileFromParams = database.getProfileFromParams;

module.exports = {
  createRating: createRating
};


/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function createRating(req, res) {
  var rating = req.swagger.params.rating.value;
  var text = database.escapeStringForSQL(rating.text);
  var ratingNum = parseInt(rating.rating, 10);
  var movieTitle = database.escapeStringForSQL(rating.movieTitle);
  var user = rating.user;
  console.log(rating.movieTitle);
  
  if (isValid(rating)) {

    var db = database.openDatabase();
    db.serialize(function () {

      console.log()
      db.run("INSERT INTO Ratings VALUES(null, " + ratingNum + ", '" +
             text + "', '" + movieTitle + "', '" + user + "')",
             function(err) {
        if (err) {
          console.log(err);
          res.json(400, { message: "Record not created." });
        } else {
          if (this.changes === 0) {
            res.json(400, { message: "Record does not exist." });
          } else {
            var ratingID = this.lastID;
            res.json(201, {
              ratingID: ratingID,
              rating: ratingNum,
              text: text,
              movieTitle: movieTitle,
              user: user
            });
          }
        }
      });
    });
  } else {
    res.json(400, { message: "Sent an invalid create request." });
  }
}