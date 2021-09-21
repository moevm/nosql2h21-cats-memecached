import React from "react";
import catsManager from "./CatsManager";
import CatItem from "./CatItem";
import './CatsList.css';

class CatsList extends React.Component {
  constructor(props) {
    super(props);
    this._onCatsChanged = () => {
      let newState = {cats: catsManager.cats, loading: catsManager.loading};
      if (this._mounted)
        this.setState(newState);
      else
        this.state = newState;
    };
    this.state = {cats: catsManager.cats, loading: catsManager.loading};
    this._mounted = false;
    this._onCatsChanged();
  }

  componentDidMount() {
    this._mounted = true;
    catsManager.addListener(this._onCatsChanged);
  }

  componentWillUnmount() {
    this._mounted = false;
    catsManager.removeListener(this._onCatsChanged);
  }

  render() {
    if (this.state.loading)
      return <div>
        <h2 className="uk-margin-large-top">Looking for your cat...</h2>
      </div>;

    let catsList = this.state.cats.map((catModel, index) => <div className="uk-width-1-2 item-wrapper">
      <CatItem key={index} model={catModel}/>
    </div>);
    return <div>
      <h2 className="uk-margin-large-top">Found results</h2>
      <div className="uk-flex uk-flex-wrap">
        {catsList}
      </div>
    </div>
  }
}

export default CatsList;