/**
 * Created by zero on 15/12/26.
 */
var App = require('./services/app');
var Book=require('./services/book');

var actions = {
    app: App.actions,
    book:Book.actions
};

module.exports = actions;