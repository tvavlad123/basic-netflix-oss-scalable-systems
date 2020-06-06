// import React from 'react';
// import ReactDOM from 'react-dom';
// import './index.css';
// import App from './App';
// // import Login from './components/Login';
// import { Route, BrowserRouter as Router } from 'react-router-dom';

// const routing = (
//   <Router>
//       <div>
//           <Route exact path="/" component = {App} />
//           {/* <Route path = "/signup" component = {Signup} />  */}
//       </div>
//   </Router>
// );

// ReactDOM.render(routing, document.getElementById('root'));

import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

import { BrowserRouter } from 'react-router-dom';

// const baseUrl = document.getElementsByTagName('base')[0].getAttribute('href');
const rootElement = document.getElementById('root');

ReactDOM.render(
	<BrowserRouter>
		<App />
	</BrowserRouter>, rootElement
);