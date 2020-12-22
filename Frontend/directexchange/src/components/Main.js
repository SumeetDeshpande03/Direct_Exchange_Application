import React from 'react';
import { Route, BrowserRouter } from 'react-router-dom';
import LoginForm from './Login';
import Rates from './rates';
import axios from "axios";
import { urlConfig } from '../config/config';


//import users components
import CreateAccount from './users/CreateAccount';
import PostOffer from './users/PostOffer';
import MyOffers from './myoffers/MyOffers';
import Headers from './Header';
import MatchingOffers from './myoffers/MatchingOffers';
import BrowseOffers from './BrowseOffers';
import OfferDetails from './OfferDetails';
//import TransactionDetails from './TransactionDetails';
import TransactionDetails from './transaction/transaction';
import MyCounterOffers from './myoffers/MyCounterOffers';
import TransactionHistory from './reports/transactionHistory';
import SystemReport from './reports/systemReport';
import Logout from './logout';

// Main Component
const initialState = {
  userName: null,
  enablePostOffer: false
};

class Main extends React.Component {
  constructor() {
    super();
    this.state = initialState;

  }

  componentDidMount = () => {
//alert(localStorage.getItem("userName"))
    this.setState({
      userName: localStorage.getItem("userName")
    })
    axios
      .get(urlConfig.url + '/getDistinctBankAccountsCountsOfUser/' + localStorage.getItem("userName"))
      .then(response => {
        console.log("Search Result : ", response.data);
        if (response.data != undefined) {
          if (response.data > 1) {
            this.setState({
              enablePostOffer: true
            });
          }

        } else {

        }

      })
      .catch(errors => {
        console.log("Error" + errors);
      });
  }

  render() {
    if (!this.state.userName || this.state.userName === null) {
      return (<Route exact path="/" component={LoginForm} key={Date.now()}/>);
    }

    return (
      <div>
          <>
          {!this.state.enablePostOffer &&
            <>
              <Route path="/" component={CreateAccount} />
            </>

          }
          {this.state.enablePostOffer &&
            <>
            <Route path="/user/myoffers/" component={MyOffers} />
            <Route path="/user/moffers/" component={MatchingOffers} />
            <Route path="/user/browseoffers/" component={BrowseOffers} />
            <Route path="/offer/details" component={OfferDetails} />
            <Route exact path="/" component={BrowseOffers} key={Date.now()} />

            <Route path="/user/rates/" component={Rates} />
            <Route path="/user/createaccount/" component={CreateAccount} />
            <Route path="/user/postoffer/" component={PostOffer} />
            <Route path="/user/transactions/" component={TransactionDetails} />
            <Route path="/offer/counteroffers/" component={MyCounterOffers} />
            <Route path="/user/transHistory" component={TransactionHistory} />
            <Route path="/user/reports/" component={SystemReport} />


            

            </>

          }

                      <Route path="/logout/" component={Logout} />


        </>






      </div>
    );
  }
}

// Export The Main Component
export default Main;
