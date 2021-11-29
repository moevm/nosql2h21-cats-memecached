import React from "react";
import Filters from "../filters/Filters";
import PageRoot from "../app/PageRoot";
import CatsList from "../list/CatsList";
import {Link} from "react-router-dom";

class SearchPage extends React.Component{
  render() {
    return <PageRoot>
      <h1>Cats</h1>
      <div>
        <Link to="/import">Import</Link>
      </div>
      <Filters/>
      <CatsList/>
    </PageRoot>;
  }
}

export default SearchPage;