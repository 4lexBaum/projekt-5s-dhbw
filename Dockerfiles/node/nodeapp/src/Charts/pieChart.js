"use strict";

import c3 from 'c3';

var chart, data;
var cnt = 1;
var length = 0;

module.exports = {
    createChart: function () {
        chart = c3.generate({
            bindto: '#pie-chart',
            data: {
                columns: [
                    ['W1', 30],
                    ['W2', 120],
                    ['W3', 30],
                    ['W4', 120]
                ],
                type: 'pie'
            }
        });
        socket.on('pie', function (msg) {
            chart.load({
                columns: [
                    ['W1', msg.w1],
                    ['W2', msg.w2],
                    ['W3', msg.w3],
                    ['W4', msg.w4],
                ]
            });
        });

    }
}
