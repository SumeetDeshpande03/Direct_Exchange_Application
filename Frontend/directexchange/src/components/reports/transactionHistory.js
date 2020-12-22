
import React, { Component } from 'react';
import { Table, Tag, Space, DatePicker } from 'antd';
import {urlConfig} from '../../config/config';
import axios from "axios";
import UserHeader from '../userHeader';


const columns = [
    {
        title: 'Date',
        dataIndex: 'date',
        key: 'date',
      },
      {
        title: 'Source Currency',
        dataIndex: 'sourceCurrency',
        key: 'sourcecurrency',
      },
      {
        title: 'Source Amount',
        dataIndex: 'sourceAmount',
        key: 'sourceamount',
      },
      {
        title: 'Destination Currency',
        dataIndex: 'destCurrency',
        key: 'destcurrency',
      },
      {
        title: 'Destination Amount',
        dataIndex: 'destAmount',
        key: 'destamount',
      },
      {
        title: 'Status',
        dataIndex: 'status',
        key: 'status',
      },
      {
        title: 'Exchange Rate',
        dataIndex: 'rate',
        key: 'rate',
      },
      {
        title: 'Service Fee',
        dataIndex: 'serviceFee',
        key: 'servicefee',
      }, {
        title: 'Total Amount',
        dataIndex: 'total',
        key: 'total',
      },
     
  ];


class TransctionHistory extends Component{

    constructor(){
        super();
       this.state = {
           dataSource:[],
            
       } 

    }

    componentDidMount(){
        //api request to load table
        console.log(this.props.location.state)
        if(this.props.location.state && this.props.location.state.user?.userName){

          console.log("insideif")
          axios
          .get(urlConfig.url + "/transaction/" + this.props.location.state.user.userName)
          .then(response => {
              console.log("Search Result : ", response.data);
              if (response.data != undefined) {
                  this.setState({
                    dataSource:response.data
                  });
              } else {
                this.setState({
                  dataSource:[]
                })
  
              }
  
          })
          .catch(errors => {
              console.log("Error" + errors);
          });
        }
        else{

          console.log("insideelse")
        axios
        .get(urlConfig.url + "/transaction/" + localStorage.getItem('userName'))
        .then(response => {
            console.log("Search Result : ", response.data);
            if (response.data != undefined) {
                this.setState({
                  dataSource:response.data
                });
            } else {
              this.setState({
                dataSource:[]
              })

            }

        })
        .catch(errors => {
            console.log("Error" + errors);
        });
      }


       
          
    }

    render(){
        return (
           <div>
             <div>
                    <UserHeader selectedKey={['9']} />
                    
                   
                   </div>
                   <br></br>

                   <div>
                   <div>
                <h1 style={{paddingTop:'2%', textAlign:'center'}} ><strong>Transaction History</strong></h1>

             <Table style={{paddingTop:'1%'}}  dataSource={this.state.dataSource} columns={columns} />;
             </div>
                       </div>


            </div>
        );
    }
}

export default TransctionHistory;


