/**
 * Created by zero on 15/12/26.
 */
var React = require('react'),
    Router = require("react-router"),
    Route = Router.Route,
    DefaultRoute = Router.DefaultRoute,
    NotFoundRoute = Router.NotFoundRoute;

var App = require('./views/app'),
    None= require('./views/none'),
    Home= require('./views/home');

var routes = (
    <Route path="/" handler={App}>

        <DefaultRoute handler={Home}/>
        <NotFoundRoute handler={None}/>
    </Route>
);

module.exports = routes;