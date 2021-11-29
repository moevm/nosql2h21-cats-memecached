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

  /*async reload() {
    this._currentSession++;
    let session = this._currentSession;
    this.loading = true;
    this.notifyListeners();

    let newCats = await this._loadAllInner();

    if (session !== this._currentSession) {
      console.log("Session became invalid, search results will not be applied");
      return;
    }

    if (newCats)
      this.cats = newCats;

    this.loading = false;
    this.notifyListeners();
  }*/

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
    try {
      let url = new URL(`/cats`, AppConfig.serverUrl).href;
      let response = await fetch(url);
      response = await response.json();
      this.cats = response.reduce(function (arr, raw) {
        try {
          arr.push(new CatModel(raw));
        } catch (e) {
          console.log(`Failed to parse cat ${raw}`);
        }
        return arr;
      }, []);
      this.cats.sort(function (a, b) {
        return a.id > b.id ? 1 : a.id < b.id ? -1 : 0;
      })
      console.log("Cats loaded")
      console.log(this.cats);
    } catch (e) {
      console.log(e);
      return null;
    } finally {
      this.notifyListeners();
    }
  }

  async reloadFiltered() {
    try {
      // TODO add filters
      await this.reloadAll();
    } catch (e) {
      console.log(e);
      return null;
    } finally {
      //TODO this.notifyListeners();
    }
  }
}

export default new CatsManager();