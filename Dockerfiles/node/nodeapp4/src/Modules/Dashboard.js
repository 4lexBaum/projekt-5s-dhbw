import React from 'react';

import { Product } from '../Components/Product.js';

import { ChartContainer } from '../Components/ChartContainer.js';

import { Header } from '../Components/Header.js';

import { Tile } from '../Components/Tile.js';

import { Grid } from 'react-bootstrap';

import { Row } from 'react-bootstrap';

import { Col } from 'react-bootstrap';

export class Dashboard extends React.Component {

        constructor(props) {
            super(props);
        }

        render() {
            return (
                <Grid fluid="true">
                  <Row className="show-grid">
                    <Col md={16}>
                      <Header></Header>
                    </Col>
                  </Row>
                  <Row id="nonFluid">
                    <Col md={3}>
                      <Tile containerId="drillingTile" streamId="DRILLING_SPEED" borders="5, 10, 15, 17.5" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="https://d30y9cdsu7xlg0.cloudfront.net/png/170362-200.png" value="0" unit="T RPM" title="Drilling Speed" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="drillingTile2" streamId="DRILLING_HEAT" borders="50, 100, 150, 200" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="https://d30y9cdsu7xlg0.cloudfront.net/png/170362-200.png" value="0" unit="°C" title="Drilling Heat" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="millingTile" streamId="MILLING_SPEED" borders="5, 10, 15, 17.5" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="http://www.freeiconspng.com/uploads/mill-icon-16.png" value="0" unit="T RPM" title="Milling Speed" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="millingTile2" streamId="MILLING_HEAT" borders="50, 100, 150, 200" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="http://www.freeiconspng.com/uploads/mill-icon-16.png" value="0" unit="°C" title="Milling Heat" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="drillingTile3" streamId="LIGHT_BARRIER" borders="" colors="" icon="http://freeflaticons.com/wp-content/uploads/2014/09/placeholder-copy-1411475612k48gn.png" value="0" unit="Barrier" title="Product Location" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="drillingTile" streamId="DRILLING_SPEED" borders="5, 10, 15, 17.5" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="https://d30y9cdsu7xlg0.cloudfront.net/png/170362-200.png" value="0" unit="T RPM" title="Drilling Speed" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="drillingTile2" streamId="DRILLING_HEAT" borders="50, 100, 150, 200" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="https://d30y9cdsu7xlg0.cloudfront.net/png/170362-200.png" value="0" unit="°C" title="Drilling Heat" status="stopped"></Tile>
                    </Col>
                    <Col md={3}>
                      <Tile containerId="millingTile" streamId="MILLING_SPEED" borders="5, 10, 15, 17.5" colors="#1e90ff,#00bfff,#60B044,#F6C600,#FF0000" icon="http://www.freeiconspng.com/uploads/mill-icon-16.png" value="0" unit="T RPM" title="Milling Speed" status="stopped"></Tile>
                    </Col>
                  </Row>
              </Grid>
            )
        }
}
/*
<!--
<Row>
  <Col md={6}>
    <ChartContainer containerId="test-chart"></ChartContainer>
  </Col>
  <Col md={6} id="gauge">
    <ChartContainer containerId="gauge-chart"></ChartContainer>
  </Col>
</Row>
<Row>
  <Col md={6}>
    <ChartContainer containerId="bar-chart"></ChartContainer>
  </Col>
  <Col md={6}>
    <ChartContainer containerId="pie-chart"></ChartContainer>
  </Col>
</Row>
-->

*/
