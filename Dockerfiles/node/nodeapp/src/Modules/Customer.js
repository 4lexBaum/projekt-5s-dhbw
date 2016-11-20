import React from 'react';

import { ChartContainer } from '../Components/ChartContainer.js';

import { Header } from '../Components/Header.js';

export class Customer extends React.Component {

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
                    <div className="chartTitle">Customer Order Amount</div>
                    <ChartContainer type="bar" containerId="customer-order-amount" bindTo="#customer-order-amount" analysisName="Customer Order Amount" socketName="CustomerOrderAmount"></ChartContainer>
                  </div>
                  <div className="chartTile">
                    <div className="chartTitle">Quality Customer Percentage</div>
                    <ChartContainer type="category" containerId="quality-customer" bindTo="#quality-customer" analysisName="Quality Customer Percentage" socketName="CustomerQualityPercentage"></ChartContainer>
                  </div>
                </div>
            )
        }
}
//<ChartContainer type="bar" containerId="quality-customer" bindTo="#quality-customer" analysisName="Quality Customer" socketName="QualityCustomer"></ChartContainer>
