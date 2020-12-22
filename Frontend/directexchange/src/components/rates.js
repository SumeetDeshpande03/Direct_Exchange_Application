
import React, { Component } from 'react';
import { Table, Tag, Space } from 'antd';
import {ratesTable} from '../config/ratesInfo';
import UserHeader from './userHeader';

const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
      },
    {
        title: 'From_Currency',
        dataIndex: 'fromcurrency',
        key: 'fromcurrency',
      },
      {
        title: 'To_Currency',
        dataIndex: 'tocurrency',
        key: 'tocurrency',
      },
      {
        title: 'Rate',
        dataIndex: 'rate',
        key: 'rate',
      },
     
  ];


class Rates extends Component{

    constructor(){
        super();
       this.state = {
           dataSource:[],
            
       } 

    }

    componentDidMount(){
        //api request to load table

        const data = ratesTable;

          this.setState({
              dataSource:data
          })
          
    }

    render(){
        return (
           <div>
             <div>
                  <UserHeader selectedKey={['6']} />
                  </div>
                 <br></br>

               <div>
                <h1 style={{paddingTop:'2%', textAlign:'center'}} ><strong>Prevailing Rates Today</strong></h1>

             <Table style={{paddingTop:'1%'}}  dataSource={this.state.dataSource} columns={columns} />;
             </div>


            </div>
        );
    }
}

export default Rates;


