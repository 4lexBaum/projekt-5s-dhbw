import React from 'react';

import { ChartContainer } from '../Components/ChartContainer.js';

var heatChart = require('../Charts/heatChart.js');
var speedChart = require('../Charts/speedChart.js');

export class MachineData extends React.Component {
  constructor(props) {
      super(props);
  }

  componentDidMount(){
    heatChart.createChart();
    speedChart.createChart();
  }

  render() {
      return (
        <div className="machineData">
          <div className="tile machineValue">
            <p className="speedTitle">Milling Speed</p>
            <ChartContainer containerId="speed-chart"></ChartContainer>
          </div>
          <div className="tile machineIconBox">
            <p className="machineTitle">Machine</p>
            <img id="machineIcon" className="iconArea" src="http://www.freeiconspng.com/uploads/mill-icon-16.png"></img>
          </div>
          <div className="tile machineValue" id="rightValue">
            <p className="heatTitle">Milling Heat</p>
            <ChartContainer containerId="heat-chart"></ChartContainer>
          </div>
        </div>
      )
  }
}
