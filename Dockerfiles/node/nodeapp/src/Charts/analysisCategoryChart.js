"use strict";

import c3 from 'c3';

var data;
//var bindTo = "#"+this.props.bindTo;

module.exports = {
    createChart: function (analysisName, socketName, bindTo) {
        var chart = c3.generate({
            bindto: bindTo,
            data: {
                x: 'x',
                columns: [
                    ['x', '4714', '4715', '4716', '4717', '4718', '4719', '4720'],
                    ['correct', 80, 70, 60, 60, 50, 70, 90],
                    ['incorrect', 20, 30, 40, 40, 50, 30, 10]
                ],
                groups: [
                  ['correct', 'incorrect']
                ],
                type: 'bar'
            },
            axis: {
                x: {
                    type: 'category'
                }
            }
        });
        socket.on(socketName, function (msg) {
          var xAxis = ['x'];
          var yAxis = ['correct'];
          var zAxis = ['incorrect'];

          for (var key in msg) {
            if (msg.hasOwnProperty(key)) {
              xAxis.push(key);
              yAxis.push(100-msg[key]);
              zAxis.push(msg[key]);
            }
          }
            chart.load({
                columns: [
                  xAxis,
                  yAxis,
                  zAxis
                ]
            });
        });

    }
}
