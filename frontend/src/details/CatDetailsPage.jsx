import React from "react";
import CatsManager from "../list/CatsManager";
import PageRoot from "../app/PageRoot";
import {Link} from "react-router-dom";
import "./CatDetailsPage.css";
import FiltersConfig from "../filters/filters-config.json";

function CharacteristicsCard(props) {
  return <div className="uk-panel-box uk-panel-box-secondary uk-margin-right uk-margin-bottom">
    {props.name}: {props.value}
  </div>
}

class CatDetailsPage extends React.Component {
  constructor(props) {
    super(props);
    this._loadIfNeed();
  }

  componentDidUpdate(prevProps) {
    if (prevProps?.match?.params?.catId !== this._extractCatId())
      this._loadIfNeed();
  }

  componentDidMount() {
    this.mounted = true;
    this._loadIfNeed();
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  _extractCatId() {
    return this.props.match.params.catId;
  }

  async _loadIfNeed() {
    if (this.state?.loadingId === this._extractCatId() && (this.state?.loading || this.state.model))
      return;

    let currentCatId = this._extractCatId();
    let newState = {
      loading: true,
      loadingId: currentCatId,
    };
    if (this.mounted)
      this.setState(newState);
    else
      // eslint-disable-next-line react/no-direct-mutation-state
      this.state = newState;

    let model = await CatsManager.loadSingle(currentCatId);

    if (currentCatId !== this._extractCatId())
      return;

    if (this.mounted)
      this.setState({
        loading: false,
        loadingId: currentCatId,
        model: model,
      });
  }

  render() {
    if (this.state?.loading)
      return <PageRoot>
        <Link to="/"><i className="uk-icon-angle-left"/> Back to list</Link>
        <h2>Loading your cat...</h2>
      </PageRoot>;

    if (!this.state?.model)
      return <PageRoot>
        <Link to="/">Back to list</Link>
        <h2>Failed to load your cat :(</h2>
      </PageRoot>;

    let model = this.state.model;
    return <PageRoot>
      <Link to="/"><i className="uk-icon-angle-left"/> Back to list</Link>
      <h2>{model.name}</h2>
      <div className="uk-flex">
        <div className="cat-details-image-wrapper uk-flex-item-none uk-margin-right">
          <img src={model.image} className="cat-list-image"/>
        </div>
        <div className="uk-flex-item-auto">
          <p>{model.short_description}</p>
        </div>
      </div>
      <h3>Breed Characteristics:</h3>
      <div className="uk-flex uk-flex-wrap">
        {Object.keys(model.characteristics).map(key => {
          let filter = FiltersConfig.find((e) => e.id === key);
          return <CharacteristicsCard key={key} name={filter?.localized ?? key} value={model.characteristics[key]}/>
        })}
      </div>
      <h3>Vital stats:</h3>
      <div className="uk-flex uk-flex-wrap">
        {Object.keys(model.stats).map(key => {
          return <CharacteristicsCard key={key} name={key} value={model.stats[key]}/>
        })}
      </div>
      <h3>History</h3>
      <p>{model.history}</p>
      <h3>Size</h3>
      <p>{model.size}</p>
      <h3>Personality</h3>
      <p>{model.personality}</p>
      <h3>Health</h3>
      <p>{model.health}</p>
      <h3>Care</h3>
      <p>{model.care}</p>
      <h3>Coat Color And Grooming</h3>
      <p>{model.grooming}</p>
      <h3>Children And Other Pets</h3>
      <p>{model.children}</p>
    </PageRoot>;
  }
}

export default CatDetailsPage;