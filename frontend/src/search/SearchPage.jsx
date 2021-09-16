import React from "react";
import Filters from "../filters/Filters";
import PageRoot from "../app/PageRoot";

class SearchPage extends React.Component{
  render() {
    return <PageRoot>
      <h1>Cats</h1>
      <Filters/>
    </PageRoot>;
  }
}

export default SearchPage;