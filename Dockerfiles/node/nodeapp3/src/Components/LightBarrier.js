import React from 'react';

export class LightBarrier extends React.Component {
  constructor(props) {
      super(props);
  }

  render() {
      return (
        <div className="lightBarrier">
          <div className="block"></div>
          <div id={this.props.lightBarrierId} className="line"></div>
          <div className="block"></div>
        </div>
      )
  }
}
