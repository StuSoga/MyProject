/**
 * Created by zero on 15/12/26.
 */
'use strict';

var React = require('react'),

//router
    Router = require('react-router'),
    Link = Router.Link,

    None;

None = React.createClass({
    render: function () {
        return (
            <div>
                <h1>404!</h1>
                <p><Link to='app'>首页</Link></p>
            </div>
        );
    }
});

module.exports = None;