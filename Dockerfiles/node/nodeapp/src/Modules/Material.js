import React from 'react';

import { Product } from '../Components/Product.js';

import { ChartContainer } from '../Components/ChartContainer.js';

import { Header } from '../Components/Header.js';

import { Grid } from 'react-bootstrap';

import { Row } from 'react-bootstrap';

import { Col } from 'react-bootstrap';

var testChart = require('../Charts/lineChart.js');
var gaugeChart = require('../Charts/gaugeChart.js');
var barChart = require('../Charts/barChart.js');
var pieChart = require('../Charts/pieChart.js');

export class Material extends React.Component {

        constructor(props) {
            super(props);
        }

        componentDidMount(){
          testChart.createChart();
          gaugeChart.createChart();
          barChart.createChart();
          pieChart.createChart();
        }

        componentWillUnmount(){
          //socket.removeAllListeners();
        }

        render() {
            return (
              <div>
                <Header></Header>
                <div className="chartTile">
                  <div className="chartTitle">Material Produced Amount</div>
                  <ChartContainer type="bar" containerId="material-produced-amount" bindTo="#material-produced-amount" analysisName="Material Produced Amount" socketName="MaterialProducedAmount"></ChartContainer>
                </div>
                <div className="chartTile">
                  <div className="chartTitle">Material Duration</div>
                  <ChartContainer type="bar" containerId="material-duration" bindTo="#material-duration" analysisName="Material Duration" socketName="MaterialDuration"></ChartContainer>
                </div>
                <div className="chartTile">
                  <div className="chartTitle">Quality Material Percentage</div>
                  <ChartContainer type="category" containerId="quality-material" bindTo="#quality-material" analysisName="Quality Material Percentage" socketName="MaterialQualityPercentage"></ChartContainer>
                </div>
              </div>
            )
        }
}

//<ChartContainer type="bar" containerId="quality-material" bindTo="#quality-material" analysisName="Quality Material" socketName="QualityMaterial"></ChartContainer>
