var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'target');
var APP_DIR = path.resolve(__dirname, 'spa');

var config = {
  entry: APP_DIR + '/tests.js',
  output: {
    path: BUILD_DIR,
    filename: 'bundle.spec.js'
  },
  module : {
    loaders : [
      {
        test : /\.js?/,
        include : APP_DIR,
        loader : 'babel'
      }
    ]
  }
};

module.exports = config;