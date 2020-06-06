// import React from 'react';
// import './App.css';
// import Login from './components/Login';

// function App() {
//   return (
//     <div className="App">
//         <Login />
//     </div>
//   );
// }

// export default App;


import React, { Component } from 'react';
import { Route } from 'react-router';
import Login from './components/Login';
import Signup from './components/Signup';
import Menu from './components/Menu';
import FilterArticles from './components/FilterArticles';
import UploadArticle from './components/UploadArticle';
import ManageTalks from './components/ManageTalks';
import RegisterForATalk from './components/RegisterForATalk';

//import './custom.css';

export default class App extends Component {
    static displayName = App.name;

    render() {
        return (
            <div>
                <Route exact path = "/" component = { Login } />
                <Route path = "/signup" component = { Signup } />
                <Route path = "/menu" component = { Menu } />
                <Route path = "/filter" component = { FilterArticles } />
                <Route path = "/upload" component = {UploadArticle} />
                <Route path = "/manage" component = {ManageTalks} />
                <Route path = "/register" component = {RegisterForATalk} />
            </div>
        )
    }
}