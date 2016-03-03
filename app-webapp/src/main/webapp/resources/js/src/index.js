/**
 * Created by zero on 15/12/26.
 */
var React = require('react'),
    ReactRouter = require('react-router'),

    Fluxxor = require('fluxxor'),

    routes = require('./routes'),
    actions = require('./actions'),
    stores = require('./stores');


var flux = new Fluxxor.Flux(stores, actions);

flux.on('dispatch', function (type, payload) {
    if (console && console.log) {
        console.log('[Dispatch]', type, payload);
    }
});


ReactRouter.run(routes, function (Handler) {
    React.render(<Handler flux={flux}/>, document.getElementById('app'));
});