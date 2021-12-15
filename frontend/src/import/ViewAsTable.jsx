import React from "react";
import CatsManager from "../list/CatsManager";
import PageRoot from "../app/PageRoot";
import {Link} from "react-router-dom";
import FiltersConfig from "../filters/filters-config.json";
import catsManager from "../list/CatsManager";

class ViewAsTable extends React.Component {
    constructor(p) {
        super(p);
        CatsManager.reloadFiltered();
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

    _buildCat(cat, index) {
        let characteristics = cat.characteristics;
        let stats = cat.stats;
        return <tr>
            <td>{index + 1}</td>
            <td><Link to={`/cats/${cat.id}`}>{cat.name}</Link></td>
            {
                FiltersConfig.filters.map((f) => <td>{characteristics[f.id]}</td>)
            }
            {
                FiltersConfig.vitalStats.map((f) => <td>{stats[f.id]}</td>)
            }
        </tr>
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

        return <div>
            <PageRoot>
                <h2 className="uk-margin-large-top">Found results</h2>
                <div className="uk-margin-bottom">
                    <Link to="/">View as list</Link>
                </div>
            </PageRoot>
            <div className="uk-margin-large-left uk-margin-large-right">
                <table className="center uk-table">
                    <thead className="uk-text-bold">
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        {
                            FiltersConfig.filters.map((f) => <td>{f.localized}</td>)
                        }
                        {
                            FiltersConfig.vitalStats.map((f) => <td>{f.localized}</td>)
                        }
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.cats.map((cat, index) => {
                            return this._buildCat(cat, index);
                        })
                    }
                    </tbody>
                </table>
            </div>
        </div>
    }
}

export default ViewAsTable;