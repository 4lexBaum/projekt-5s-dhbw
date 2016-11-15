import React from 'react';


export class Product extends React.Component {
  constructor(props) {
      super(props);
  }

  static get defaultProps() {
      return {
        title: 'No title'
      }
  }

  render() {
      return (
        <div style={{border: '1px solid red', padding: '20px', margin: '20px 0 20px 0'}}>

          <h2>{this.props.nr +1}. {this.props.title}</h2>
          <p>Price: {this.props.price}</p>

        </div>
      )
  }
}
