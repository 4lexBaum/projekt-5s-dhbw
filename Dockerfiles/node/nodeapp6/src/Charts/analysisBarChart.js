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
                    [analysisName, 10, 8, 11, 9, 6, 7, 15]
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
          var yAxis = [analysisName];

          for (var key in msg) {
            if (msg.hasOwnProperty(key)) {
              xAxis.push(key);
              yAxis.push(msg[key]);
            }
          }
            chart.load({
                columns: [
                  xAxis,
                  yAxis
                ]
            });
        });

    }
}
