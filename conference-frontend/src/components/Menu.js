import React, { Component } from 'react';
import Layout from './Layout';

export default class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.location.state.userId,
            email: this.props.location.state.email,
            token: this.props.location.state.token
            // roles: this.props.location.state.roles,
            // speaker: false,
            // participant: false,
            // organizer: false
        }
        
        
    }
    render(){
        return (
            <div>
                <Layout data ={this.state}>
                <h2>Hello! </h2>
                {/* <h2>{this.state.token}</h2> */}
                <p>Welcome to this year's edition of the conference!
                Using the buttons above you can:
                </p>
                <ul>
                    <li>Access the chatroom</li>
                    <li>Upload or search for articles</li>
                </ul>
                </Layout>
            </div>
        );
    }
}