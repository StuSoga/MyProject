/**
 * Created by zero on 15/12/26.
 */
/*
 * Copyright (c) 2015. Xiamen Aupad Network Technology co,ltd.
 */

'use strict';

var React = require('react'),
    Fluxxor=require('fluxxor'),
    CommentBox,
    CommentList,
    Home;

CommentList=React.createClass({

    select:function(id){
        alert(id);
    },
    render:function(){
        var commentNodes=this.props.data.map(function(comment){
            return(

                <li>{comment.name}</li>
            );
        }.bind(this));
        return(
            <div className="commentList">
                <ul>
                    {commentNodes}
                </ul>
            </div>
        );
    }
});

CommentBox=React.createClass({

    mixins:[Fluxxor.FluxMixin(React),Fluxxor.StoreWatchMixin('BookStore')],
    getStateFromFlux:function(){
        this.actions=this.getFlux().actions;
        return{
            data:this.getFlux().store('BookStore').getBooks()
        }
    },
    componentDidMount:function(){

        this.actions.book.getBooks();
    },
    render:function(){
        return(
            <div className="commentBox">
                <h1>您好,世界</h1>
                <button type="button" className="btn btn-primary">Primary</button>
                <CommentList data={this.state.data}/>
            </div>
        );
    }
});


Home = React.createClass({


    getStateFromFlux: function () {
        return {}
    },

    renderTopBar: function () {
        return (
            <h1>Hello world!</h1>
        )
    },

    render: function () {

        return (
            <div>
               <CommentBox/>
            </div>
        );
    }
});

module.exports = Home