import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, hashHistory } from 'react-router';
import { Dashboard } from './src/Modules/Dashboard';
import { Customer } from './src/Modules/Customer';
import { Material } from './src/Modules/Material';
import { MachineStat } from './src/Modules/MachineStat';
import { MachineOverview } from './src/Modules/MachineOverview';

ReactDOM.render((
  <Router history={hashHistory}>
    <Route path="/" component={Dashboard}/>
    <Route path="/customer" component={Customer}/>
    <Route path="/material" component={Material}/>
    <Route path="/machine" component={MachineOverview}/>
    <Route path="/machineStat" component={MachineStat}/>
  </Router>
), document.getElementById('main'));
