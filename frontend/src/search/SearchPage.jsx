import React from "react";
import Filters from "../filters/Filters";

class SearchPage extends React.Component{
  render() {
    return <div>
      <h1 className="uk-margin-large-top">Cats</h1>
      <Filters/>
    </div>;
  }
}

export default SearchPage;