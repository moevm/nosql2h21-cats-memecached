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

    let newCats = await this._loadAllInner();

    if (session !== this._currentSession) {
      console.log("Session became invalid, search results will not be applied");
      return;
    }

    if (newCats)
      this.cats = newCats;

    this.loading = false;
    this.notifyListeners();
  }

  async loadSingle(catId) {
    let allCats = await this._loadAllInner();
    return allCats.find(cat => cat.id === catId);
  }

  async _loadAllInner() {
    // TODO will be used in requests
    let params = FiltersManager.toUrlParameters();
    // imitating request delay
    await new Promise(resolve => setTimeout(resolve,1000));

    return AllCatsConfig.map((raw, index) => new CatModel(raw, index.toString()));
  }
}

export default new CatsManager();