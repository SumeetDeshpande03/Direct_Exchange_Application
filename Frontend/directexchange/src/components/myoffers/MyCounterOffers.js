import React, { Component, Fragment, useState } from "react";
import axios from "axios";
import { Button, Card, Divider, Layout, Menu, Breadcrumb, Space, Row, Col } from 'antd';
import firebase from 'firebase';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import UserHeader from '../userHeader';
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons';
import { urlConfig } from '../../config/config';


const { SubMenu } = Menu;
const { Header, Content, Footer, Sider } = Layout;

const gridStyle = {
    width: '100%',
    textAlign: 'center',
};

const gridStyle1 = {
    width: '30%',
    textAlign: 'center',
};

class MyCounterOffers extends React.Component {

    constructor() {
        super();
        this.state = {

        }
    }

    onSelectCard = (e) => {
        console.log('button', e);
        var selectedOffer = this.state.offers.find(obj => {
            return obj.id === e;
        })
        this.setState({
            selectedOffer: selectedOffer
        });

    }

    onAcceptClick = (e) => {



        var data = {
            "exchangeOfferId": this.state.selectedOffer.id,
            "counterOfferId": e.id,
            "status": "InTransaction"
        }
        console.log('e', data);
        axios
            .put(urlConfig.url + "/exchangeOffer/updateCounterOffer/", data)
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        // offers: response.data,
                        // showModal: false
                    });
                    //Redirect to transaction page
                    this.props.history.push({
                        pathname: '/user/myoffers',
                        data: response.data // your data array of objects
                    })
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });

          

            var bankObj = this.state.bankAccounts.find(obj => {
                return obj.id === this.state.selectedBankId;
            })
            var data = {
                "userName": e.splitUserId1,
                "amount": this.state.selectedOffer.amountToRemitSourceCurrency,
                "percentOfTotalAmount": 5,
                "exchangeOfferId": this.state.selectedOffer.id,
                "bankName": 'US Bank',
            "accountNumber": 93049039438488999,
                "inverseExRate": (1 / this.state.selectedOffer.exchangeRate).toFixed(5)
            }

         
            axios
                .put(urlConfig.url + "/exchangeOffer/updateOfferStatusToInTransactionReverse/", data)
                .then(response => {
                    console.log("Search Result : ", response.data);
                    if (response.data != undefined) {
                        this.setState({
                            offers: response.data,
                            showModal: false
                        });
                        //Redirect to transaction page
    
    
    
                        // this.props.history.push({
                        //     pathname: '/user/myoffers/',
                        //     data: response.data // your data array of objects
                        // })
                    } else {
    
                    }
    
                })
                .catch(errors => {
                    console.log("Error" + errors);
                });

               


    }

    onRejectClick = (e) => {
        var data = {
            "exchangeOfferId": this.state.selectedOffer.id,
            "counterOfferId": e,
            "status": "Reject"
        }
        axios
            .put(urlConfig.url + "/exchangeOffer/updateCounterOffer/", data)
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        // offers: response.data,
                        // showModal: false
                    });
                    //Redirect to transaction page
                    location.reload();
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });
    }

    acceptOfferHandler = (e) => {

        console.log('Insideacceptoffer');


        var bankObj = this.state.bankAccounts.find(obj => {
            return obj.id === this.state.selectedBankId;
        })
        var data = {
            "userName": localStorage.getItem('userName'),
            "amount": this.state.selectedOffer.amountToRemitSourceCurrency,
            "percentOfTotalAmount": 5,
            "exchangeOfferId": this.state.selectedOffer.id,
            "bankName": 'US Bank',
            "accountNumber": 93049039438488999,
            "ExRate": (1 / this.state.selectedOffer.exchangeRate).toFixed(5)
        }
        axios
            .put(urlConfig.url + "/exchangeOffer/updateOfferStatusToInTransaction/", data)
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        offers: response.data,
                        showModal: false
                    });
                    //Redirect to transaction page



                    this.props.history.push({
                        pathname: '/offer/myoffers/',
                        data: response.data // your data array of objects
                    })
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });
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

        axios
            .get(urlConfig.url + "/getBankAccountsOfUser/" + localStorage.getItem('userName'))
            .then(response => {
                console.log("Bank Accounts : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        bankAccounts: response.data

                    });
                } else {
                    //No Accounts found
                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });
    }

    render() {
        console.log(this.state.offers);

        return (
            <div>
                <div>
                    <UserHeader selectedKey={['11']} />
                </div>
                <Content style={{ padding: '0 50px' }}>
                    <Layout className="site-layout-background" style={{ padding: '24px 0' }}>
                        <Sider className="site-layout-background" width={300}>
                            <Card title="My Offers. Click on counter offers" >
                                {this.state.offers && this.state.offers.map((value, index) => {
                                    return <Card.Grid bordered={true} style={gridStyle}>
                                        <p><b>Offer ID : {value.id}
                                            <Divider type="vertical" /></b>
                                            Offer Status : {value.offerStatus}
                                        </p>
                                        <Divider />
                                        <p><b>Amount</b> : {value.amountToRemitSourceCurrency}</p>
                                        <p><b>Exchange Rate</b> : {value.exchangeRate}</p>
                                        <Button type="dashed" onClick={this.onSelectCard.bind(this, value.id)}>Counter Offers</Button>
                                    </Card.Grid>
                                })}

                            </Card>
                        </Sider>
                        <Content style={{ padding: '0 24px', minHeight: 280 }}>
                            <Card title="Offer Details">

                                {this.state.selectedOffer != undefined &&
                                    <>
                                        <Row gutter={[16, 24]}>
                                            <Col className="gutter-row" span={6}>
                                                <div> Offer ID : {this.state.selectedOffer.id}</div>
                                            </Col>
                                            <Col className="gutter-row" span={6}>
                                                <div>Offer Status : {this.state.selectedOffer.offerStatus}</div>
                                            </Col>
                                            <Col className="gutter-row" span={6}>
                                                <div>Amount : {this.state.selectedOffer.amountToRemitSourceCurrency}</div>
                                            </Col>
                                            <Col className="gutter-row" span={6}>
                                                <div>Exchange Rate: {this.state.selectedOffer.exchangeRate}</div>
                                            </Col>
                                            <Col className="gutter-row" span={6}>
                                                <div>Source Country : {this.state.selectedOffer.sourceCountry}</div>
                                            </Col>
                                            <Col className="gutter-row" span={6}>
                                                <div>Destination Country : {this.state.selectedOffer.destinationCountry}</div>

                                            </Col><Col className="gutter-row" span={6}>

                                                <div>Destination Currency : {this.state.selectedOffer.destinationCurrency}</div>
                                            </Col><Col className="gutter-row" span={6}>
                                                <div>Counter Offer Allowed? : {this.state.selectedOffer.allowCounterOffers}</div>
                                            </Col><Col className="gutter-row" span={6}>
                                                <div>Split Allowed? : {this.state.selectedOffer.allowSplitExchanges}</div>
                                            </Col><Col className="gutter-row" span={6}>
                                                <div> Expiration Date : {this.state.selectedOffer.expirationDate}</div>
                                            </Col><Col className="gutter-row" span={6}>

                                                <div>Bank : {this.state.selectedOffer.receivingBankName}</div>
                                            </Col>
                                            {/* <Divider orientation="left">User Details</Divider>
                                        {this.state.selectedOffer.user.name}<Divider type="vertical" /> {this.state.selectedOffer.user.userName} */}

                                        </Row>

                                    </>
                                }
                            </Card>
                            <Card>



                                {this.state.selectedOffer != undefined && this.state.selectedOffer && this.state.selectedOffer.proposedOffers.map((value, index) => {
                                    return <>
                                        <Card.Grid bordered={true} style={gridStyle1}>
                                            <p><b>Offer ID : {value.id}
                                                <Divider type="vertical" /></b>
                        Offer Status : {value.status}
                                            </p>
                                    <Divider />
                                        <p><b>User 1 Split Amount</b> : {value.splitUser1Amount}</p>
                                        <p><b>User 2 Split Amount</b> : {value.splitUser2Amount}</p>

                                        <p><b>Exchange Rate</b> : {value.exchangeRate}</p>
                                        <Divider />
                                            {(value.status == "Open" || value.status == "open" ) && 
                                            <Space>
                                                <Button type="primary" onClick={this.onAcceptClick.bind(this, value)} >Accept</Button>
                                                {/* <Button Disabled type="primary">Reject</Button> */}
                                                <Button type="primary" onClick={this.onRejectClick.bind(this, value.id)}>Reject</Button>
                                            </Space>
                                            }
                                            {value.status == "Expired" &&
                                                <Space>
                                                    Offer Expired
                                            </Space>
                                            }
                                            {value.status == "Reject" &&
                                                <Space>
                                                    Offer Rejected
                                            </Space>
                                            }
                                </Card.Grid>
                                </>
                        })}
                                {this.state.selectedOffer != undefined && this.state.selectedOffer && this.state.selectedOffer.proposedOffers.length == 0 &&
                                    <p>
                                        No Counter Offers Found
                         </p>



                                }

                    </Card>

                        </Content>
                    </Layout>
                </Content>

            </div>

        )
    }
}
export default MyCounterOffers;