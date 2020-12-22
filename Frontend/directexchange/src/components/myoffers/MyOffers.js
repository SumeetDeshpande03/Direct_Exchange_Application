import React, { Component, Fragment, useState } from "react";
import axios from "axios";
import { Button, Card, Divider } from 'antd';
import firebase from 'firebase';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import UserHeader from '../userHeader';
import { urlConfig } from "../../config/config";


const gridStyle = {
    width: '25%',
    textAlign: 'center',
};


class MyOffers extends React.Component {

    constructor() {
        super();
        this.state = {

        }
    }

    // Listen to the Firebase Auth state and set the local state.
    componentDidMount() {

        axios
            .get(urlConfig.url + "/exchangeOffer/getOffers/" + localStorage.getItem('userName'))
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        offers: response.data
                    });
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });
    }

    render() {
        return (
            <div>
                    <div>
                        <UserHeader selectedKey={['1']} />
                    </div>
            <Card title="My Offers">
                {this.state.offers && this.state.offers.map((value, index) => {
                    return <Card.Grid bordered={true} style={gridStyle}>
                        <p><b>Offer ID : {value.id}
                            <Divider type="vertical" /></b>
                        Offer Status : {value.offerStatus}
                        </p>
                        <Divider />
                        <p><b>Amount</b> : {value.amountToRemitSourceCurrency}</p>
                        <p><b>Exchange Rate</b> : {value.exchangeRate}</p>
                        <Divider />
                        <p>Source Country : {value.sourceCountry}</p>
                        <p>Destination Country : {value.destinationCountry}</p>
                        <p>Destination Currency : {value.destinationCurrency}</p>
                        <Divider />
                        <p>Counter Offer Allowed? : {value.allowCounterOffers}</p>
                        <p>Split Allowed? : {value.allowSplitExchanges}</p>
                        <p>Expiration Date : {value.expirationDate}</p>

                        {/* <p>{value.receivingAccountNumber}</p> */}
                        <p>Bank : {value.receivingBankName}</p>
                        {value.offerStatus != "InTransaction" &&
                            <>
                                <Link to={{
                                    pathname: '/user/moffers/',
                                    state: value
                                }}>
                        
                                    <Button type="primary">Find Matching Offers</Button>
                                </Link>
                            </>
                        }

                        {value.offerStatus == "InTransaction" &&
                            <>
                        <Link to={{
                            pathname: '/user/moffers/',
                            state: value
                        }}>

                                <Button disabled type="primary">Find Matching Offers</Button>
                        </Link>
                            </>
                        }

                    </Card.Grid>
                })}

            </Card>
            </div>

        )
    }
}
export default MyOffers;