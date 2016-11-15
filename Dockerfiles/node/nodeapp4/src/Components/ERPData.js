import React from 'react';

export class ERPData extends React.Component {
  constructor(props) {
      super(props);
      this.state = {customerNumber: 5487, materialNumber: 5475, orderNumber: "454df5d8-5875-564s-df55-57df888e5fs5", timeStamp: "2016-11-10T11:45:02.784Z"};
  }

  componentDidMount(){
    var that = this;
    socket.on("erp", function(msg){
      that.setState({customerNumber: msg.customerNumber, materialNumber: msg.materialNumber, orderNumber: msg.orderNumber, timeStamp: msg.timeStamp});
    })
  }

  render() {
      return (
        <div className="erpContainer">
          <p className="erptitle">ERP Data</p>
          <div className="erpcontent">
            <p className="erpcontenttitle">Customer Number</p>
            <p className="erpdata">{this.state.customerNumber}</p>
          </div>
          <div className="erpcontent">
            <p className="erpcontenttitle">Material Number</p>
            <p className="erpdata">{this.state.materialNumber}</p>
          </div>
          <div className="erpcontent">
            <p className="erpcontenttitle">Order Number</p>
            <p className="erpdata">{this.state.orderNumber}</p>
          </div>
          <div className="erpcontent">
            <p className="erpcontenttitle">Time Stamp</p>
            <p className="erpdata">{this.state.timeStamp}</p>
          </div>
        </div>
      )
  }
}
