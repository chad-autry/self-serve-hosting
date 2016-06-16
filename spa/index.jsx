var React = require('react');
var ReactDOM = require('react-dom');


var App = React.createClass({
  render: function() {
    if (!!window.nickname ) {
    return (
      <p>Hello, {window.nickname}!  You can <a href={window.logoutURL}>sign out</a>.</p>
    );
    } else {
        return (
       <p>Please <a href={window.loginURL}>sign in</a>.</p>);
    }
  }
});

var NavBar = React.createClass({
  getInitialState: function() {
    return {
      menuCollapsed: true
    };
  },
  menuClicked: function() {
    this.setState({
      menuCollapsed: !this.state.menuCollapsed
    });
  },
  render: function() {
    return (
<div className="container">
  <div className="navbar navbar-default">
	<div className="navbar-header" onClick={this.menuClicked}>
	  <div className="navbar-toggle">
		<span className="sr-only">Toggle navigation</span>
		<i className={this.state.menuCollapsed ? 'fa fa-chevron-right':'fa fa-chevron-down'}></i>
	  </div>
	  <div className="navbar-brand">
		<i className="fa fa-cube"></i> Self Serve Hosting
	  </div>
	</div>
	<div className={this.state.menuCollapsed ? 'navbar-collapse hidden-xs' : 'navbar-collapse'}>
	  <ul className="nav navbar-nav">
		<li className="active"> {/*Hard Code Activity for now until introducing state*/}
		  <a >
			<i className="fa fa-home"></i> Home
		  </a>
		</li>
		<li>
		  <a href="https://github.com/chad-autry/self-serve-hosting">
			<i className="fa fa-github-alt"></i> Github
		  </a>
		</li>
		<li>
		  <a href="https://github.com/chad-autry/self-serve-hosting/issues">
			<i className="fa fa-comments"></i> Support
		  </a>
		</li>
	  </ul>
	  <ul className="nav navbar-nav navbar-right">
		<li>
		  <a href={this.props.isAuthenticated ? this.props.logoutUrl : this.props.logonUrl}>
			<i className="fa fa-user"></i> {this.props.isAuthenticated ? this.props.userName + ' ': "Logon "}
		  </a>
		</li>
	  </ul>
	</div>
  </div>
</div>
    );
  }
});


ReactDOM.render(
  <NavBar isAuthenticated={!!window.logoutURL}
      logoutUrl = {window.logoutURL}
      logonUrl = {window.loginURL}
      userName = {window.nickname}/>,
  document.getElementById('app')
);