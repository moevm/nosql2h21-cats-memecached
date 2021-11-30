import React from "react";
import PageRoot from "../app/PageRoot";
import {Link, withRouter} from "react-router-dom";
import FiltersConfig from "../filters/filters-config.json";
import AppConfig from "../config.json";

const vitalStats = [
    {id: "length", localized: "Length"},
    {id: "lifeSpan", localized: "Life span"},
    {id: "origin", localized: "Origin"},
    {id: "weight", localized: "Weight"}
]

class AddCatPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};

        this.handleInputChange = this.handleInputChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            ...this.state,
            [name]: value,
        });
    }

    async onSubmit(e) {
        e.preventDefault()

        try {
            let data = {
                breedName: this.state.breedName,
                care: this.state.care,
                characteristics: FiltersConfig.filters.reduce((arr, e) => {arr[e.id] = parseInt(this.state[e.id]); return arr}, {}),
                childrenAndPets: this.state.childrenAndPets,
                colorAndGrooming: this.state.colorAndGrooming,
                health: this.state.health,
                history: this.state.history,
                personality: this.state.personality,
                roundImgUrl: this.state.roundImgUrl,
                shortDescription: this.state.shortDescription,
                size: this.state.size,
                vitalStats: vitalStats.reduce((arr, e) => {arr[e.id] = this.state[e.id]; return arr}, {}),
            };
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data),
            };
            let url = new URL("/cats", AppConfig.serverUrl).href;
            let result = await fetch(url, requestOptions);
            if (result.ok) {
                this.props.history.push(`/cats/${this.state.breedName}`)
            } else {
                alert(`Something gone wrong: ${await result.text()}`);
                console.log(`Something gone wrong: ${await result.text()}`)
            }
        } catch (e) {
            alert(e)
            console.log(e)
        }
    }

    render() {
        return <PageRoot>
            <Link to="/"><i className="uk-icon-angle-left"/> Back</Link>
            <form onSubmit={this.onSubmit}>
                <div>
                    <label>Name: <br/><input required name="breedName" value={this.state.breedName ?? ""}
                                             onChange={this.handleInputChange}/></label>
                </div>
                <div>
                    <label>Image url: <br/><input required name="roundImgUrl" value={this.state.roundImgUrl ?? ""}
                                                  onChange={this.handleInputChange}/></label>
                </div>
                <div>
                    <label>Short description: <br/><textarea required name="shortDescription"
                                                             onChange={this.handleInputChange} value={this.state.shortDescription ?? ""}></textarea></label>
                </div>
                <h3>Characteristics</h3>
                <div>
                    {
                        FiltersConfig.filters.map((e) => {
                            return <label>{e.localized}: <input required type="number" min={e.min} max={e.max} name={e.id} value={this.state[e.id] ?? ""}
                                                                     onChange={this.handleInputChange}/><br/></label>
                        })
                    }
                </div>
                <h3>Vital stats</h3>
                <div>
                    {
                        vitalStats.map((stat) => {
                            return <label>{stat.localized}: <input required type="text" name={stat.id} value={this.state[stat.id] ?? ""}
                                                                onChange={this.handleInputChange}/><br/></label>
                        })
                    }
                </div>
                <div>
                    <label>History: <br/><textarea required name="history"
                                                             onChange={this.handleInputChange} value={this.state.history ?? ""}></textarea></label>
                </div>
                <div>
                    <label>Size: <br/><textarea required name="size"
                                                   onChange={this.handleInputChange} value={this.state.size ?? ""}></textarea></label>
                </div>
                <div>
                    <label>Personality: <br/><textarea required name="personality"
                                                   onChange={this.handleInputChange} value={this.state.personality ?? ""}></textarea></label>
                </div>
                <div>
                    <label>Health: <br/><textarea required name="health"
                                                       onChange={this.handleInputChange} value={this.state.health ?? ""}></textarea></label>
                </div>
                <div>
                    <label>Care: <br/><textarea required name="care"
                                                  onChange={this.handleInputChange} value={this.state.care ?? ""}></textarea></label>
                </div>
                <div>
                    <label>Coat Color And Grooming: <br/><textarea required name="colorAndGrooming"
                                                onChange={this.handleInputChange} value={this.state.colorAndGrooming ?? ""}></textarea></label>
                </div>
                <div>
                    <label>Children And Other Pets: <br/><textarea required name="childrenAndPets"
                                                                   onChange={this.handleInputChange} value={this.state.childrenAndPets ?? ""}></textarea></label>
                </div>
                <div>
                    <input type="submit" value="Add"/>
                </div>
            </form>
        </PageRoot>;
    }
}

export default withRouter(AddCatPage);