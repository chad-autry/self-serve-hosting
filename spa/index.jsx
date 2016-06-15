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
	<div className="navbar-header" onclick={this.menuClicked}>
	  <div className="navbar-toggle">
		<span className="sr-only">Toggle navigation</span>
		<i className={this.state.menuCollapsed ? 'fa-chevron-right':'fa-chevron-down'} className='fa'></i>
	  </div>
	  <div className="navbar-brand">
		Self Serve Hosting
		<small>
		  <a className="navbar-link" href="https://github.com/chad-autry/self-serve-hosting/tree/master">
			 master
		  </a>
		</small>
	  </div>
	</div>
	<div className="navbar-collapse">
	  <ul className="nav navbar-nav">
		<li >
		  <a >
			<i className="fa fa-home"></i>
			Home
		  </a>
		</li>
		<li>
		  <a>
			<i className="fa fa-download"></i>
			Lazy Load Demo
		  </a>
		</li>
		<li>
		  <a href="https://github.com/chad-autry/self-serve-hosting">
			<i className="fa fa-github-alt"></i>
			Github
		  </a>
		</li>
		<li>
		  <a href="https://github.com/chad-autry/self-serve-hosting/issues">
			<i className="fa fa-comments"></i>
			Support
		  </a>
		</li>
	  </ul>
	  <ul className="nav navbar-nav navbar-right">
		<li >
		  <a>
			<i className="fa fa-user"></i>
			Test
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
  <NavBar/>,
  document.getElementById('app')
);