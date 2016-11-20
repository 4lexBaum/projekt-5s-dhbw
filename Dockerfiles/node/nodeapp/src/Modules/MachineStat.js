import React from 'react';

import { Product } from '../Components/Product.js';

import { ChartContainer } from '../Components/ChartContainer.js';

import { Header } from '../Components/Header.js';

export class MachineStat extends React.Component {

        constructor(props) {
            super(props);
        }

        componentDidMount(){
        }

        componentWillUnmount(){
          //socket.removeAllListeners();
        }

        render() {
            return (
              <div>
                <Header></Header>
                <div className="chartTile">
                  <div className="chartTitle">Material Milling Heat</div>
                  <ChartContainer type="bar" containerId="material-milling-heat" bindTo="#material-milling-heat" analysisName="Material Milling Heat" socketName="MaterialMillingHeat"></ChartContainer>
                </div>
                <div className="chartTile">
                  <div className="chartTitle">Material Milling Speed</div>
                  <ChartContainer type="bar" containerId="material-milling-speed" bindTo="#material-milling-speed" analysisName="Material Milling Speed" socketName="MaterialMillingSpeed"></ChartContainer>
                </div>
                <div className="chartTile">
                  <div className="chartTitle">Material Drilling Heat</div>
                  <ChartContainer type="bar" containerId="material-drilling-heat" bindTo="#material-drilling-heat" analysisName="Material Drilling Heat" socketName="MaterialDrillingHeat"></ChartContainer>
                </div>
                <div className="chartTile">
                  <div className="chartTitle">Material Drilling Speed</div>
                  <ChartContainer type="bar" containerId="material-drilling-speed" bindTo="#material-drilling-speed" analysisName="Material Drilling Speed" socketName="MaterialDrillingSpeed"></ChartContainer>
                </div>
              </div>
            )
        }
}
