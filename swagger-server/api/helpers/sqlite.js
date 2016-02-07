var sqlite3 = require("sqlite3").verbose();
var fs = require("fs");
var file = __dirname + "/tomatoes.db";


module.exports = {
  openDatabase: openDatabase,
  escapeStringForSQL: escapeStringForSQL
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