'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;

module.exports = {
  getRatings: getRatings
};

function getRatings(req, res) {
  // variables defined in the Swagger document can be referenced using req.swagger.params.{parameter_name}
  var movieTitle = database.escapeStringForSQL(req.swagger.params.movieTitle.value);
  var db = database.openDatabase();

  if (isValid(movieTitle)) {

    db.serialize(function() {
      db.get("SELECT * FROM Ratings WHERE movieTitle IS '" + movieTitle + "'", function (err, rows) {
        if (err) {
          console.log(err);
          res.json(400, {
            message: "Records not found for get request."
          });
        } else if (!isValid(rows)) {
          console.log(rows);
          console.log("didn't find any records");
          res.json(200, {
            movieTitle: movieTitle,
            ratings: []
          });
        } else {
          var ratings = rows.map(function (row) {
            return {
              ratingID: row.ratingID,
              text: row.text,
              movieTitle: row.movieTitle,
              user: row.user
            };
          });

          res.json(200, {
            movieTitle: movieTitle,
            ratings: ratings
          });
        }
      });
    });

  } else {
    res.json(400, {
      message: "Sent an invalid username for get request."
    });
  }
}
