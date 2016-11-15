import React from 'react';

import { LightBarrier } from '../Components/LightBarrier.js';

export class Machine extends React.Component {
  constructor(props) {
      super(props);
  }

  render() {
      return (
        <div id="machineBox">
          <div id="areasBox">
            <div id="area1" className="area">
              <div className="areaNumber">1</div>
              <img className="product" src="./icon_product.png"></img>
              <LightBarrier lightBarrierId="lb1"></LightBarrier>
            </div>
            <div id="area2" className="area">
              <div className="areaNumber">2</div>
              <LightBarrier lightBarrierId="lb2"></LightBarrier>
            </div>
            <div id="area3" className="area">
              <div className="areaNumber">3</div>
              <LightBarrier lightBarrierId="lb3"></LightBarrier>
            </div>
            <div id="area4" className="area">
              <div className="areaNumber">4</div>
              <LightBarrier lightBarrierId="lb4"></LightBarrier>
            </div>
            <div id="area5" className="area">
              <div className="areaNumber">5</div>
              <LightBarrier lightBarrierId="lb5"></LightBarrier>
            </div>
          </div>

          <div>
            <div id="conveyor"></div>
              <div id="pillarBox">
                <div className="pillarLeft pillar"></div>
                <div className="pillar"></div>
                <div className="pillar"></div>
                <div className="pillar"></div>
                <div className="pillar"></div>
          	  <div className="pillarRight pillar"></div>
            </div>
          </div>
        </div>
      )
  }
}
