var sqlite3 = require("sqlite3").verbose();
var fs = require("fs");
var file = __dirname + "/tomatoes.db";


module.exports = {
  openDatabase: openDatabase
};

function openDatabase() {
  var db = new sqlite3.Database(file);
  var exists = fs.existsSync(file);
  
  db.serialize(function() {
    if(!exists) {
      db.run("CREATE TABLE Users (username TEXT, password TEXT)");
    }
  });
  return db;
}