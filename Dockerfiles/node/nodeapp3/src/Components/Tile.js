import React from 'react';

export class Tile extends React.Component {

        constructor(props) {
            super(props);
            this.state = {value: this.props.value, status: this.props.status};
        }

        componentDidMount(){
          var that = this;
          var valueTag = "#" + this.props.containerId + "value";
          var statusTag = "#" + this.props.containerId + "status";
          var colorchanged = false;
          if (typeof this.props.borders !== 'undefined' && typeof this.props.colors !== 'undefined') {
            var bordersarray = this.props.borders.split(',');
            var colorsarray = this.props.colors.split(',');
          }
          socket.on(this.props.streamId, function (msg) {
            if (typeof that.props.borders !== 'undefined' && typeof that.props.colors !== 'undefined') {
              if (colorsarray.length - bordersarray.length == 1) {
                colorchanged = false;
                for (var i = 0; i < bordersarray.length; i++) {
                  if (msg <= bordersarray[i]) {
                    $(valueTag).css("color",colorsarray[i]);
                    colorchanged = true;
                    break;
                  }
                }
                if (colorchanged === false) {
                  $(valueTag).css("color",colorsarray[colorsarray.length-1]);
                }
              } else {
                console.log("Borders and colors not compatible for " + that.props.containerId);
              }
            }
            $(statusTag).css("color","#60B044");
            that.setState({value: msg, status: "running"});
            socket.on('disconnect', function(){
              that.setState({status: "stopped"});
              $(statusTag).css("color","#FF0000");
            });
          });
        }

        handleClick(){
          window.location.href = '/#/test';
        }

        render() {
          return (
            <div className="tile" onClick={this.handleClick}>
              <img className="icon" src={this.props.icon}></img>
              <p className="value" id={this.props.containerId + "value"}>{this.state.value}</p>
              <p className="unit">{this.props.unit}</p>
              <p className="title">{this.props.title}</p>
              <p className="status" id={this.props.containerId + "status"}>{this.state.status}</p>
            </div>
          )
        }
}
