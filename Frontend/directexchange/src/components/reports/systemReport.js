
import React, { Component } from 'react';
import {Result, Button, Card, Row, Col } from 'antd';
import { SmileOutlined, CheckCircleTwoTone, DollarCircleTwoTone, ExclamationCircleTwoTone } from '@ant-design/icons';
import {urlConfig} from '../../config/config';
import axios from "axios";
import {ratesTable} from '../../config/ratesInfo';

import UserHeader from '../userHeader';


class SystemReport extends Component{

    constructor(){
        super();
       this.state = {
           dataSource:[],
           loaded:false,
           completedTransactions:0,
           incompletedTransactions:0,
           amount:0,
           servicefee:0
            
       } 

    }

    componentDidMount(){
        //api request to load data
        let amount = 0;
        let servicefee = 0;
        let completedTransactions = 0;
        let incompletedTransactions = 0;
        let transSet = new Set();
        axios
        .get(urlConfig.url + "/alltransactions")
        .then(response => {
            console.log("Search Result : ", response.data);
            if (response.data != undefined) {
              let data = response.data;
              for(let i = 0 ; i<data.length;i++){
                if(data[i].status.toUpperCase() === "EXPIRED"){
                  if(!transSet.has(data[i].exchage_offer_id)){
                    incompletedTransactions+=1;
                    transSet.add(data[i].exchage_offer_id);

                  }
                }
                else if(data[i].status.toUpperCase() === "FULFILLED"){
                  if(!transSet.has(data[i].exchage_offer_id)){
                    completedTransactions+=1;
                    transSet.add(data[i].exchage_offer_id);

                  }
                 if(data[i].currency.toUpperCase()!=="USD"){
                    for(let j = 0 ; j<ratesTable.length; j++){
                      if(ratesTable[j]['fromcurrency'].toUpperCase() === data[i].currency.toUpperCase()
                      && ratesTable[j]['tocurrency'].toUpperCase() === "USD"){
                        amount+=(data[i].amount*ratesTable[j]['rate']);
                        servicefee+=(data[i].serviceFee*ratesTable[j]['rate']);

                      }
                  }

                  }
                  else{
                    amount+=data[i].amount;
                    servicefee+=data[i].serviceFee;
                  }
                }

              }
              this.setState({
                servicefee: servicefee,
                amount:amount,
                incompletedTransactions:incompletedTransactions,
                completedTransactions:completedTransactions
              })
                
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

    render(){
        return (
           <div>
             <div>
             <UserHeader selectedKey={['10']} />

                   </div>
                   <div>
                   <div>
                <h1 style={{paddingTop:'2%', textAlign:'center'}} ><strong>System Report</strong></h1>
                <Row>
                  <Col md={10}>
                <Card>
                <Result
    icon={<CheckCircleTwoTone twoToneColor="#52c41a" />}
    title={this.state.completedTransactions+" Total Completed Transactions"}
    //extra={<Button type="primary">Next</Button>}
  />

                </Card>
                </Col>
                <Col md={2}></Col>
                <Col md={10}>
                <Card>
                <Result
    icon={<ExclamationCircleTwoTone />}
    title={this.state.incompletedTransactions+" Total Incomplete Transactions"}
    //extra={<Button type="primary">Next</Button>}
  />
                </Card>
                </Col>
                </Row>
                <Row>
                <Col md={10}>
                <Card>
                <Result
    icon={<DollarCircleTwoTone  twoToneColor="blue"/>}
    title={this.state.amount+" Total amount"}
    //extra={<Button type="primary">Next</Button>}
  />
                </Card>
                </Col>
                <Col md={2}></Col>

                <Col md={10}>
                <Card>
                <Result
    icon={<DollarCircleTwoTone />}
    title={this.state.servicefee+" Service Fee"}
    //extra={<Button type="primary">Next</Button>}
  />
                </Card>
                </Col>
                </Row>

             </div>
                       </div>


            </div>
        );
    }
}

export default SystemReport;


