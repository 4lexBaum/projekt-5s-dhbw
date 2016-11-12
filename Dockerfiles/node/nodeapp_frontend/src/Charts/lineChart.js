"use strict";

import c3 from 'c3';


module.exports = {
    createChart: function () {
        var chart, data;
        var cnt = 0;
        var length = 0;
        chart = c3.generate({
            bindto: '#test-chart',
            data: {
                x: 'x',
                columns: [
                    ['x'],
                    ['drill temperature']
                ],
                type: 'spline'
            },
            axis: {
                y: {
                    padding: {
                        top: 0,
                        bottom: 0
                    },
                    max: 360,
                    min: 90
                },
                x: {
                    type: 'timeseries',
                    tick: {
                        culling: {
                            //max: 0
                        },
                        format: '%H:%M:%S'
                    }
                }
            },
            transition: {
                duration: 0
            },
            grid: {
                y: {
                    show: true,
                    lines: [{
                        value: 200,
                        class: 'minmax'
                    }, {
                        value: 250,
                        text: 'ideal °C range',
                        class: 'minmax'
                    }]
                }
            }
        });
        socket.on('line', function (msg) {
            cnt++;
            if (cnt > 20) {
                length = 1;
            }

            chart.flow({
                columns: [
                    ['x', new Date()],
                    ['drill temperature', msg]
                ],
                length: length,
                duration: 500
            });
        });

    }
}
