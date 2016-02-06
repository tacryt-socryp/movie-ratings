var sqlite3 = require('sqlite3').verbose();


module.exports = {
  openDatabase: openDatabase
};

function openDatabase() {
  var db = new sqlite3.Database('./tomatoes.db');
  return db;
}