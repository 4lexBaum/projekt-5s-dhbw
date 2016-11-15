import React from 'react';

export class SpectralData extends React.Component {
  constructor(props) {
      super(props);
      this.state = {overallStatus: "OK", em1: "n.A.", a1: "n.A.", b1: "n.A."};
  }

  componentDidMount(){
    var that = this;
    socket.on("manufacturingData", function(msg){
      var data = msg.analysisData;
      var overallStatus = data.overallStatus;
      var em1 = data.em1.toFixed(2);
      var a1 = data.a1.toFixed(2);
      var b1 = data.b1.toFixed(2);
      that.setState({overallStatus: overallStatus, em1: em1, a1: a1, b1: b1});
    })
  }

  render() {
      return (
        <div className="erpContainer">
          <p className="erptitle">Spectral Data</p>
          <div className="erpcontent">
            <p className="erpcontenttitle">Overall Status</p>
            <p className="erpdata">{this.state.overallStatus}</p>
          </div>
          <div className="erpcontent">
            <p className="erpcontenttitle">em1</p>
            <p className="erpdata">{this.state.em1}</p>
          </div>
          <div className="erpcontent">
            <p className="erpcontenttitle">a1</p>
            <p className="erpdata">{this.state.a1}</p>
          </div>
          <div className="erpcontent">
            <p className="erpcontenttitle">b1</p>
            <p className="erpdata">{this.state.b1}</p>
          </div>
        </div>
      )
  }
}
