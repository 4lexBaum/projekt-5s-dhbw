import React from 'react';

var analysisBarChart = require('../Charts/analysisBarChart.js');

var analysisCategoryChart = require('../Charts/analysisCategoryChart.js');

export class ChartContainer extends React.Component {

        constructor(props) {
            super(props);
        }

        componentDidMount(){
          if (this.props.type == "bar") {
            analysisBarChart.createChart(this.props.analysisName, this.props.socketName, this.props.bindTo);
          } else if (this.props.type == "category") {
            analysisCategoryChart.createChart(this.props.analysisName, this.props.socketName, this.props.bindTo);
          }
        }

        render() {
          return (
            <div id={this.props.containerId}></div>
          )
        }
}
