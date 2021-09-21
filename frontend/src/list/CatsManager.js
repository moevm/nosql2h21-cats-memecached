import AllCatsConfig from './all-cats.json';
import CatModel from "./CatModel";
import Notifier from "../utils/Notifier";
import FiltersManager from "../filters/manager";

class CatsManager extends Notifier {
  constructor() {
    super();
    this._currentSession = 0;
    this.loading = false;
    this.cats = [];
    this.reload();
  }

  async reload() {
    this._currentSession++;
    let session = this._currentSession;
    this.loading = true;
    this.notifyListeners();

    // TODO will be used in requests
    let params = FiltersManager.toUrlParameters();
    // imitating request delay
    await new Promise(resolve => setTimeout(resolve,1000));

    let newCats = AllCatsConfig.map(raw => new CatModel(raw))

    if (session !== this._currentSession) {
      console.log("Session became invalid, search results will not be applied");
      return;
    }

    this.cats = newCats;
    this.loading = false;
    this.notifyListeners();
  }
}

export default new CatsManager();