
const express = require('express')
const fs = require('fs')
const path = require('path')
const app = express()

app.get('/', function(req, res) {
	res.sendFile(path.join(__dirname + '/res/measures.xml'))
})


let port = 3001;
app.listen(port, function () {
	console.log(`App is running on port ${port}`)
})