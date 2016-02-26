var sqlite3 = require("sqlite3").verbose();
var fs = require("fs");
var file = __dirname + "/tomatoes.db";


module.exports = {
  openDatabase: openDatabase,
  escapeStringForSQL: escapeStringForSQL,
  isValid: typeChecker,
  getProfileFromParams: getProfileFromParams
};

function openDatabase() {
  var db = new sqlite3.Database(file, function() {
      db.run('PRAGMA foreign_keys=on');
  });
  var exists = fs.existsSync(file);
  
  db.serialize(function() {
    if(!exists) {
      db.run(
        "CREATE TABLE Users (username TEXT PRIMARY KEY UNIQUE, password TEXT NOT NULL, profile INTEGER REFERENCES Profiles(profileID))"
      );
      db.run(
        "CREATE TABLE Profiles (profileID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)"
      );
      db.run(
        "CREATE TABLE Ratings (ratingID INTEGER PRIMARY KEY AUTOINCREMENT, rating INTEGER NOT NULL, text TEXT NOT NULL, movieTitle text NOT NULL, user TEXT REFERENCES Users(username))"
      );
    }
  });
  return db;
}

// Code snippet taken from StackOverflow: http://stackoverflow.com/questions/7744912/making-a-javascript-string-sql-friendly
function escapeStringForSQL(str) {
  var regex = new RegExp(/[\0\x08\x09\x1a\n\r"'\\\%]/g)
  var escaper = function escaper(char){
    var m = ['\\0', '\\x08', '\\x09', '\\x1a', '\\n', '\\r', "'", '"', "\\", '\\\\', "%"];
    var r = ['\\\\0', '\\\\b', '\\\\t', '\\\\z', '\\\\n', '\\\\r', "''", '""', '\\\\', '\\\\\\\\', '\\%'];
    return r[m.indexOf(char)];
  };
  
  str = str.replace(regex, escaper);
  return str;
}

function typeChecker(anything) {
  return (typeof anything !== "undefined") && (anything !== null);
}

function getProfileFromParams(params) {
  var profile = params.profile.value;
  
  if (typeChecker(profile) && typeChecker(profile.profileID) && typeChecker(profile.name)) {
    return profile;
  } else {
    return null;
  }
}