import React from "react";
import Filters from "../filters/Filters";
import PageRoot from "../app/PageRoot";
import CatsList from "../list/CatsList";

class SearchPage extends React.Component{
  render() {
    return <PageRoot>
      <h1>Cats</h1>
      <Filters/>
      <CatsList/>
    </PageRoot>;
  }
}

export default SearchPage;