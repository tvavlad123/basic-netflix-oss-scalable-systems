import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { withStyles } from "@material-ui/core/styles";
import Container from '@material-ui/core/Container';
import { withRouter} from 'react-router-dom';
import axios from 'axios';

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


class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email : "",
            password: "",
            token: "",
            received_roles : null,
            userId: 0
        }
    }


    redirect = () => {
        this.props.history.push({
            pathname: '/menu',
            state: {
                email: this.state.email,
                token: this.state.token,
                roles: this.state.received_roles,
                userId: this.state.userId
            }
        });
    }

    sendData = () => {

        var apiBaseUrl = "http://localhost:8765/api/auth/login";
        var payload = {
            "email": this.state.email,
            "password": this.state.password
        };
        //console.log(payload);

        axios.post(apiBaseUrl, payload).then((response) => {
            if (response.status === 200) {
  
                //alert("Success!!")
                var received_roles = response.data.roles.map(x => x.roleName);
                this.setState( {received_roles: received_roles});
                this.setState({token: response.data.accessToken });
                this.setState({userId: response.data.userId});
                //console.log(response.data.accessToken);
                this.redirect();

            }
            else {
                alert("Incorrect credentials! Try again");
            }
        });
    }

    render() {
        //const classes = useStyles();
        const { classes } = this.props;
        return (          
            <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>
                <form className={classes.form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        //required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        onChange={ (e) => this.setState({email : e.target.value })}
                        autoFocus
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        //required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                        onChange={ (e) => this.setState({password : e.target.value })}
                    />
                    <Button
                        
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={() => this.sendData()}
                    >
                        Sign In
                    </Button>
                    <Grid container>
                        <Grid item>
                            <Link href="/signup" variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>
        </Container>
        );
    }
};
export default withStyles(styles, { withTheme: true })(withRouter(Login));
