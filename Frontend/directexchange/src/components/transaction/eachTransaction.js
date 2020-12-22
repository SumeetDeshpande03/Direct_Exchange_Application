
import React, { Component } from 'react';
import { Card, Button } from 'antd';
import axios from "axios";
import {urlConfig} from '../../config/config';
import Swal from 'sweetalert2';


class EachTransaction extends Component{

    constructor(props){
        super(props);
       this.state = {
           dataSource:this.props,
            
       } 

    }
    /*
    accountNumber: 837476574859
    amount: 2000
    bankName: "us bank"
    country: "usa"
    createDate: "2020-12-15T18:24:11.040+00:00"
    currency: "USD"
    exchange_offer: {id: 3, sourceCountry: "usa", sourceCurrency: "USD", amountToRemitSourceCurrency: 2000, destinationCountry: "india", …}
    expiryDate: ""
    id: 7
    isTransferred: false
    offerid1: 0
    offerid2: 0
    percentOfTotalAmount: 5
    rate: 0.01
    serviceFee: 0
    status: "InTransaction"
    username: "aliceharper216@gmail.com"
    */
    componentDidMount(){
        
    }

    

    pay = (e) => {
        //change status to paid
///transaction/updateTransactionsBasedOnPayment/
let values = {
    exchangeOfferId : this.props.transaction.exchange_offer.id,
    amount: this.props.transaction.amount,
    userName: localStorage.getItem("userName")
};

axios
.post(urlConfig.url + "/transaction/updateTransactionsBasedOnPayment/", values)
.then(response => {
    console.log("Search Result : ", response.data);
    if (response.data != undefined) {
        Swal.fire('Success', 'Payment Completed', 'success')
        .then(()=>{
            location.reload();
        })
    } else {

    }
    
})
.catch(errors => {
    console.log("Error" + errors);
});


    }

    render(){
        console.log(this.props)
        return (
           <div>
               <div>
               <Card.Grid>
                        <p>Amount to Transfer: {this.props.transaction.amount}</p>
                        <p>Currency: {this.props.transaction.currency}</p>
                        <p>Source Country: {this.props.transaction.country}</p>
                        <Button onClick={this.pay} style={{background:'green'}}>Pay</Button>
                </Card.Grid>
             </div>


            </div>
        );
    }
}

export default EachTransaction;


