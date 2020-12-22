import React, { Component, Fragment, useState } from "react";

import { Button, Card, Input, Row, Col } from 'antd';
import {
   
    Link
} from "react-router-dom";

const gridStyle = {
    width: '30%',
    textAlign: 'center',
};


import OfferCardSplit from './OfferCardSplit';
const { TextArea } = Input;


class SplitMatchFirst extends React.Component {

    constructor(props){
        super(props)
        this.state={
            // offerStatus: this.props.value.offerStatus,
            // allowCounterOffers: this.props.value.allowCounterOffers,
            // amountToRemitSourceCurrency: this.props.value.amountToRemitSourceCurrency,
            // destinationCountry: this.props.value.destinationCountry,
            // destinationCurrency:this.props.value.destinationCurrency,
            // expirationDate:this.props.value.expirationDate,
            // sourceCountry:this.props.value.sourceCountry,
            // sourceCurrency:this.props.value.sourceCurrency,
            // userAmountToRemit:this.props.userAmountToRemit,
            // proposedOffer:'',
            loaded:false,
            allowCounterOffers:false,
            amountToRemit:0,
            splitUser:'',
            proposedOffer:0

        }
    }

    componentDidMount(){
        let totalAmount = 0;
        let allowCounter = true;
        let splitUser = '';
        for(let i = 0; i<this.props.offers.length;i++){
            totalAmount+=parseInt(this.props.offers[i].amountToRemitSourceCurrency);
            if(this.props.offers[i].allowCounterOffers.toUpperCase() === "DENY"){
                allowCounter = false
            }
        }

        if(this.props.offers[0] && this.props.offers[1]){
            if(this.props.offers[0].amountToRemitSourceCurrency>this.props.offers[1].amountToRemitSourceCurrency){
                splitUser = this.props.offers[0].id
              
            }
            else{
               
                    splitUser = this.props.offers[0].id
            
            }
        }

        this.setState({
            amountToRemit:totalAmount,
            allowCounterOffers:allowCounter,
            splitUser:splitUser
        },()=>{
            this.setState({
                loaded: true
            })
        })

        console.log(totalAmount);
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

    changeMyOfferClicked = (e) => {

    }

    acceptOfferClicked = () => {
        
    }

    render(){
        return(
            <div style={{marginLeft:'4%', marginRight:'4%'}}> 
            <Card title="Split Matching Offer">
            <Link to={{ pathname: '/user/details/', state: this.props.offers }}>
                                   <Button type="primary">View User details</Button>
                                  </Link>
                                  <p></p>
                {
                    this.props.offers.length && this.state.loaded && this.props.offers.map((value, ind)=>{
                        return (
                            <div>
                            <OfferCardSplit key={ind} value={value} userAmountToRemit={this.state.amountToRemit}></OfferCardSplit> 
                           
                            </div>
                        )
                    })
                }
                <p></p>
                <Button type="primary" style={{background:'green'}} onClick={(e)=>this.acceptOfferClicked(e)}>Accept Offer</Button>
                                  <p></p>
                                 
                                  <Row>
                                      <Col md={12}>
                                  <Input type="text" value={this.state.proposedOffer} onChange={this.changeProposedOffer} disabled={this.state.allowCounterOffers}></Input>
                                  </Col>
                                  <Col md={12}>

                                 <Button type="primary" disabled={this.state.allowCounterOffers} onClick={(e)=>this.counterOfferClicked(e)} danger>Propose Counter Offer</Button>
                                 </Col>
                                 </Row> 


                              {/* <Button type="primary" style={{background:'green'}} disabled={this.props.amountToRemit === this.state.userAmountToRemit? false: true}>Accept Offer</Button>
                                  <p></p>
                                  <Row>
                                      <Col md={12}>
                                  <Input type="text" value={this.state.changeOffer} onChange={this.onChangeChangeOffer} disabled={this.state.amountToRemit === this.state.userAmountToRemit? true: false}></Input>
                                       </Col>
                                  <Col md={12}>

                                 <Button type="primary" disabled={this.state.amountToRemit === this.state.userAmountToRemit? true: false} onClick={(e)=>this.changeMyOfferClicked(e)} style={{background:'blue'}}>Change My Offer</Button>
                                 </Col>
                                 </Row>
                                 <p></p>
                                  <Row>
                                      <Col md={12}>
                                  <Input type="text" value={this.state.proposedOffer} onChange={this.changeProposedOffer} disabled={this.state.allowCounterOffers}></Input>
                                  </Col>
                                  <Col md={12}>

                                 <Button type="primary" disabled={this.state.allowCounterOffers} onClick={(e)=>this.counterOfferClicked(e)} danger>Propose Counter Offer</Button>
                                 </Col>
                                 </Row> */}

                                 
                                 
                                 
            </Card>
            <br></br>
        </div>
                                 
        );
    }





}


export default SplitMatchFirst;
