"use strict";

import c3 from 'c3';

var chart, data;

module.exports = {
    createChart: function () {
        chart = c3.generate({
            bindto: '#heat-chart',
            data: {
                columns: [
                    ['data1']
                ],
                type: 'gauge'
            },
            gauge: {
                label: {
                    format: function (value, ratio) {
                        return value + " °C";
                    },
                },
                min: 0,
                max: 300
            },
            color: {
                pattern: ['#1e90ff', '#00bfff', '#60B044', '#F6C600', '#FF0000'],
                threshold: {
                    unit: 'value',
                    max: 300,
                    values: [50, 100, 150, 200, 250]
                }
            }
        });
        socket.on('machine', function (msg) {
            if(msg.itemName == "MILLING_HEAT" || msg.itemName == "DRILLING_HEAT"){
              chart.load({
                  columns: [
                      ['data1', Math.floor(msg.value)]
                  ]
              });
            }


        });

    }
}
