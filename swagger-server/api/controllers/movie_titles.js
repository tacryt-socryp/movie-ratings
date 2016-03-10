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
  console.log(filterBy);
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

function overviewFilter(db, res) {
  
  db.all("SELECT * FROM Ratings", function (err, rows) {
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
      callbackFilterByAvgRating(db, res, rows);
    }
  });
  
}


function majorFilter(db, res, major) {
  
  db.all("SELECT * FROM Ratings WHERE major LIKE '" + major + "'", function (err, rows) {
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
      callbackFilterByAvgRating(db, res, rows);
    }
  });
}

function callbackFilterByAvgRating(db, res, rows) {
  var objDict = {};
  /*
   movieTitle: {
    avgRating: 3.0,
    numRatings: 4
   }
  */
  rows.forEach(function (row) {
    if (typeof objDict[row.movieTitle] === "undefined") {
      objDict[row.movieTitle] = {
        avgRating: row.rating,
        numRatings: 1
      };
      
    } else {
      var avgRating = objDict[row.movieTitle].avgRating;
      var numRatings = objDict[row.movieTitle].numRatings + 1;
      avgRating = (avgRating * (numRatings - 1) + row.rating) / numRatings;
      
      objDict[row.movieTitle] = {
        avgRating: avgRating,
        numRatings: numRatings
      };
    }
  });
  
  var movieTitles = [];
  for (var key in objDict) {
    var value = objDict[key];
    if (movieTitles.length === 0) {
      movieTitles[0] = key;
    } else {
      var i = movieTitles.length;  
      while ((i > 0) && (value.avgRating >= movieTitles[i-1].avgRating))
        {   
          movieTitles[i] = movieTitles[i - 1];
          i = i - 1;
        }
      movieTitles[i] = key;
    }
  }
  
  // sort ratings into an dict of objects consisting of: avg rating, num ratings, movieTitle
  // map the dict of objects into a flat array of movieTitles

  console.log(movieTitles);
  res.json(200, {
    movieTitles: movieTitles
  });
}