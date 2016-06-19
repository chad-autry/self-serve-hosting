var MainApp = require('./MainApp.jsx');
var React = require('react'); //ReactDOM needs React in scope
var ReactDOM = require('react-dom');

ReactDOM.render(
  <MainApp isAuthenticated={!!window.logoutURL}
      logoutUrl = {window.logoutURL}
      logonUrl = {window.loginURL}
      userName = {window.nickname}
      isAdmin = {window.isAdmin}/>,
  document.getElementById('app')
);