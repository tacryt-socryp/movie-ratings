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
      console.log("didn't find any records");
      res.json(200, {
        movieTitles: []
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
      console.log("didn't find any records");
      res.json(200, {
        movieTitles: []
      });
    } else {
      callbackFilterByAvgRating(db, res, rows);
    }
  });
}

function callbackFilterByAvgRating(db, res, rows) {
  var objDict = {};
  var movieTitles = [];
  
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
      movieTitles.push(row.movieTitle);
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
  
  movieTitles.sort(function(a, b) {
    var aObj = objDict[a];
    var bObj = objDict[b];
    if (bObj.avgRating === aObj.avgRating) {
      return bObj.numRatings - aObj.numRatings;
    }
    return bObj.avgRating - aObj.avgRating;
  });
  
  // sort ratings into an dict of objects consisting of: avg rating, num ratings, movieTitle
  // map the dict of objects into a flat array of movieTitles

  res.json(200, {
    movieTitles: movieTitles
  });
}