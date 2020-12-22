import React, { Component, Fragment, useState } from "react";
import axios from "axios";
import { Input, Row, Col, Button, Card, Divider, Pagination, Form, Select, Rate } from 'antd';
import firebase from 'firebase';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import { FieldStringOutlined, MailOutlined } from '@ant-design/icons';
import { Typography } from 'antd';
import UserHeader from './userHeader';
import filter from '@mcabreradev/filter';
import JwPagination from 'jw-react-pagination';
import { urlConfig } from '../config/config';


const { Title } = Typography;


const gridStyle = {
    width: '25%',
    textAlign: 'center',
};

const style = { background: '#0092ff', padding: '8px 0' };


class BrowseOffers extends React.Component {

    constructor() {
        super();
        this.state = {
            dstCurrency: null,
            srcCurrency: null,
            srcAmount: null,
            pageOfItems: []
        };
        this.onChangePage = this.onChangePage.bind(this);

    }

    // Listen to the Firebase Auth state and set the local state.
    componentDidMount() {

        axios
            .get(urlConfig.url + "/exchangeOffer/getAllActiveOffers/" + localStorage.getItem('userName'))
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        offers: response.data,
                        backupArray: response.data,
                    });
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });
    }

    onSourceCurrencyChange = (e) => {
        if (e) {
            this.setState({
                sourcecurrency: e.toUpperCase()
            })

        }
        else {
            this.setState({
                sourcecurrency: ''
            })
        }
        this.filterOffers();
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        }, () => {
                this.filterOffers();
        })
        console.log('state', this.state);
        //this.filterOffers();
    }
    filterOffers() {
        console.log("inside fite", this.state.offers);
        console.log("inside fite", this.state);
        var filteredArray = this.state.backupArray;
        
        if (this.state.destinationcurrency != null) {
            filteredArray = filter(filteredArray, {
                'destinationCurrency': this.state.destinationcurrency
            });
        }  
        if (this.state.sourcecurrency != null) {
            filteredArray = filter(filteredArray, {
                'sourceCurrency': this.state.sourcecurrency
            });
        }
        if (this.state.srcAmount != null) {
            filteredArray = filter(filteredArray, {
                'amountToRemitSourceCurrency': this.state.srcAmount
            });
        }
        

        console.log("inside fite", filteredArray);
        this.setState({ offers: filteredArray });

    }

    onDestinationCurrencyChange = (e) => {
        if (e) {
            this.setState({
                destinationcurrency: e.toUpperCase()
            }, () => {
                    this.filterOffers();
            })
        }
        else {
            this.setState({
                destinationcurrency: ''
            }, () => {
                    this.filterOffers();
            })
        }
        // this.filterOffers();
    }
    onChangePage(pageOfItems) {
        // update local state with new page of items
        this.setState({ pageOfItems });
    }

    render() {
        return (
            <>
                <div>
                    <div>
                        <UserHeader selectedKey={['2']} />
                    </div>
                    <Title level={3}>Browse Offers</Title>
                    <div >
                        <Row >
                            <Divider type="vertical" />
                            <Col className="gutter-row" span={3}>
                                <Form.Item
                                    name="sourcecurrency"
                                    label="Source Currency"
                                    rules={[
                                        {
                                            required: false,
                                        },
                                    ]}
                                >
                                    <Select onChange={this.onSourceCurrencyChange} name="srcCurrency" style={{ "max-width": "57px" }}>
                                        <Option value="EUR">EUR</Option>
                                        <Option value="GBP">GBP</Option>
                                        <Option value="INR">INR</Option>
                                        <Option value="RMB">RMB</Option>
                                        <Option value="USD">USD</Option>
                                    </Select>
                                </Form.Item>

                            </Col>
                            <Divider type="vertical" />
                            <Col className="gutter-row" span={4}>
                                <Form.Item
                                    name="destinationcurrency"
                                    label="Destination Currency"
                                    rules={[
                                        {
                                            required: false,
                                        },
                                    ]}
                                >
                                    <Select onChange={this.onDestinationCurrencyChange} name="dstCurrency" style={{ "max-width": "57px" }}>
                                        <Option value="EUR">EUR</Option>
                                        <Option value="GBP">GBP</Option>
                                        <Option value="INR">INR</Option>
                                        <Option value="RMB">RMB</Option>
                                        <Option value="USD">USD</Option>
                                    </Select>
                                </Form.Item>
                            </Col>
                            <Divider type="vertical" />
                            <Col className="gutter-row" span={5}>
                                <Form.Item
                                    name="Source Currency Amount"
                                    label="Source Currency Amount"
                                    rules={[
                                        {
                                            required: false,
                                        },
                                    ]}
                                >
                                    <Input
                                        type="text"
                                        value="1000"
                                        name="srcAmount"
                                        onChange={this.onChange}
                                        style={{ width: 50 }}
                                    />
                                </Form.Item>
                            </Col>
                            <Divider type="vertical" />
                            <Col className="gutter-row" span={5}>
                                <Form.Item
                                    name="Destination Currency Amount"
                                    label="Destination Currency Amount"
                                    rules={[
                                        {
                                            required: false,
                                        },
                                    ]}
                                >
                                    <Input
                                        type="text"
                                        value="1000"
                                        name="destAmount"
                                        onChange={this.onChange}
                                        style={{ width: 50 }}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>

                    </div>
                    <Card>
                        {this.state.pageOfItems && this.state.pageOfItems.map((value, index) => {
                            return <>
                                <Card.Grid bordered={true} style={gridStyle}>
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
                                    <Divider orientation="left">User Details</Divider>
                                    <p>{value.user.name}<Divider type="vertical" /> 
                                    <Link to={{pathname: '/user/transHistory',state: value}}>
                                        <Rate disabled defaultValue={value.user.reputation} />
                                        </Link>
                                    {/* <Rate disabled defaultValue={4} />  */}
                                    
                                    </p>
                                    <Divider dashed />
                                    <Link to={{
                                        pathname: '/offer/details/',
                                        state: value
                                    }}>
                                        <Button type="primary">Show Offer Details</Button>
                                    </Link>
                                </Card.Grid>
                            </>
                        })}

                    </Card>
                    <JwPagination items={this.state.offers} onChangePage={this.onChangePage} />

                </div>
            </>
        )
    }
}
export default BrowseOffers;