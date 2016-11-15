"use strict";

import c3 from 'c3';

var chart, data;

module.exports = {
    createChart: function () {
        chart = c3.generate({
            bindto: '#gauge-chart',
            data: {
                columns: [
                    ['data1', 50]
                ],
                type: 'gauge'
            },
            gauge: {
                label: {
                    format: function (value, ratio) {
                        return value + "°C";
                    },
                },
                min: 100,
                max: 400
            },
            color: {
                pattern: ['#1e90ff', '#00bfff', '#60B044', '#F6C600', '#FF0000'],
                threshold: {
                    unit: 'value',
                    max: 400,
                    values: [150, 200, 250, 300, 350]
                }
            }
        });
        socket.on('gauge', function (msg) {
            chart.load({
                columns: [
                    ['data1', msg]
                ]
            });
        });

    }
}
