var Footer = require('./Footer.jsx');
var NavBar = require('./NavBar.jsx');
var React = require('react');


module.exports = React.createClass({
    render: function() {
        return (
            <div class="container-fluid">
                <NavBar isAuthenticated={this.props.isAuthenticated}
                    logoutUrl = {this.props.logoutUrl}
                    logonUrl = {this.props.logonUrl}
                    userName = {this.props.userName}/>
                <Footer/>
            </div>
        );
    }
});