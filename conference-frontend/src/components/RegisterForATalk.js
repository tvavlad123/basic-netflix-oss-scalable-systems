import React, { Component } from 'react';
import axios from 'axios';
import Layout from './Layout';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';


class RegisterforATalk extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.location.state.userId,
            email: this.props.location.state.email,
            token: this.props.location.state.token,
            registeredTalks: [],
            availableTaks: []
        }
    }

    componentDidMount() {
        axios.all([
            axios.get('http://localhost:8765/api/conference/talks/attendee/' + this.state.userId, {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }),
            axios.get('http://localhost:8765/api/conference/talks/available', {
                params: {
                    "userId": this.state.userId
                },
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            })

        ])
            .then(axios.spread((talksResponse, availableResponse) => {

                this.setState({ registeredTalks: talksResponse.data });
                this.setState({ availableTaks: availableResponse.data });
                this.setState({ ok: true });

            }))

    }

    renderTable1 = (tableData) => {
        return (
            <table className="table table-striped" aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Domain</th>
                        <th>Author</th>
                        <th>Register</th>
                    </tr>
                </thead>
                <tbody>
                    {tableData.map(entry =>
                        <tr>
                            <td>{entry.title}</td>
                            <td>{entry.domain}</td>
                            <td>{entry.author.firstName + " " + entry.author.lastName}</td>
                            <td>
                                <Button
                                    //fullWidth
                                    variant="contained"
                                    color="primary"
                                    //className={classes.submit}
                                    onClick={() => this.register(entry.id)}>
                                    Checkin
                                </Button>
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    renderTable2 = (tableData) => {
        return (
            <table className="table table-striped" aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Domain</th>
                        <th>Author</th>
                        <th>Registered</th>
                    </tr>
                </thead>
                <tbody>
                    {tableData.map(entry =>
                        <tr>
                            <td>{entry.title}</td>
                            <td>{entry.domain}</td>
                            <td>{entry.author.firstName + " " + entry.author.lastName}</td>
                            <td>
                                <Button
                                    //fullWidth
                                    variant="contained"
                                    color="secondary"
                                    //className={classes.submit}
                                    onClick={() => this.unregister(entry.id)}>
                                    Unregister
                                </Button>
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    unregister = (val) => {
        axios.post('http://localhost:8765/api/conference/talks/unregister',

            {
                "userId": this.state.userId,
                "talkId": val
            },
            {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }
        ).then(response => {

            axios.get('http://localhost:8765/api/conference/talks/attendee/' + this.state.userId, {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }).then(talksResponse => {

                this.setState({ registeredTalks: talksResponse.data });
                axios.get('http://localhost:8765/api/conference/talks/available', {
                    params: {
                        "userId": this.state.userId
                    },
                    headers: {
                        Authorization: `Bearer ${this.state.token}`
                    }
                }).then(availableResponse => {

                    this.setState({ availableTaks: availableResponse.data });
                    this.setState({ ok: true });
                })
            })
        })
    }

    register = (value) => {
        axios.post('http://localhost:8765/api/conference/talks/register',
            {
                "userId": this.state.userId,
                "talkId": value
            },
            {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }
        ).then(response => {
            axios.get('http://localhost:8765/api/conference/talks/attendee/' + this.state.userId, {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }).then(talksResponse => {
                this.setState({ registeredTalks: talksResponse.data });
                axios.get('http://localhost:8765/api/conference/talks/available', {
                    params: {
                        "userId": this.state.userId
                    },
                    headers: {
                        Authorization: `Bearer ${this.state.token}`
                    }
                }).then(availableResponse => {
                    this.setState({ availableTaks: availableResponse.data });
                    this.setState({ ok: true });
                })
            })
        })
    }

    render() {
        return (
            <div>
                <Layout data={{ "email": this.state.email, "userId": this.state.userId, "token": this.state.token }}>
                    <h2>Here you can register for a talk!</h2>
                    <p>
                        These talks are available:
                    </p>
                    <Grid item xs={6}>
                        {this.renderTable1(this.state.availableTaks)}
                    </Grid>
                    <p>You already registered to these talks:</p>
                    <Grid item xs={6}>
                        {this.renderTable2(this.state.registeredTalks)}
                    </Grid>
                </Layout>
            </div>
        )
    }

}
export default RegisterforATalk;