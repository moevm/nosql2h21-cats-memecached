import CatsManager from "../list/CatsManager";
import catsManager from "../list/CatsManager";
import {Link} from "react-router-dom";
import FiltersConfig from "../filters/filters-config.json";
import PageRoot from "../app/PageRoot";
import React from "react";
import {CartesianGrid, Line, Tooltip, XAxis, LineChart, YAxis, Label} from "recharts";

class ViewAsGraph extends React.Component {
    constructor(p) {
        super(p);
        CatsManager.reloadFiltered();
        this._onCatsChanged = () => {
            let newState = {...this.state, cats: catsManager.cats, loading: catsManager.loading};
            if (this._mounted)
                this.setState(newState);
            else
                this.state = newState;
        };
        this.state = {
            cats: catsManager.cats,
            loading: catsManager.loading,
            x: FiltersConfig.filters[0],
            y: FiltersConfig.filters[1],
        };
        this._mounted = false;
        this._onCatsChanged();
    }

    componentDidMount() {
        this._mounted = true;
        catsManager.addListener(this._onCatsChanged);
    }

    componentWillUnmount() {
        this._mounted = false;
        catsManager.removeListener(this._onCatsChanged);
    }

    _buildSelect(value, onChange) {
        return <select value={value.id} onChange={onChange}>
            {
                FiltersConfig.filters.map((f) => {
                    return <option value={f.id}>{f.localized}</option>
                })
            }
        </select>
    }

    render() {
        if (this.state.loading)
            return <div>
                <h2 className="uk-margin-large-top">Looking for your cat...</h2>
            </div>;

        let cats = this.state.cats;
        let x = this.state.x;
        let y = this.state.y;
        let data = [
            1,2,3,4,5
        ].filter((value) => {
            return cats.some((cat) => cat.characteristics[x.id] === value)
                && cats.some((cat) => cat.characteristics[y.id]);
        }).map((xValue) => {
            let yCount = 0;
            let yTotal = 0;
            cats.forEach((cat) => {
               if (cat.characteristics[x.id] === xValue && cat.characteristics[y.id]) {
                   yCount++;
                   yTotal += cat.characteristics[y.id];
               }
            });
            let res = {};
            res[x.id] = xValue;
            if (yCount > 0)
                res[y.id] = yTotal / yCount;
            return res;
        });
        console.log(data);
        return <div>
            <PageRoot>
                <h2 className="uk-margin-large-top">Found results</h2>
                <div>
                    <Link to="/">View as list</Link>
                    <span> | </span>
                    <Link to="/table">View as table</Link>
                </div>
            </PageRoot>
            <PageRoot>
                <div className="uk-margin-bottom">
                    <label>
                        x: {
                        this._buildSelect(x, (e) => {
                            this.setState({
                                ...this.state,
                                x: FiltersConfig.filters.find((f) => f.id === e.target.value)
                            })
                        })
                    }
                    </label>
                    <br/>
                    <label>
                        y: {
                        this._buildSelect(y, (e) => {
                            this.setState({
                                ...this.state,
                                y: FiltersConfig.filters.find((f) => f.id === e.target.value)
                            })
                        })
                    }
                    </label>
                </div>
                <LineChart width={600} height={300} data={data}>
                    <Line type="monotone" stroke="#8884d8" dataKey={y.id}/>
                    <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                    <XAxis dataKey={x.id} name={x.localized}>
                        <Label value={x.localized} offset={0} position="insideBottom" />
                    </XAxis>
                    <YAxis  name={y.localized}>
                        <Label value={y.localized} angle={-90} position="insideLeft"   />
                    </YAxis>
                </LineChart>
            </PageRoot>
        </div>
    }
}

export default ViewAsGraph;