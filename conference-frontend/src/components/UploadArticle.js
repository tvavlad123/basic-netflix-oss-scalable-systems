import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Layout from './Layout';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: "white"
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
});

class UploadArticle extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.location.state.userId,
            email: this.props.location.state.email,
            token: this.props.location.state.token,
            title : "",
            link: "",
            description: "",
            domain : ""
            //roles: this.props.location.state.roles,
        }
    }

    upload = () => {

        var payload = {
            "title" : this.state.title,
            "author" : {"id" : this.state.userId},
            "domain" : this.state.domain,
            "description": this.state.description,
            "link": this.state.link
        }
        console.log(payload);
        //console.log(this.state.token);
        axios.post('http://localhost:8765/api/conference/articles/',
            payload,{
            headers:{
                Authorization: `Bearer ${this.state.token}` }
            }
        ).then((response)=>{
            if(response.status === 200)
                alert('Added with success')
            else
                alert('A problem occured, please try again!');
        });
    }

    render(){
        const { classes } = this.props;
        return(
            <div>
                <Layout data ={{"email" : this.state.email, "userId" : this.state.userId, "token" : this.state.token}}>
                <h2>Here you can upload a new article!</h2>
                {/* <Container maxWidth="xs"> */}
                <form className={classes.form} noValidate>
                <Grid item xs={4}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        //required
                        fullWidth
                        id="title"
                        label="Title"
                        name="title"
                        onChange={ (e) => this.setState({title : e.target.value })}
                        autoFocus
                    />
                </Grid>
                <Grid item xs={4}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        fullWidth
                        name="link"
                        label="Link"
                        id="link"
                        onChange={ (e) => this.setState({link : e.target.value })}
                    />
                </Grid>
                <Grid item xs={4}>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        //required
                        fullWidth
                        id="domain"
                        label="Domain"
                        name="domain"
                        onChange={ (e) => this.setState({domain : e.target.value })}
                        autoFocus
                    />
                </Grid>
                <Grid item xs={4}>
                <TextField
                    variant="outlined"
                    margin="normal"
                    //required
                    fullWidth
                    name="description"
                    label="Description"
                    type="password"
                    id="password"
                    multiline
                    rows = {5}
                    autoComplete="current-password"
                    onChange={ (e) => this.setState({description : e.target.value })}
                />
                </Grid>
                    <Button
                        //fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={() => this.upload()}
                    >
                        Submit!
                    </Button>
                </form>
                {/* </Container> */}
                </Layout>
            </div>
        )
    }

}
export default withStyles(styles, { withTheme: true })(withRouter(UploadArticle));    