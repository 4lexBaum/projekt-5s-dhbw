"use strict";

import c3 from 'c3';

var chart, data;

module.exports = {
    createChart: function () {
        chart = c3.generate({
            bindto: '#speed-chart',
            data: {
                columns: [
                    ['data1']
                ],
                type: 'gauge'
            },
            gauge: {
                label: {
                    format: function (value) {
                        return value + "T RPM";
                    },
                },
                min: 0,
                max: 20
            },
            color: {
                pattern: ['#1e90ff', '#00bfff', '#60B044', '#F6C600', '#FF0000'],
                threshold: {
                    unit: 'value',
                    max: 20,
                    values: [0, 5, 10, 15, 17.5]
                }
            }
        });
        socket.on('machine', function (msg) {
            if(msg.itemName == "MILLING_SPEED" || msg.itemName == "DRILLING_SPEED"){
              chart.load({
                  columns: [
                      ['data1', Math.floor(msg.value / 1000)]
                  ]
              });
            }

        });

    }
}
