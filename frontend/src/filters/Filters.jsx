import {Component} from "react";
import LabeledSliderFilter from "./LabeledSliderFilter";
import SearchField from "./SearchField";
import filtersManager from './manager';
import CatsManager from "../list/CatsManager";

class Filters extends Component {
  constructor(props) {
    super(props);
    this._onFilterChanged = () => {
      this.forceUpdate();
    };
  }

  componentDidMount() {
    filtersManager.searchFilter.addListener(this._onFilterChanged);
    filtersManager.rangeFilters.forEach(filter => filter.addListener(this._onFilterChanged));
  }

  componentWillUnmount() {
    filtersManager.searchFilter.removeListener(this._onFilterChanged);
    filtersManager.rangeFilters.forEach(filter => filter.removeListener(this._onFilterChanged));
  }

  render() {
    let filters = [];
    for (let filter of filtersManager.rangeFilters) {
      filters.push(<div key={filter.id}>
        <LabeledSliderFilter
          label={filter.localized}
          min={filter.min}
          max={filter.max}
          value={filter.value}
          onChange={(value) => this._onFilterChange(filter, value)}/>
      </div>);
    }
    return <div>
      <h2 className="uk-margin-large-top">Filters</h2>
      {<div className="uk-form">
        <SearchField value={filtersManager.searchFilter.value}
                     onChange={(e) => this._onSearchChange(filtersManager.searchFilter, e.target.value)}/>
      </div>}
      <div className="uk-flex uk-flex-wrap">
        {filters}
      </div>
      <div>
        <button className="uk-button uk-border-rounded uk-button-primary uk-margin-small-right"
                onClick={() => CatsManager.reloadFiltered()}>Find me
        </button>
        <button className="uk-button uk-border-rounded" onClick={() => {
          filtersManager.resetFilters();
          CatsManager.reloadFiltered();
        }}>Clear
        </button>
      </div>
    </div>;
  }

  _onSearchChange(filter, newValue) {
    filter.value = newValue;
  }

  _onFilterChange(filter, newValue) {
    filter.value = newValue;
  }
}

export default Filters;