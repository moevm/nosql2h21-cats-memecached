import {Component} from "react";
import Config from "./filters-config.json";
import LabeledSliderFilter from "./LabeledSliderFilter";
import SearchField from "./SearchField";

class Filters extends Component {
  render() {
    let filters = [];
    for (let filter of Config.filters) {
      filters.push(<div>
        <LabeledSliderFilter key={filter.id}
                             label={filter.localized}
                             min={filter.min}
                             max={filter.max}/>
      </div>);
    }
    return <div>
      <h2 className="uk-margin-top">Filters</h2>
      <div className="uk-form">
        <SearchField/>
      </div>
      <div className="uk-flex uk-flex-wrap">
        {filters}
      </div>
    </div>;
  }
}

export default Filters;