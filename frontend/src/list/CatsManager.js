import CatModel from "./CatModel";
import Notifier from "../utils/Notifier";
import FiltersManager from "../filters/manager";
import AppConfig from "../config.json";

class CatsManager extends Notifier {
    constructor() {
        super();
        this._currentSession = 0;
        this.loading = false;
        this.cats = [];
    }

    async loadSingle(catId) {
        catId = catId.replaceAll(" ", "_");
        try {
            let url = new URL(`/cats/${catId}`, AppConfig.serverUrl).href;
            let response = await fetch(url);
            response = await response.json();
            console.log("Cat's data loaded");
            console.log(response);
            return new CatModel(response, catId);
        } catch (e) {
            console.log(e);
            return null;
        } finally {
            this.notifyListeners();
        }
    }

    async reloadAll() {
        this._currentSession++;
        let session = this._currentSession;
        try {
            let url = new URL(`/cats`, AppConfig.serverUrl).href;
            let response = await fetch(url);
            response = await response.json();
            if (this._currentSession !== session) return;
            this._applyCats(response)
            console.log("Cats loaded")
            console.log(this.cats);
        } catch (e) {
            console.log(e);
        } finally {
            if (this._currentSession === session)
                this.notifyListeners();
        }
    }

    async reloadFiltered() {
        this._currentSession++;
        let session = this._currentSession;
        try {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: FiltersManager.toJsonParameters(),
            };
            let url = new URL("/cats/filter", AppConfig.serverUrl).href;
            let response = await fetch(url, requestOptions);
            response = await response.json();
            if (this._currentSession !== session) return;
            this._applyCats(response);
        } catch (e) {
            console.log(e);
        } finally {
            this.notifyListeners();
        }
    }

    _applyCats(json) {
        this.cats = json.reduce(function (arr, raw) {
            try {
                arr.push(new CatModel(raw));
            } catch (e) {
                console.log(`Failed to parse cat ${raw}`);
            }
            return arr;
        }, []);
        this.cats.sort(function (a, b) {
            return a.id > b.id ? 1 : a.id < b.id ? -1 : 0;
        });
    }
}

export default new CatsManager();