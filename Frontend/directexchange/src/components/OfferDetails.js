import React, { Component, Fragment, useState } from "react";
import axios from "axios";
import { Button, Card, Rate, Divider, Pagination, Modal, Input, Form, Select, InputNumber, Space, Row, Col } from 'antd';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import { MailOutlined } from '@ant-design/icons';
import { useHistory } from "react-router-dom";
import UserHeader from './userHeader';
import { urlConfig } from '../config/config';


const gridStyle = {
    width: '25%',
    textAlign: 'center',
};

const validateMessages = {
    required: '${label} is required!',
    number: {
        range: '${label} must +- 10% of original amount!',
    },

}

class OfferDetails extends React.Component {
    formRef = React.createRef();


    constructor() {
        super();
        this.state = {
            showModal: false,
            accountNum: 0,
            bankName: '',
            showCounterModal: false,
            updatedAmount: "",
            bankAccounts: null
        }
    }

    // Listen to the Firebase Auth state and set the local state.
    componentDidMount() {

        console.log('this.state', this.props.location.state);
        if (this.props.location.state) {
            this.setState({ offerDetails: this.props.location.state });
        }

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

    onAcceptClick = (e) => {
        //Show modal
        this.setState({ showModal: true });
    }

    closeModal = (e) => {
        //Show modal
        this.setState({ showModal: false });
    }

    setBankName = (e) => {
        if (e) {
            this.setState({
                bankName: e.target.value
            })
        }
        else {
            this.setState({
                bankName: ''
            })
        }
    }

    setAccountNum = (e) => {
        if (e) {
            this.setState({
                accountNum: e.target.value
            })

        }
        else {
            this.setState({
                accountNum: 0
            })
        }

    }




    acceptOfferHandler = (e) => {

        console.log('Insideacceptoffer');


        var bankObj = this.state.bankAccounts.find(obj => {
            return obj.id === this.state.selectedBankId;
        })
        console.log("inverseRate", (1 / this.state.offerDetails.exchangeRate).toFixed(5));
        var data = {
            "userName": localStorage.getItem('userName'),
            "amount": this.state.offerDetails.amountToRemitSourceCurrency,
            "percentOfTotalAmount": 5,
            "exchangeOfferId": this.state.offerDetails.id,
            "bankName": bankObj.bankName,
            "accountNumber": bankObj.accountNumber,
            "inverseExRate": (1 / this.state.offerDetails.exchangeRate).toFixed(5)
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
                        pathname: '/user/transHistory/',
                        data: response.data // your data array of objects
                    })
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });
    }
    toggleCounterModal = (e) => {
        //Show modal
        this.setState(prevState => ({
            showCounterModal: !prevState.showCounterModal
        }));
    }

    onBankChange = (e) => {
        //Show modal
        this.setState({
            selectedBankId: e
        });
        console.log('bank', e);
    }

    handleChangeAmount = (e) => {
        this.setState({ updatedAmount: e })
    }
    counterOfferHandler = (e) => {
        console.log('Insideacceptoffer');
        var data = {
            "splitUserId1": this.state.offerDetails.user.userName,
            "splitUserId2": null,
            "splitUser1Amount": this.state.updatedAmount,
            "splitUser2Amount": 0,
            "exchangeOfferId": this.state.offerDetails.id,
            "amount": this.state.updatedAmount
        }
        console.log(data);
        axios
            .post(urlConfig.url + "/exchangeOffer/updateOfferStatusForCounterOffer", data)
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    this.setState({
                        offers: response.data

                    });
                    this.props.history.push({
                        pathname: '/user/browseoffers/',
                        data: response.data // your data array of objects
                    })
                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });


    }

    render() {
        return (
            <>
                <div>
                    <div>
                        <UserHeader selectedKey={['3']} />
                    </div>


                    {this.state.offerDetails &&
                        <>
                            <Card style={{ width: "70%", "margin-left": "15%", "margin-top": "3%" }} title="Offer Details">
                                <Row gutter={[16, 24]}>
                                    <Col className="gutter-row" span={6}>
                                        <div> Offer ID : {this.state.offerDetails.id}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                        <div>Offer Status : {this.state.offerDetails.offerStatus}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                        <div>Amount : {this.state.offerDetails.amountToRemitSourceCurrency}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                        <div>Exchange Rate: {this.state.offerDetails.exchangeRate}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                        <div>Source Country : {this.state.offerDetails.sourceCountry}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                        <div>Destination Country : {this.state.offerDetails.destinationCountry}</div>

                                    </Col><Col className="gutter-row" span={6}>

                                        <div>Destination Currency : {this.state.offerDetails.destinationCurrency}</div>
                                    </Col><Col className="gutter-row" span={6}>
                                        <div>Counter Offer Allowed? : {this.state.offerDetails.allowCounterOffers}</div>
                                    </Col><Col className="gutter-row" span={6}>
                                        <div>Split Allowed? : {this.state.offerDetails.allowSplitExchanges}</div>
                                    </Col><Col className="gutter-row" span={6}>
                                        <div> Expiration Date : {this.state.offerDetails.expirationDate}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>

                                        <div>Bank : {this.state.offerDetails.receivingBankName}</div>
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                        User Details
                                {this.state.offerDetails.user.name}
                                    </Col>
                                    <Col className="gutter-row" span={6}>
                                    <Link to={{ pathname: '/user/transHistory', state: this.state.offerDetails }}>
                                        <Rate disabled defaultValue={this.state.offerDetails.user.reputation} />
                                        </Link>
                                    </Col>
                                    <Space>
                                        <Button type="primary" onClick={this.onAcceptClick} >Accept</Button>
                                        {/* <Button Disabled type="primary">Reject</Button> */}
                                        <Button type="primary" onClick={this.toggleCounterModal}>Counter</Button>
                                    </Space>

                                </Row>
                            </Card>

                            {/* <Card style={{ width: "30%" }} title="Offer Details">
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

                                <p>{this.state.offerDetails.receivingAccountNumber}</p>
                                <p>Bank : {this.state.offerDetails.receivingBankName}</p>
                                <Divider orientation="left">User Details</Divider>
                                <p>{this.state.offerDetails.user.name}<Divider type="vertical" /> <MailOutlined /> {this.state.offerDetails.user.userName}</p>
                                <Divider dashed />
                                <Button type="primary" onClick={this.onAcceptClick} >Accept</Button> <Divider type="vertical"> </Divider>
                                <Button Disabled type="primary">Reject</Button>
                                <Button type="primary" onClick={this.toggleCounterModal}>Counter</Button>
                            </Card> */}

                            <Modal
                                title="Provide Bank Details"
                                style={{ top: 20, width: "450px" }}
                                visible={this.state.showModal}
                                onOk={() => this.acceptOfferHandler()}
                                onCancel={() => this.closeModal()}
                            >
                                <Form.Item
                                    name="bankAccount"
                                    label="Bank Account"
                                    rules={[
                                        {
                                            required: false,
                                        },
                                    ]}
                                >
                                    {this.state.bankAccounts &&
                                        <Select onChange={this.onBankChange} name="dstCurrency" style={{ "max-width": "200px" }}>
                                            {this.state.bankAccounts.map(item => (
                                                <Option value={item.id}>{item.bankName}</Option>
                                            ))}
                                        </Select>
                                    }
                                </Form.Item>
                                {/* <Form.Item
                            label="Bank Name"
                            name="bankName"
                            rules={[{ required: true, message: 'Please input your bank name!' }]}
                        >
                            <Input type="text" value={this.state.bankName} onChange={this.setBankName} />
                        </Form.Item>
                        <Divider />
                        <Form.Item
                            label="Account Number"
                            name="accountNumber"
                            rules={[{ required: true, message: 'Please input your account number!' }]}
                        >
                            <Input type="text" value={this.state.accountNum} onChange={this.setAccountNum}/>
<<<<<<< HEAD
                        </Form.Item>
                    </Modal>
                </>
                }
=======
                        </Form.Item> */}
                            </Modal>
                            <Modal
                                title="Counter Offer Details"
                                centered
                                style={{ top: 20, width: "450px" }}
                                visible={this.state.showCounterModal}
                                // onOk={() => this.counterOfferHandler()}
                                onCancel={() => this.toggleCounterModal()}
                                footer={null}
                            >
                                <b>Current Offer Amount</b> : {this.state.offerDetails.amountToRemitSourceCurrency}
                                <Divider />
                                <Form validateMessages={validateMessages}
                                    ref={this.formRef}
                                    scrollToFirstError
                                    onFinish={this.counterOfferHandler}>
                                    <Form.Item
                                        label="Updated Amount"
                                        name="updatedAmount"
                                        rules={[
                                            {
                                                type: 'number',
                                                min: (this.state.offerDetails.amountToRemitSourceCurrency * 0.90),
                                                max: (this.state.offerDetails.amountToRemitSourceCurrency * 1.10)
                                            }, {
                                                required: true,
                                                message: 'Please enter amount'
                                            }]}
                                    >
                                        <InputNumber onChange={this.handleChangeAmount} value={this.state.updatedAmount} />
                                    </Form.Item>
                                    <Space>

                                        <Button style={{ float: "right" }} type="primary" htmlType="submit">
                                            Submit
                                    </Button>

                                        <Button danger style={{ float: "right" }} type="primary" onClick={this.toggleCounterModal}>
                                            Cancel
                                    </Button>
                                    </Space>
                                </Form>
                            </Modal>
                        </>
                    }
                </div>
            </>
        )
    }
}
export default OfferDetails;
