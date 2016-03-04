'use strict';
var util = require('util');
var database = require('../helpers/sqlite');
var isValid = database.isValid;
var getProfileFromParams = database.getProfileFromParams;

module.exports = {
  searchFilterMovieTitles: searchFilterMovieTitles
};


/*
  Functions in a127 controllers used for operations should take two parameters:

  Param 1: a handle to the request object
  Param 2: a handle to the response object
 */
function searchFilterMovieTitles(req, res) {
  var filterBy = req.swagger.params.filterBy.value;
  var other = req.swagger.params.other.value;
  if (!isValid(other)) {
    res.json(400, { message: "Invalid parameters, missing (other)." });
    return;
  }
  
  var db = database.openDatabase();
  if (filterBy.equals("overview")) {
    db.serialize(function () {
      overviewFilter(db, res);
    });
    
  } else if (filterBy.equals("major")) {
    db.serialize(function () {
      majorFilter(db, res);
    });
  } else {
    res.json(400, { message: "Invalid parameters, invalid (other)." });
    return;
  }
  
  /*
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
  */
}

function overviewFilter(db, res) {
  
  db.all("SELECT * FROM Ratings WHERE movieTitle LIKE '" + movieTitle + "'", function (err, rows) {
    if (err) {
      console.log(err);
      res.json(400, {
        message: "Records not found for get request."
      });
    } else if (!isValid(rows)) {
      console.log(err, rows);
      console.log("didn't find any records");
      res.json(200, {
        movieTitle: movieTitle,
        ratings: []
      });
    } else {
      var ratings = rows.map(function (row) {
        return {
          ratingID: row.ratingID,
          rating: row.rating,
          text: row.text,
          movieTitle: row.movieTitle,
          user: row.user
        };
      });

      console.log(ratings);
      res.json(200, {
        movieTitle: movieTitle,
        ratings: ratings
      });
    }
  });
}


function majorFilter(db, res) {
  
  db.all("SELECT * FROM Ratings WHERE movieTitle LIKE '" + movieTitle + "'", function (err, rows) {
    if (err) {
      console.log(err);
      res.json(400, {
        message: "Records not found for get request."
      });
    } else if (!isValid(rows)) {
      console.log(err, rows);
      console.log("didn't find any records");
      res.json(200, {
        movieTitle: movieTitle,
        ratings: []
      });
    } else {
      var ratings = rows.map(function (row) {
        return {
          ratingID: row.ratingID,
          rating: row.rating,
          text: row.text,
          movieTitle: row.movieTitle,
          user: row.user
        };
      });

      console.log(ratings);
      res.json(200, {
        movieTitle: movieTitle,
        ratings: ratings
      });
    }
  });
}