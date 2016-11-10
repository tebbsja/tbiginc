var fs = require('fs');
var http = require('http');
var url = require('url');
var readline = require('readline');
var ROOT_DIR = "html/";
var io = require('socket.io');


var server = http.createServer(function (req, res) {

  var urlObj = url.parse(req.url, true, false);
  var nUrlObj = url.format(urlObj);

  if(urlObj.pathname.indexOf("delete") !=-1) {
    if(req.method === "POST") {

      //read data;
      var jsonData = "";
      req.on('data', function (chunk) {
        jsonData += chunk;
      });
      req.on('end', function() {
        var reqObj = JSON.parse(jsonData);
        //console.log("reqObj: " + reqObj[0]);

        var MongoClient = require('mongodb').MongoClient;
        MongoClient.connect("mongodb://localhost/tbig", function(err, db) {
          if(err) throw err;
          db.collection("messages", function(err, messages){
            if(err) throw err;
            messages.find(reqObj, function(err, items){
              items.toArray(function(err, itemArr){
            messages.remove(reqObj, function(err, results) {
                res.writeHead(200);
                res.end("success");
              });
              });
            });
          });
        });
      });
    }
  } // If this is our message REST service
  else if(urlObj.pathname.indexOf("message") !=-1) {

    if(req.method === "POST") {
      //read form data
      var jsonData = "";
      req.on('data', function (chunk) {
        jsonData += chunk;
      });
      req.on('end', function () {
        var reqObj = JSON.parse(jsonData);
        console.log(reqObj);

        // Now put it into the database
        var MongoClient = require('mongodb').MongoClient;
        MongoClient.connect("mongodb://localhost/tbig", function(err, db) {
          if(err) throw err;
          db.collection('messages').insert(reqObj,function(err, records) {
            //console.log("Record added as " + records[0]._id);
            res.writeHead(200);
            res.end("");
     });
   });
});
    } else if (req.method === "GET") {
      var str = urlObj.search;
      var agnt = str.slice("3");

      // Read all of the database entries and return them in a JSON array
      var MongoClient = require('mongodb').MongoClient;
      MongoClient.connect("mongodb://localhost/tbig", function(err, db) {
        if(err) throw err;
        db.collection("messages", function(err, messages){
          if(err) throw err;
          messages.find({agent: agnt}, function(err, items){
            items.toArray(function(err, itemArr){
              res.writeHead(200);
              res.end(JSON.stringify(itemArr));
            });
          });
        });
      });
    }
  } else if (urlObj.pathname.indexOf("user") !=-1) {

    if(req.method === "POST") {
      //read form data
      var jsonData = "";
      req.on('data', function (chunk) {
        jsonData += chunk;
      });
      req.on('end', function () {
        var reqObj = JSON.parse(jsonData);


        // Now put it into the database
        var MongoClient = require('mongodb').MongoClient;
        MongoClient.connect("mongodb://localhost/tbig", function(err, db) {
          if(err) throw err;
          db.collection('users').update(reqObj[0], reqObj[0], {upsert: true, w:1, forceServerObjectId: false}, function(err, data)
          {
              if (err)
              {
                console.log("error");
                console.log(err);
              }
              else
              {
                console.log("success");
              }//console.log("Record added as "+records[0]._id);

            res.writeHead(200);
            res.end("");
     });
   });
});
    } else if (req.method === "GET") {
      // Read all of the database entries and return them in a JSON array
      var MongoClient = require('mongodb').MongoClient;
      MongoClient.connect("mongodb://localhost/tbig", function(err, db) {
        if(err) throw err;
        db.collection("users", function(err, users){
          if(err) throw err;
          users.find(function(err, items){
            items.toArray(function(err, itemArr){
              res.writeHead(200);
              res.end(JSON.stringify(itemArr));
            });
          });
        });
      });
    }

  } else {
   // Normal static file
    fs.readFile(ROOT_DIR + urlObj.pathname, function (err,data) {
      if (err) {
        res.writeHead(404);
        res.end(JSON.stringify(err));
        return;
      }
      res.writeHead(200);
      res.end(data);
    });
  }
}).listen(8080);

var listener = io.listen(server);
listener.sockets.on('connection', function(socket){
  console.log("working");
  socket.on('test', function(data){
    console.log(data[0].name);
    console.log("data " + JSON.stringify(data));
    socket.broadcast.emit('refresh', data);

  });

});
