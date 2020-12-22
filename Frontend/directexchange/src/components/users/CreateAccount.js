import React, { Component } from 'react';
import { Form, Input, Alert, Select, Button, Row, Col, DatePicker } from 'antd';
import { Redirect } from 'react-router';
import Swal from 'sweetalert2';
import UserHeader from '../userHeader';
import { urlConfig } from '../../config/config';

import axios from 'axios';

const { MonthPicker } = DatePicker;

const { Option } = Select;

const userName = localStorage.getItem('userName');//
//const userName = "ambika@sjsu.edu";
class CreateAccount extends Component {
    formRef = React.createRef();

    constructor() {
        super();
        this.state = {

            redirectPage: '',
            showError: false,
        };
    }

    componentDidMount() {

    }

    onFinish = values => {
        values.userName = userName;
        console.log(values);

        axios
            .post(urlConfig.url + "/createBankAccount", values)
            .then(response => {
                console.log("Search Result : ", response.data);
                if (response.data != undefined) {
                    Swal.fire('Success', 'Account Created', 'success')
                    //   this.setState({
                    //     redirectPage: <Redirect to={{ pathname: '/user/createaccount/' }} />
                    //     })
                    location.reload();

                } else {

                }

            })
            .catch(errors => {
                console.log("Error" + errors);
            });

        //api call
    };
    disabledDate = (current) => {
        return current && current.valueOf() < Date.now();

    }

    render() {
        const frontFormLayout = {
            labelCol: {
                span: 8,
            },
            wrapperCol: {
                span: 8,
            },
        };
        const tailFormLayout = {
            wrapperCol: {
                offset: 8,
                span: 8,
            },
        };

        return (
            <div>

                {this.state.redirectPage}

                <div>
                    <UserHeader selectedKey={['7']} />

                </div>
                <div>
                    <div>
                        <Alert
                            message="Mandatory Information"
                            description="Two bank accounts are necessary to perform any operations"
                            type="info"
                            showIcon
                            closable
                        />
                    </div>
                    <Form
                        {...frontFormLayout}
                        ref={this.formRef}
                        name="CreateAccount"
                        onFinish={this.onFinish}
                        scrollToFirstError
                        style={{ paddingTop: '2%', marginBottom: '10%' }}

                    >
                        <Form.Item
                            name="bankName"
                            label="Bank Name"
                            rules={[
                                {

                                },
                                {
                                    required: true,
                                    message: 'Please input name of the bank!',
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            name="country"
                            label="Country"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please select country'
                                },
                            ]}
                        >
                            <Select

                                // onChange={this.onStateChange}
                                allowClear
                            >
                                <Option value="usa">USA</Option>
                                <Option value="india">India</Option>
                                <Option value="china">China</Option>
                                <Option value="uk">UK</Option>
                                <Option value="australia">Australia</Option>
                                <Option value="germany">Germany</Option>
                                <Option value="spain">Spain</Option>
                                <Option value="finland">Finland</Option>
                                <Option value="italy">Italy</Option>
                                <Option value="france">France</Option>

                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="accountNumber"
                            label="Account Number"
                            rules={[
                                {
                                    validator: this.checkLicenseNum,
                                },
                                {
                                    required: true,
                                    message: 'Please input your account number!',
                                }
                            ]}
                        >
                            <Input type="tel" style={{ width: '100%' }} />
                        </Form.Item>
                        <Form.Item
                            name="ownerName"
                            label="Owner Name"
                            rules={[

                                {
                                    required: true,
                                    message: 'Please input your name!',
                                },
                            ]}
                        >
                            <Input type="text" />
                        </Form.Item>
                        <Form.Item
                            name="ownerAddress"
                            label="Owner's Address"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please input your address!',
                                },
                            ]}
                        >
                            <Input.TextArea />

                        </Form.Item>

                        <Form.Item
                            name="primaryCurrency"
                            label="Primary Currency"
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Select

                                onChange={this.onMembershipChange}
                                allowClear
                            >
                                <Option value="EUR">EUR</Option>
                                <Option value="GBP">GBP</Option>
                                <Option value="INR">INR</Option>
                                <Option value="RMB">RMB</Option>
                                <Option value="USD">USD</Option>
                            </Select>
                        </Form.Item>

                        <Form.Item
                            name="modeSupported"
                            label="Supports"
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Select

                                onChange={this.onMembershipChange}
                                allowClear
                            >
                                <Option value="sending">Sending</Option>
                                <Option value="receiving">Receiving</Option>
                                <Option value="both">Both</Option>
                            </Select>
                        </Form.Item>

                        <Form.Item
                            {...tailFormLayout}
                        >
                            <Button type="primary" htmlType="submit">
                                Register
                            </Button>
                        </Form.Item>
                    </Form>
                </div>


            </div>
        );
    }



}


export default CreateAccount;