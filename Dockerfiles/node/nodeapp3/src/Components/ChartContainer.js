import React from 'react';

var analysisBarChart = require('../Charts/analysisBarChart.js');

export class ChartContainer extends React.Component {

        constructor(props) {
            super(props);
        }

        componentDidMount(){
          analysisBarChart.createChart(this.props.analysisName, this.props.socketName, this.props.bindTo);
        }

        render() {
          return (
            <div id={this.props.containerId}></div>
          )
        }
}
