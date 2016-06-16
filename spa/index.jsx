var React = require('react');
var ReactDOM = require('react-dom');

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

var Footer = React.createClass({
  render: function() {
    return (
  <div className="container">
	<div className="footer-inner">
	  <p>
		<i className="fa fa-copyright"></i> 2016 <a href="http://chad-autry.github.io/">Chad Autry</a>.
	  </p>
	</div>
  </div>
    );
  }
});

var App = React.createClass({
  render: function() {
    return (
        <div>
        <NavBar isAuthenticated={this.props.isAuthenticated}
      logoutUrl = {this.props.logoutUrl}
      logonUrl = {this.props.logonUrl}
      userName = {this.props.userName}/>
      <Footer/>
      </div>
    );
  }
});

ReactDOM.render(
  <App isAuthenticated={!!window.logoutURL}
      logoutUrl = {window.logoutURL}
      logonUrl = {window.loginURL}
      userName = {window.nickname}/>,
  document.getElementById('app')
);