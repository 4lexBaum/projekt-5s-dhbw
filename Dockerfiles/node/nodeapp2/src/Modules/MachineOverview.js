import React from 'react';

import { Machine } from '../Components/Machine.js';
import { MachineData } from '../Components/MachineData.js';
import { Header } from '../Components/Header.js';
import { ERPData } from '../Components/ERPData.js';

export class MachineOverview extends React.Component {

        constructor(props) {
            super(props);
        }

        componentDidMount(){
          $('#machineIcon').jrumble();
          var machineIcon = $("#machineIcon");
          var product = $(".product");
          var areaWidth = $(".area").width();
          var move = '+=' + areaWidth + 'px';
          var moveBack = '-=' + (4 * areaWidth) + 'px';
          socket.on("machine", function(msg){
            switch (msg.itemName) {
              case "L1":
                if(msg.value){
                  $("#lb1").css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 1000);
                  $("#area1").removeClass("GOOD BAD");
                  product.animate({marginLeft: move}, 2000);
                } else{
                  product.css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 500);
                  $("#lb1").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 1000);
                  $("#area1").addClass(msg.status);
                }
                break;
              case "L2":
                if(msg.value){
                  $("#lb2").css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 1000);
                  $("#area2").removeClass("GOOD BAD");
                  product.animate({marginLeft: move}, 2000);
                } else{
                  $("#lb2").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 1000);
                  $("#area2").addClass(msg.status);
                }
                break;
              case "L3":
                machineIcon.attr("src","http://www.freeiconspng.com/uploads/mill-icon-16.png");
                machineIcon.width("80%");
                $(".heatTitle").text("Milling Heat");
                $(".speedTitle").text("Milling Speed");
                if(msg.value){
                  $("#lb3").css({opacity: 0, visibility: "visible"}).animate({opacity:1.0}, 1000);
                  $("#area3").removeClass("GOOD BAD");
                  product.animate({marginLeft: move}, 2000);
                } else{
                  $("#lb3").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 1000);
                  $("#area3").addClass(msg.status);
                  $(".machineIconBox").css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 2000);
                  $(".machineValue").css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 2000);
                }
                break;
              case "L4":
                machineIcon.attr("src","https://d30y9cdsu7xlg0.cloudfront.net/png/170362-200.png");
                machineIcon.width("90%");
                $(".heatTitle").text("Drilling Heat");
                $(".speedTitle").text("Drilling Speed");
                if(msg.value){
                  $("#lb4").css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 1000);
                  $("#area4").removeClass("GOOD BAD");
                  product.animate({marginLeft: move}, 2000);
                } else{
                  $("#lb4").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 1000);
                  $(".machineValue").effect("highlight", {color:"rgba(17, 120, 149, 0.9)"}, 2000);
                  $(".machineIconBox").effect("highlight", {color:"rgba(17, 120, 149, 0.9)"}, 2000);
                  $("#area4").addClass(msg.status);
                }
                break;
              case "L5":
                $(".machineIconBox").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 2000);
                $(".machineValue").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 2000);
                if(msg.value){
                  $("#lb5").css({opacity: 0, visibility: "visible"}).animate({opacity: 1.0}, 1000);
                  $("#area5").removeClass("GOOD BAD");
                  product.css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 500);
                  product.animate({marginLeft: moveBack}, 0);
                } else{
                  $("#lb5").css({opacity: 1.0, visibility: "visible"}).animate({opacity: 0}, 1000);
                  $("#area5").addClass(msg.status);
                }
                break;
              case "MILLING":
                if(msg.value){
                  $('#machineIcon').trigger('startRumble');
                } else{
                  $('#machineIcon').trigger('stopRumble');
                }
                break;
              case "DRILLING":
                if(msg.value){
                  $('#machineIcon').trigger('startRumble');
                } else{
                  $('#machineIcon').trigger('stopRumble');
                }
                break;
              default:
            }
          })
        }

        componentWillUnmount(){
        }

        render() {
            return (
              <div>
                <Header></Header>
                <MachineData></MachineData>
                <div className="machineBox">
                  <ERPData></ERPData>
                  <Machine></Machine>
                  <ERPData></ERPData>
                </div>
              </div>

            )
        }
}
