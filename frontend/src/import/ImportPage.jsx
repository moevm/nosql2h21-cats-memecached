import React from "react";
import PageRoot from "../app/PageRoot";
import AppConfig from "../config.json";
import {Link} from "react-router-dom";

class ImportPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            text: null,
            count: 0,
            error: null,
            message: null,
        };
    }

    selectFile(e) {
        e.preventDefault()
        const reader = new FileReader()
        reader.onload = async (e) => {
            let text = e.target.result.toString()
            try {
                let parsed = JSON.parse(text);
                this.setState({
                    ...this.state,
                    text: text,
                    count: parsed.length,
                    error: null,
                    message: `Ready to upload ${parsed.length} cats`,
                });
            } catch (e) {
                console.log("Can not parse file content");
                console.log(e);
                this.setState({
                    ...this.state,
                    text: null,
                    count: 0,
                    error: "Can not parse file content. " + e
                });
            }
        };
        reader.readAsText(e.target.files[0])
    }

    async upload(e) {
        e.preventDefault();
        if (this.state.count <= 0) {
            this.setState({
                ...this.state,
                error: "Invalid data",
                message: null,
            });
            return;
        }

        try {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: this.state.text,
            };
            let url = new URL("/cats/import", AppConfig.serverUrl).href;
            let result = await fetch(url, requestOptions);
            if (result.ok) {
                this.setState({
                    ...this.state,
                    error: null,
                    message: "Successful upload",
                    count: 0,
                    text: null,
                });
            } else {
                this.setState({
                   ...this.state,
                   message: null,
                   error: `Something gone wrong: ${await result.text()}`,
                });
            }
        } catch (e) {
            this.setState({
                ...this.state,
                error: "Something gone wrong...",
                message: null,
            });
        }
    }

    render() {
        return <PageRoot>
            <Link to="/"><i className="uk-icon-angle-left"/> Back</Link>
            <h2>Import data</h2>
            <form onSubmit={(e) => this.upload(e)}>
                <input type="file" onChange={(e) => this.selectFile(e)}/>
                <input type="submit" value="Import"/>
                <p>{this.state.message ?? ""}</p>
                <p style={{color: "red"}}>{this.state.error ?? ""}</p>
            </form>
        </PageRoot>;
    }
}

export default ImportPage;