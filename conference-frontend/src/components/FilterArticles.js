import React, { Component } from 'react';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
//import Select from 'react-select';
import InputLabel from '@material-ui/core/InputLabel';
import Button from '@material-ui/core/Button';
//import Select from 'react-select';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Layout from './Layout';
import Grid from '@material-ui/core/Grid';
import MenuItem from '@material-ui/core/MenuItem';
import { withStyles } from "@material-ui/core/styles";
import TextField from '@material-ui/core/TextField';


const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing.unit * 2,
    },
});

class FilterArticles extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.location.state.userId,
            email: this.props.location.state.email,
            token: this.props.location.state.token,
            articles: [],
            criteria : "",
            search : ""
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8765/api/conference/articles/', {
            headers: {
                Authorization: `Bearer ${this.state.token}`
            }
        })
            .then(res => {
                if (res.status === 200) {
                    this.setState({ articles: res.data });
                    this.setState({ ok: true });
                }
            }
            ).catch(eror => {
                alert("Something went wrong! Try again!");
            })
        //this.populateTable();
    }

    renderTable = (tableData) => {
        return (
            <table className="table table-striped" aria-labelledby="tabelLabel">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Domain</th>
                        <th>Author</th>
                    </tr>
                </thead>
                <tbody>
                    {tableData.map(entry =>
                        <tr>
                            <td>{entry.title}</td>
                            <td>{entry.domain}</td>
                            <td>{entry.author.firstName + " " + entry.author.lastName}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        );
    }

    handleFilter = () => {
        console.log(this.state.criteria);
        console.log(this.state.search);
        if (this.state.criteria === "domain") {
            axios.get('http://localhost:8765/api/conference/articles/domain/' + this.state.search,
              {
                headers: {
                  Authorization: `Bearer ${this.state.token}`
                }
              })
              .then(res => {
         
                this.setState({ articles: res.data });
                this.setState({ok:true});
              })
          }
    }
    

    render() {
        const { classes } = this.props;
        return (
            <div>
                <Layout data={{ "email": this.state.email, "userId": this.state.userId, "token": this.state.token }}>
                    <h2>Here you can filter articles!</h2>
                    <FormControl className={classes.formControl} style={{minWidth: 250, marginBottom : 25}}>
                        <InputLabel htmlFor="age-simple">Select filter criteria:</InputLabel>
                        <Select
                            style = {{marginBottom : 10}}
                            fullWidth
                            //value={this.state.criteria}
                            onChange={(e) => this.setState({criteria : e.target.value })}
                            // inputProps={{
                            //     name: 'age',
                            //     id: 'age-simple',
                            // }}
                            >
                            <MenuItem value={"author"}>Author</MenuItem>
                            <MenuItem value={"domain"}>Domain</MenuItem>
                            <MenuItem value={"title"}>Title</MenuItem>
                        </Select>
                        <TextField
                        variant="outlined"
                        margin="normal"
                        //required
                        fullWidth
                        id="search"
                        label="Search term"
                        name="search"
                        autoComplete="search"
                        onChange={ (e) => this.setState({search : e.target.value })}
                        autoFocus
                    />
                    <Button
                        //fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={() => this.handleFilter()}
                    >
                        Filter
                    </Button>
                    </FormControl>
                    <Grid item xs={6}>
                        {this.renderTable(this.state.articles)}
                    </Grid>
                </Layout>
            </div>
        );
    }
}
export default withStyles(styles, { withTheme: true })(withRouter(FilterArticles));   