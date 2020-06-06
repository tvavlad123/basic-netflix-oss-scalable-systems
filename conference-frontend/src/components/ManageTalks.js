import React, { Component } from 'react';
//import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Layout from './Layout';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';


class ManageTalks extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.location.state.userId,
            email: this.props.location.state.email,
            token: this.props.location.state.token,
            articleId : "",
            talks: [],
            articles: [],
            open: false,
            date : "",
            startTime : "",
            endTime : ""
        }
    }

    handleClose = () => {
        this.setState({ open: false })
    }
    timeConverter = (UNIX_timestamp) => {
        var a = new Date(UNIX_timestamp);
        var months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        var year = a.getFullYear();
        var month = months[a.getMonth()];
        var date = a.getDate();
        var hour = a.getHours();
        var min = a.getMinutes();
        var time = date + ' ' + month + ' ' + year + ', ' + hour + ':' + min;
        return time;
    }

    componentDidMount() {
        axios.all([
            axios.get('http://localhost:8765/api/conference/talks/', {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }),
            axios.get('http://localhost:8765/api/conference/articles/unregistered/', {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            })
        ]).then(axios.spread((talksResponse, articlesResponse) => {
            console.log(talksResponse);
            console.log(articlesResponse);
            this.setState({ talks: talksResponse.data });
            this.setState({ articles: articlesResponse.data });
        }))
    }

    register = (id) => {
        this.setState({ open: true, articleId : id })

    }

    submit = () => {
        //this.setState({ showModal: false });
        var d = new Date(this.state.date + " " + this.state.startTime);
        var dd = new Date(this.state.date + " " + this.state.endTime);

        axios.post("http://localhost:8765/api/conference/talks/", {
            "startTime": d.getTime(),
            "endTime": dd.getTime(),
            "articleId": this.state.articleId,
        }, {
            headers: {
                Authorization: `Bearer ${this.state.token}`
            }
        }).then(response => {
            console.log(response);
            alert("The room " + response.data.name + "is set for the talk");
            axios.get('http://localhost:8765/api/conference/talks/', {
                headers: {
                    Authorization: `Bearer ${this.state.token}`
                }
            }).then(response => {
                this.setState({ talks: response.data });
                axios.get('http://localhost:8765/api/conference/articles/unregistered/', {
                    headers: {
                        Authorization: `Bearer ${this.state.token}`
                    }
                }).then(resp => {

                    this.setState({ articles: resp.data, open: false });
                })

            })

        }).catch(error => {
            alert("something went wrong! Please try again");
        })
    }

    renderArticles = (tableData) => {
        return (
            <table className="table table-striped" aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Domain</th>
                        <th>Description</th>
                        <th>Author</th>
                        <th>Add talk</th>
                    </tr>
                </thead>
                <tbody>
                    {tableData.map(entry =>
                        <tr>
                            <td>{entry.title}</td>
                            <td>{entry.domain}</td>
                            <td>{entry.description}</td>
                            {/* <td>AAA</td> */}
                            <td>{entry.author.firstName + " " + entry.author.lastName}</td>
                            <td>
                                <Button
                                    //fullWidth
                                    variant="contained"
                                    color="primary"
                                    //className={classes.submit}
                                    onClick={() => this.register(entry.id)}>
                                    Add
                                </Button>
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    Dialog = () => {
        return (
            <Dialog open={this.state.open} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Assign to conference</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Enter the conference details below
              </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="date"
                        label="Date"
                        type="text"
                        onChange={ (e) => this.setState({date : e.target.value })}
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="starttime"
                        label="Start Time"
                        type="text"
                        onChange={ (e) => this.setState({startTime : e.target.value })}
                        fullWidth
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="endtime"
                        label="End Time"
                        type="text"
                        onChange={ (e) => this.setState({endTime : e.target.value })}
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.submit} color="primary">
                        Submit
                </Button>
                    <Button onClick={this.handleClose} color="primary">
                        Cancel
                </Button>
                </DialogActions>
            </Dialog>
        )
    }

    renderTalks = (tableData) => {
        return (
            <table className="table table-striped" aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Room Name</th>
                        <th>Capacity</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                    </tr>
                </thead>
                <tbody>
                    {tableData.map(entry =>
                        <tr>
                            <td>{entry.title}</td>
                            <td>{entry.room.name}</td>
                            <td>{entry.room.places}</td>
                            <td>{this.timeConverter(entry.startTime)}</td>
                            <td>{this.timeConverter(entry.endTime)}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    render() {
        return (
            <div>
                <this.Dialog />
                <Layout data={{ "email": this.state.email, "userId": this.state.userId, "token": this.state.token }}>
                    <h2>Here you can assign articles to talks!</h2>
                    These are the available articles:
                    <Grid item xs={8}>
                        {this.renderArticles(this.state.articles)}
                    </Grid>
                    These are the talks registered so far:
                        {this.renderTalks(this.state.talks)}
                </Layout>
            </div>
        )
    }

}
export default ManageTalks;