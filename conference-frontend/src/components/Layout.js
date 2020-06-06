import React, { Component } from 'react';
import NavMenu from './NavMenu';
import { Container } from 'reactstrap';


export default class Layout extends Component {
    render(){
        return (
            <div>
                <NavMenu data = {this.props.data} />
                <Container>
                    {this.props.children}
                </Container>
            </div>
        )
    }
}