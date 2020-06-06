import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
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
	},
	avatar: {
		margin: theme.spacing(1),
		backgroundColor: theme.palette.secondary.main,
	},
	form: {
		width: '100%', // Fix IE 11 issue.
		marginTop: theme.spacing(3),
	},
	submit: {
		margin: theme.spacing(3, 0, 2),
	},
});

class Signup extends Component {

	constructor(props) {
		super(props);
		this.state = {
			firstName: "",
			lastName: "",
			username: "",
			password: "",
			affiliation: "",
			isParticipant: false,
			isSpeaker: false
		}
	}

	sendData = () => {

		var apiBaseUrl = "http://localhost:8765/api/auth/signup";
		let roles = [];
		if (this.state.isParticipant) {
			roles.push({ "roleName": "ROLE_PARTICIPANT" })
		}
		if (this.state.isSpeaker) {
			roles.push({ "roleName": "ROLE_SPEAKER" })
		}
		var payload = {
			"firstName": this.state.firstName,
			"lastName": this.state.lastName,
			"email": this.state.email,
			"affiliation": this.state.affiliation,
			"password": this.state.password,
			"roles": roles
		};
		console.log(payload);

		axios.post(apiBaseUrl, payload).then((response) => {
			if (response.status === 201) {
				//this.setState({ token: response.data.accessToken });
				//alert(this.state.token);
				//this.redirect();
				alert("success");
			}
			if (response.status === 401) {
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
					{/* <Avatar className={classes.avatar}>
						<LockOutlinedIcon />
					</Avatar> */}
					<Typography component="h1" variant="h5">
						Sign up
        			</Typography>
					<form className={classes.form} noValidate>
						<Grid container spacing={2}>
							<Grid item xs={12} sm={6}>
								<TextField
									autoComplete="fname"
									name="firstName"
									variant="outlined"
									//required
									fullWidth
									id="firstName"
									label="First Name"
									autoFocus
									onChange={(e) => this.setState({ firstName: e.target.value })}
								/>
							</Grid>
							<Grid item xs={12} sm={6}>
								<TextField
									variant="outlined"
									//required
									fullWidth
									id="lastName"
									label="Last Name"
									name="lastName"
									autoComplete="lname"
									onChange={(e) => this.setState({ lastName: e.target.value })}
								/>
							</Grid>
							<Grid item xs={12}>
								<TextField
									variant="outlined"
									//required
									fullWidth
									id="email"
									label="Email Address"
									name="email"
									autoComplete="email"
									onChange={(e) => this.setState({ email: e.target.value })}
								/>
							</Grid>
							<Grid item xs={12}>
								<TextField
									variant="outlined"
									//required
									fullWidth
									id="email"
									label="What's your affiliation?"
									name="affiliation"
									//autoComplete="email"
									onChange={(e) => this.setState({ affiliation: e.target.value })}
								/>
							</Grid>
							<Grid item xs={12}>
								<TextField
									variant="outlined"
									//required
									fullWidth
									name="password"
									label="Password"
									type="password"
									id="password"
									autoComplete="current-password"
									onChange={(e) => this.setState({ password: e.target.value })}
								/>
							</Grid>
							<Grid item xs={12} sm={6}>
								<FormControlLabel
									control={<Checkbox value="allowExtraEmails" color="primary" onChange = {(e) => this.setState({isSpeaker : e.target.checked})}/>}
									label="I am a speaker."
								/>
							</Grid>
							<Grid item xs={12} sm={6}>
								<FormControlLabel
									control={<Checkbox value="allowExtraEmails" color="primary" onChange = {(e) => this.setState({isParticipant : e.target.checked})}/>}
									label="I am a participant."
								/>
							</Grid>
						</Grid>
						<Button
							//type="submit"
							fullWidth
							variant="contained"
							color="primary"
							className={classes.submit}
							onClick={() => this.sendData()}
						>
							Sign Up
         				</Button>
						<Grid container justify="flex-end">
							<Grid item>
								<Link href="/" variant="body2">
									Already have an account? Sign in
              					</Link>
							</Grid>
						</Grid>
					</form>
				</div>
			</Container>
		);
	}
};
export default withStyles(styles, { withTheme: true })(withRouter(Signup));