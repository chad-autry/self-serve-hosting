var React = require('react');
var ReactDOM = require('react-dom');


var App = React.createClass({
  render: function() {
    return (
      <p> Hello React!</p>
    );
  }
});
ReactDOM.render(
  <App/>,
  document.getElementById('app')
);