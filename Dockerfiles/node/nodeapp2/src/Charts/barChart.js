"use strict";

import c3 from 'c3';

module.exports = {
    createChart: function () {
        var chart, data;
        var cnt = 0;
        var length = 0;
        chart = c3.generate({
            bindto: '#bar-chart',
            data: {
                x: 'x',
                columns: [
                    ['x'],
                    ['Quality']
                ],
                type: 'bar'
            },
            axis: {
                y: {
                    padding: {
                        top: 0,
                        bottom: 0
                    },
                    max: 100,
                    min: 0
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
                        value: 70,
                        class: 'minmax',
                        text: 'minimum requirement'
                    }]
                }
            }
        });
        socket.on('bar', function (msg) {
            cnt++;
            if (cnt > 30) {
                length = 1;
            }

            chart.flow({
                columns: [
                    ['x', new Date()],
                    ['Quality', msg]
                ],
                length: length,
                duration: 500
            });
        });

    }
}
