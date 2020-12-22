
import React, { Component } from 'react';
import { Card, Button } from 'antd';
import Header from './Header';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import { Redirect } from 'react-router';


class Logout extends Component{

    constructor(){
        super();
       this.state = {
            data:[],
            loaded:true,
            redirectPage: '',

       } 

    }

    componentDidMount(){
        //api request to load table

        
       
    }

    onClickButton = () => {
       
            this.setState({
                redirectPage: <Redirect to={{ pathname: '' }} />
              },()=>{
                  location.reload()
              })
        
       

    }

    
    render(){
        return (
           <div>
               {this.state.redirectPage}
             <div>
                    <Header selectedKey={['3']} />
                    </div>
                    <br></br>

               <div style={{textAlign:'center'}}>
                   <h1>Thank you for using our services</h1>
                        
                                    <Button type="primary" onClick={this.onClickButton}>Login</Button>
                 
             </div>


            </div>
        );
    }
}

export default Logout;


