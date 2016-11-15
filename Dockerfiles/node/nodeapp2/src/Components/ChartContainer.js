import React from 'react';

export class ChartContainer extends React.Component {

        constructor(props) {
            super(props);
        }

        handleClick(){
          var container = this.props.containerId;
          alert("bin geklickt worden: " + container);
        }

        render() {
          return (
            <div onClick={this.handleClick.bind(this)} id={this.props.containerId}></div>
          )
        }
}
