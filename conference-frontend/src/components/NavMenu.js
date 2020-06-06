import React, { Component } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import { withRouter } from 'react-router-dom';
import { withStyles } from "@material-ui/core/styles";

import './NavMenu.css';
const styles = theme => ({
	root: {
		flexGrow: 1,
	},
	menuButton: {
		marginRight: theme.spacing(2),
	},
	title: {
		flexGrow: 1,
	},
});

class NavMenu extends Component {
	//const classes = useStyles();

	redirect = (path) => {
		this.props.history.push({
			pathname: path,
			state : {
				email : this.props.data.email,
				userId : this.props.data.userId,
				token: this.props.data.token
			}
		});
	}

	render() {

		const { classes } = this.props
		return (
			<div className={classes.root}>
				<AppBar position="static" id="Appbar">
					<Toolbar>
						<IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
							<MenuIcon />
						</IconButton>
						<Typography variant="h6" className={classes.title}>
							{this.props.data.email}
						</Typography>
						<Button color="inherit" onClick={() => this.redirect("/menu")} >Home</Button>
						<Button color="inherit" onClick={() => this.redirect("/upload")} >Upload Articles</Button>
						<Button color="inherit" onClick={() => this.redirect("/filter")} >Filter Articles</Button>
						<Button color="inherit" onClick={() => this.redirect("/register")} >Register for a talk</Button>
						<Button color="inherit" onClick={() => this.redirect("/manage")} >Manage Talks</Button>
						<Button color="inherit">Chat</Button>
						<Button color="inherit">Logout</Button>
					</Toolbar>
				</AppBar>
			</div>
		);
	}
}

export default withStyles(styles, { withTheme: true })(withRouter(NavMenu));