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
ReactDOM.render(
  <App/>,
  document.getElementById('app')
);