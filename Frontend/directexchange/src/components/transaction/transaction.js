
import React, { Component } from 'react';
import { Card } from 'antd';
import UserHeader from '../userHeader';
import EachTransaction from './eachTransaction';
import axios from "axios";
import {urlConfig} from '../../config/config';


class Transaction extends Component{

    constructor(){
        super();
       this.state = {
            data:[],
            loaded:true
       } 

    }

    componentDidMount(){
        //api request to load table

        axios
        .get(urlConfig.url + "/transaction/pendingTransactions/" + localStorage.getItem('userName'))
        .then(response => {
            console.log("Search Result : ", response.data);
            if (response.data != undefined) {
                this.setState({
                  data:response.data
                });
            } else {
              this.setState({
                data:[]
              })

            }

        })
        .catch(errors => {
            console.log("Error" + errors);
        });


        
       
    }

    
    render(){
        return (
           <div>
             <div>
                    <UserHeader selectedKey={['8']} />
                    </div>
                    <br></br>

               <div>
                   {console.log(this.state.loaded)}
                <h1 style={{paddingTop:'2%', textAlign:'center'}} ><strong>Your Pending Transactions</strong></h1>
                <Card>
                {
                    
                    this.state.loaded && this.state.data.map((value, ind)=>{
                        
                        return(

                            <EachTransaction key={ind} transaction = {value}></EachTransaction>

                        )
                    })
                }
</Card>
             </div>


            </div>
        );
    }
}

export default Transaction;


