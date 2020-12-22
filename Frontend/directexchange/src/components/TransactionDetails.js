import React, { Component, Fragment, useState } from "react";
import axios from "axios";
import { Button, Card, Divider, Pagination, Modal, Input, Form } from 'antd';
import firebase from 'firebase';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import { MailOutlined } from '@ant-design/icons';
import { useHistory } from "react-router-dom";


const gridStyle = {
    width: '25%',
    textAlign: 'center',
};


class TransactionDetails extends React.Component {


    constructor() {
        super();
        this.state = {
            showModal: false
        }
    }

    // Listen to the Firebase Auth state and set the local state.
    componentDidMount() {


        const { data } = this.props.location

        console.log('this.state', data);
        if (this.props.location.state) {
            this.setState({ offerDetails: data });
        }
    }

    onAcceptClick = (e) => {
        //Show modal
        this.setState({ showModal: true });
    }

    closeModal = (e) => {
        //Show modal
        this.setState({ showModal: false });
    }

    render() {
        return (
            <>
                {this.state.offerDetails &&
                    <>
                        <Card style={{ width: "30%" }} title="Offer Details">
                            <p><b>Offer ID : {this.state.offerDetails.id}
                                <Divider type="vertical" /></b>
                        Offer Status : {this.state.offerDetails.offerStatus}
                            </p>
                            <Divider />
                            <p><b>Amount</b> : {this.state.offerDetails.amountToRemitSourceCurrency}</p>
                            <p><b>Exchange Rate</b> : {this.state.offerDetails.exchangeRate}</p>
                            <Divider />
                            <p>Source Country : {this.state.offerDetails.sourceCountry}</p>
                            <p>Destination Country : {this.state.offerDetails.destinationCountry}</p>
                            <p>Destination Currency : {this.state.offerDetails.destinationCurrency}</p>
                            <Divider />
                            <p>Counter Offer Allowed? : {this.state.offerDetails.allowCounterOffers}</p>
                            <p>Split Allowed? : {this.state.offerDetails.allowSplitExchanges}</p>
                            <p>Expiration Date : {this.state.offerDetails.expirationDate}</p>

                            {/* <p>{this.state.offerDetails.receivingAccountNumber}</p> */}
                            <p>Bank : {this.state.offerDetails.receivingBankName}</p>
                            <Divider orientation="left">User Details</Divider>
                            <p>{this.state.offerDetails.user.name}<Divider type="vertical" /> <MailOutlined /> {this.state.offerDetails.user.userName}</p>
                            <Divider dashed />
                            <Button type="primary" onClick={this.onAcceptClick} >Accept</Button> <Divider type="vertical"> </Divider>
                            {/* <Button Disabled type="primary">Reject</Button> */}
                            <Button type="primary" onClick={this.counterOfferHandler}>Counter</Button>
                        </Card>


                    </>
                }
            </>
        )
    }
}
export default TransactionDetails;