import {Component} from "react";
import LabeledSliderFilter from "./LabeledSliderFilter";
import SearchField from "./SearchField";
import filtersManager from './manager';

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
      filters.push(<div>
        <LabeledSliderFilter
          key={filter.id}
          label={filter.localized}
          min={filter.min}
          max={filter.max}
          value={filter.value}
          onChange={(value) => this._onFilterChange(filter, value)}/>
      </div>);
    }
    return <div>
      <h2 className="uk-margin-top">Filters</h2>
      <div className="uk-form">
        <SearchField value={filtersManager.searchFilter.value}
                     onChange={(e) => this._onSearchChange(filtersManager.searchFilter, e.target.value)}/>
      </div>
      <div className="uk-flex uk-flex-wrap">
        {filters}
      </div>
      <div>
        <button className="uk-button uk-border-rounded uk-button-primary uk-margin-small-right"
                onClick={() => console.log("I wasn't found")}>Find me
        </button>
        <button className="uk-button uk-border-rounded" onClick={() => filtersManager.resetFilters()}>Clear</button>
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