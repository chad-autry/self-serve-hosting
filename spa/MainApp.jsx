var CreateVM = require('./CreateVirtualMachineComponent.jsx');
var Footer = require('./Footer.jsx');
var NavBar = require('./NavBar.jsx');
var React = require('react');

//The fetch polyfill
//require('whatwg-fetch');


module.exports = React.createClass({
    

    render: function() {
        var virtualMachineTypes = [];
        virtualMachineTypes.push({instanceName:"n1-standard-1", instanceDescription:"1 virtual CPU and 3.75 GB of memory"});
        var createVm = '';
        if (this.props.isAuthenticated && this.props.isAdmin) {
            createVm = <CreateVM virtualMachineTypes = {virtualMachineTypes}/>;
        }
        return (
            <div class="container-fluid">
                <NavBar isAuthenticated={this.props.isAuthenticated}
                    logoutUrl = {this.props.logoutUrl}
                    logonUrl = {this.props.logonUrl}
                    userName = {this.props.userName}/>
                {createVm}
                <Footer/>
            </div>
        );
    }
});