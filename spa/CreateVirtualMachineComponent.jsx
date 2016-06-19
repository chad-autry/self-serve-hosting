var React = require('react');
/**
 * React component to create a Virtual Machine
 * Instance Types selector
 * Instance Name input with validation
 * Disk Size input with validation
 * Region
 * Create button
 */
module.exports = React.createClass({
    getInitialState: function() {
        return {type: "n1-standard-4", zone:"us-central1-a", disk:20};
    },
    onDiskChange: function(event) {
        this.setState({disk:event.target.value});
    },
    onTypeChange: function(event) {
        this.setState({type:event.target.value});
    },
    onZoneChange: function(event) {
        this.setState({zone:event.target.value});
    },
    incrementDisk: function(event) {
        if (this.state.disk < 100) {
            this.setState({disk:this.state.disk + 1});
        }
    },
    decrementDisk: function(event) {
        if (this.state.disk > 5) {
            this.setState({disk:this.state.disk - 1});
        }
    },
    render: function() {
        var isValid = false;
        
        return (
            <div className="panel panel-default">
                <div className="panel-heading">Create Server</div>
                    <div className="panel-body">
                        <div className="row">
                            <div className="col-md-3">
                                <div className="input-group">
                                    <span className="input-group-addon" id="basic-addon1">Name</span>
                                    <input type="text" className="form-control"/>
                                </div>
                            </div>
                            <div className="col-md-3">
                                <div className="input-group">
                                    <a href="https://cloud.google.com/compute/docs/machine-types#standard_machine_types" className="input-group-addon" id="basic-addon1">Type</a>
                                    <select className="form-control" onChange={this.onTypeChange} value={this.state.type}>
                                        { /*I am having trouble dynamically providing options, hard coded in JSX*/ }
                                        <option value="n1-standard-1">n1-standard-1</option>
                                        <option value="n1-standard-2">n1-standard-2</option>
                                        <option value="n1-standard-4">n1-standard-4</option>
                                        <option value="n1-standard-8">n1-standard-8</option>
                                    </select>
                                </div>
                            </div>
                            <div className="col-md-3">
                                <div className="input-group">
                                    <a href="https://cloud.google.com/compute/docs/regions-zones/regions-zones#available" className="input-group-addon" id="basic-addon1">Region</a>
                                    <select className="form-control" onChange={this.onZoneChange} value={this.state.zone}>
                                        <option value="us-east1-b">us-east1-b</option>
                                        <option value="us-central1-a">us-central1-a</option>
                                        <option value="europe-west1-b">europe-west1-b</option>
                                        <option value="asia-east1-a">asia-east1-a</option>
                                    </select>
                                </div>
                            </div>
                            <div className="col-md-3">
                                <div className="input-group">
                                    <span className="input-group-addon" id="basic-addon1">Disk</span>
                                    <a className={this.state.disk > 5 ? "btn btn-default input-group-addon" : "disabled btn btn-default input-group-addon"} onClick={this.decrementDisk}><i className="fa fa-minus"></i></a>
                                    <input type="range" className="form-control" min="5" max="100" step="1" value={this.state.disk} onChange={this.onDiskChange}/>
                                    <a className={this.state.disk < 100 ? "btn btn-default input-group-addon" : "disabled btn btn-default input-group-addon"} onClick={this.incrementDisk}><i className="fa fa-plus"></i></a>
                                    <span className="input-group-addon" id="basic-addon3">{this.state.disk} GB</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="panel-footer">
                        <button type="button" className={isValid ? "btn btn-default" : "btn btn-default disabled"}>Create</button>
                    </div>
                </div>
            );
        }
    }
);