import React, { Component, Fragment, useState } from "react";

import { Button, Card, message, Input, Row, Col } from 'antd';
import {urlConfig} from '../../config/config';

const { TextArea } = Input;

const gridStyle = {
    width: '50%',
    textAlign: 'center',
};
const userName = localStorage.getItem('userName');

class OfferCardSplit extends React.Component {

    constructor(props){
        super(props)
        this.state={
            offerStatus: this.props.value.offerStatus,
            allowCounterOffers: this.props.value.allowCounterOffers,
            amountToRemitSourceCurrency: this.props.value.amountToRemitSourceCurrency,
            destinationCountry: this.props.value.destinationCountry,
            destinationCurrency:this.props.value.destinationCurrency,
            expirationDate:this.props.value.expirationDate,
            sourceCountry:this.props.value.sourceCountry,
            sourceCurrency:this.props.value.sourceCurrency,
            userAmountToRemit:this.props.userAmountToRemit,
            proposedOffer:'',

        }
    }

    changeProposedOffer = (e) => {
        console.log(e.target.value);
        this.setState({
            proposedOffer:e.target.value
        })
    }

    counterOfferClicked = (e) => {
        console.log(this.state.proposedOffer)

        //check if it lies within the range
    }

    sendMessage = (e) => {

        try{
        let object = {
            fromUser:userName,
            toUser:this.props.value.user.userName,
            message:this.state.message
        }

        axios
        .post(urlConfig.url + "/sendEmail", object)
        .then(response => {
            console.log("Search Result : ", response.data);
            if (response.data != undefined) {
                message.success('Successfully sent email to the user')

                this.setState({
                        message:''
                });
            } else {
                this.setState({
                    message:''
            });
            }

        })
        .catch(errors => {
            console.log("Error" + errors);
        });
    }
    catch(e){
        this.setState({
            message:''
    });
    }
    }

    messageChange = (e) => {
        if(e){
            this.setState({
                message:e.target.value
            })
        }
        else{
            this.setState({
                message:''
            })
        }
    }

    render(){
        return(
            <Card.Grid bordered={true} style={gridStyle} extra={<a>{this.state.offerStatus}</a>}>
                                  <p>Counter Offer Allowed: <strong>{this.state.allowCounterOffers}</strong></p>
                                  <p>Amount to Remit: <strong>{this.state.amountToRemitSourceCurrency}</strong></p>
                                  <p>Destination Country: <strong>{this.state.destinationCountry}</strong></p>
                                  <p>Destination Currency: <strong>{this.state.destinationCurrency}</strong></p>
                                  {/* <p>{value.exchangeRate}</p> */}
                                  <p>Expiry Date: <strong>{this.state.expirationDate}</strong></p>
                                  {/* <p>{value.id}</p> */}
                                  <p>Status Of Offer: <strong>{this.state.offerStatus}</strong></p>
                                  {/* <p>{value.receivingAccountNumber}</p> */}
                                  <p>Source Country: <strong>{this.state.sourceCountry}</strong></p>
                                  <p>Source Currency: <strong>{this.state.sourceCurrency}</strong></p>
                                  {/* <p>{value.receivingBankName}</p> */}
                                 
                                  <p></p>

                                  <Row>
                                      <Col md={14}>
                                      <TextArea onChange={this.messageChange} value={this.state.message} rows={4} />
                                      </Col>
                                      <Col md={10} style={{paddingTop:'5%'}}>
                                   <Button type="primary" onClick={this.sendMessage} danger>Send Message</Button>
                                   </Col>
                                   </Row>
                                 </Card.Grid>
                                
                                 
        );
    }
    





}


export default OfferCardSplit;
