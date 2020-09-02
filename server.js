
const express = require('express')
const fs = require('fs')
const path = require('path');
const e = require('express');
const app = express()
const parseString = require('xml2js').parseString;

const measures = fs.readFileSync(`${__dirname}/res/measures.xml`, "utf-8")


function findMetricByClass(classMetric, parsedXMLMetrics, obj){
	parsedXMLMetrics["METRICS"]["METRIC"].forEach(element => {
		if(element["$"].abbreviation === classMetric){
			obj = { "name":  element["$"].name}
			obj.values = []
			element["VALUE"].forEach(value => {
				let len = obj.values.length
				obj.values[len] = {}
				obj.values[len] = {"className":value["$"]["measured"], "value": value["$"]["value"]}
			});
		}
	});
	return obj;
}

app.get('/api', function(req, res) {
	console.log("Got a request")
	//Get the requested metric name
	const classOfMetric = req.query.metric;
	var response = "";
	let obj = {}
	const result = parseString(measures, function(err, result){
		obj = findMetricByClass(classOfMetric, result, obj);
	})
	res.status(200).json(obj) 
})




let port = 3001;
app.listen(port, function () {
	console.log(`App is running on port ${port}`)
})