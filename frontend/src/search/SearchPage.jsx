import React from "react";
import Filters from "../filters/Filters";
import PageRoot from "../app/PageRoot";
import CatsList from "../list/CatsList";
import {Link} from "react-router-dom";
import AppConfig from "../config.json";
import ViewAsTable from "../import/ViewAsTable";
import ViewAsGraph from "../import/ViewAsGraph";

class SearchPage extends React.Component{
  async exportAll(e) {
    e.preventDefault()
    let text = "[]";
    try {
      let href = new URL("cats/export", AppConfig.serverUrl).href
      let res = await fetch(href);
      if (res.ok) {
        text = await res.text();
      } else {
        console.log(res)
        alert("Can not download data from server :(")
      }
    } catch (e) {
      console.log(e)
    }
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', "all.json");
    element.style.display = 'none';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  }

  render() {
    let cur = <PageRoot>
      <h1>Cats</h1>
      <div>
        <Link to="/add">Add cat</Link>
        <span> or </span>
        <Link to="/import">Import</Link>
        <span> or </span>
        <a href="/cats/export" download="all.json" onClick={this.exportAll}>Export</a>
        <span> data</span>
      </div>
      <Filters/>
    </PageRoot>;

    if (this.props.asTable)
      return <div>
        {cur}
        <ViewAsTable/>
      </div>
    if (this.props.asGraph)
      return <div>
        {cur}
        <ViewAsGraph/>
      </div>
    return <div>
      {cur}
      <PageRoot>
        <CatsList/>
      </PageRoot>
    </div>
  }
}

export default SearchPage;