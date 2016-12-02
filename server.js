var express = require('express');
var app = express();
var path = require('path');

var __projectRoot = __dirname;

app.use(express.static(__projectRoot));

app.get('/', function (req, res) {
    res.sendFile(path.join(__projectRoot + '/index.html'));
});

console.log('Server up and running on http://localhost:3000/');
app.listen(3000);
