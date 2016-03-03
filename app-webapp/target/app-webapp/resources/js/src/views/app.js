/**
 * Created by zero on 15/12/26.
 */
/*
 * Copyright (c) 2015. Xiamen Aupad Network Technology co,ltd.
 */

var React = require('react'),
    Fluxxor = require('fluxxor'),

//router
    ReactRouter = require('react-router'),
    RouteHandler = ReactRouter.RouteHandler,
    App;

App = React.createClass({

    mixins: [ReactRouter.state, ReactRouter.Navigation, Fluxxor.FluxMixin(React), Fluxxor.StoreWatchMixin('AppStore')],

    getStateFromFlux: function () {
        this.appStore = this.getFlux().store('AppStore');
        return {
            authFailureFlag: this.appStore.isAuthFailure()
        }
    },

    componentDidUpdate: function () {
        if (this.state.authFailureFlag) {
            var currentPath = this.getcurrentPath();
            this.transitionTo('/passport/login', {}, {redirectUri: currentPath});
        }

    },

    render: function () {
        return (
            <div>
                <RouteHandler {...this.props}/>

            </div>
        )
    }
});

module.exports = App;