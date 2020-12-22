import React, { Component } from 'react';
import Headers from './Header';
import Footer from './Footer';
import { Redirect } from 'react-router';
import StyledFirebaseAuth from 'react-firebaseui/StyledFirebaseAuth';
import firebase from 'firebase';
import axios from "axios";
import { urlConfig } from '../config/config';


const initialState = {
    isSignedIn: false,
    redirectPage: '',
    showError: false,
    emailVerificationSent : false
};

class Login extends Component {
    constructor() {
        super();
        this.state = initialState;
        this.sendEmailVerification = this.sendEmailVerification.bind(this);
        // this.verificationDone = this.verificationDone.bind(this);
    }

    uiConfig = {
        // Popup signin flow rather than redirect flow.
        signInFlow: 'popup',
        // signInSuccessUrl : 'http://localhost:3000/',
         // We will display Google and Facebook as auth providers.
        signInOptions: [
          firebase.auth.EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD,  
          firebase.auth.GoogleAuthProvider.PROVIDER_ID,
          firebase.auth.FacebookAuthProvider.PROVIDER_ID
        ],
        callbacks: {
            // Avoid redirects after sign-in.
            signInSuccessWithAuthResult: () => false
        }
    };

    actionCodeSettings = {
        url: urlConfig.urlEmail + '/?user_verified=true'
    };
    

    // Listen to the Firebase Auth state and set the local state.
    componentDidMount() { 
        this.unregisterAuthObserver = firebase.auth().onAuthStateChanged(
            (user) => {
                if (user) {
                    var data = {
                        name: user.displayName,
                        userName: user.email
                        // emailVerified: user.emailVerified
                    }
                    console.log(this.props);
                    const isEmailVerified =  this.props.location.search.includes("verified=true");

                    if (isEmailVerified) {
                        data = {...data, isVerified : true};
                        axios.put(urlConfig.url + "/user/updateuser", data)
                            .then(response => {
                                console.log("Verification status updated: ", response);
                            })
                            .catch(errors => {
                                console.log("Error" + errors);
                            });
                    }
                    
                    axios.get(urlConfig.url + "/user/login/" + data.userName)
                        .then(response => {
                            console.log(response);
                            if (!response.data.isVerified && !isEmailVerified) {
                                this.sendEmailVerification();
                                    this.setState(
                                        {
                                            emailVerificationSent : true
                                        }
                                );  
                            } else {
                                this.setState({ isSignedIn: !!user });
                                localStorage.setItem("userName", user.email);
                                localStorage.setItem("enablePostOffer", true);
                                location.reload();

                            }
                            
                        })
                        .catch((response,errors) => {
                            console.log(response, errors)
                            axios.post(urlConfig.url + "/user/signup", data)
                                .then(response => {
                                    console.log("Search Result : ", response);
                                    this.sendEmailVerification();
                                    this.setState(
                                        {
                                            emailVerificationSent : true
                                        }
                                    );   
                                })
                                .catch(errors => {
                                    console.log("Error" + errors);
                                });
                        });                         
                }
                
            }

        );
    }

    // Make sure we un-register Firebase observers when the component unmounts.
    componentWillUnmount() {
        this.unregisterAuthObserver();
    }
  
    noop = () => { }

    sendEmailVerification = () => {
        var user = firebase.auth().currentUser;
        user.sendEmailVerification(this.actionCodeSettings).then(function () {
            console.log("Sent email");
            firebase.auth().signOut();
        }).catch(function (error) {
            console.log(error)
        });
    }

    render() {
        if (!this.state.isSignedIn && !this.state.emailVerificationSent) {
            return (
                <div>
                    <div>
                        <Headers selectedKey={['3']} />
                    </div>
                    <div style={{ marginLeft: "50%", marginTop: "7%" }}>
                        <StyledFirebaseAuth uiConfig={this.uiConfig} firebaseAuth={firebase.auth()}/>
                    </div>
                    <div>
                        <Footer />
                    </div>

                </div>
            );
        }
        if (this.state.isSignedIn) {
           return( <div><Redirect to={{ pathname: '/' }} ></Redirect> </div>)
        }

        if (this.state.emailVerificationSent) {
            return (
                <div>
                    <div>
                        <Headers />
                    </div>
                    <h1>Verification Link Sent</h1>
                    <p>Please verify your email address</p>
                    <div>
                        <Footer />
                    </div>
                </div>
            );
        }
        //  else {
        //     return(<div> Redirect to verified profile page</div>)
        // }
    }
}

export default Login;
