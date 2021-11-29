import Config from './filters-config.json';
import Notifier from "../utils/Notifier";

class FilterBase extends Notifier {
  constructor(id) {
    super();
    this.id = id;
  }

  correctValue(value) {
    return value;
  }

  encodeValue(value) {
    return value;
  }

  decodeValue(storedValue) {
    return storedValue;
  }

  get _storeKey() {
    return `filter_${this.id}`;
  }

  getStoredValue() {
    try {
      let encoded = window.sessionStorage.getItem(this._storeKey);
      return this.correctValue(this.decodeValue(encoded));
    } catch (e) {
      console.log(e);
      return this.correctValue(null);
    }
  }

  storeValue(value) {
    try {
      window.sessionStorage.setItem(this._storeKey, this.encodeValue(this.correctValue(value)));
    } catch (e) {
      console.error(e);
    }
  }

  resetValue() {
    this.value = this.correctValue(null);
  }

  get value() {
    return this.getStoredValue();
  }

  set value(value) {
    this.storeValue(value);
    this.notifyListeners();
  }

  toJsonObject() {
    throw Error("Not implemented");
  }
}

class RangeFilter extends FilterBase {
  constructor(id, min, max, localized) {
    super(id);
    this.id = id;
    this.min = min;
    this.max = max;
    this.localized = localized;
  }

  correctValue(value) {
    if (!value || !value.length || value.length !== 2)
      value = [this.min, this.max];
    if (value[0] > value[1])
      value = [value[1], value[0]];
    if (value[0] < this.min)
      value[0] = this.min;
    if (value[1] > this.max)
      value[1] = this.max;
    return value;
  }

  encodeValue(value) {
    return JSON.stringify(value);
  }

  decodeValue(storedValue) {
    return JSON.parse(storedValue);
  }

  toUrlParameters() {
    let value = this.value;
    let minValue = Math.min(...value);
    let maxValue = Math.max(...value);
    let params = [];
    if (minValue !== this.min) params.push(`${this.id}_min=${minValue}`);
    if (maxValue !== this.max) params.push(`${this.id}_max=${maxValue}`);
    return params;
  }

  toJsonObject() {
    let res = {
      min: Math.min(...this.value),
      max: Math.max(...this.value),
      localized: this.id,
    };
    return res;
  }
}

class SearchFilter extends FilterBase {
  constructor() {
    super("search");
  }

  correctValue(value) {
    if (!value)
      value = "";

    return value;
  }

  toUrlParameters() {
    let value = this.value;
    if (value === "") return [];
    return [`search=${value}`];
  }
}

class FiltersManager {
  constructor() {
    this.searchFilter = new SearchFilter();
    this.rangeFilters = Config.filters.map((value) => {
      return new RangeFilter(value.id, value.min, value.max, value.localized);
    });
  }

  resetFilters() {
    this.searchFilter.resetValue();
    this.rangeFilters.forEach(filter => filter.resetValue());
  }

  toJsonParameters() {
    return JSON.stringify({
      filters: this.rangeFilters.map((e) => e.toJsonObject()),
    });
  }

  toUrlParameters() {
    let params = [];
    params.push(...this.searchFilter.toUrlParameters());
    for (let filter of this.rangeFilters)
      params.push(...filter.toUrlParameters());
    return params;
  }
}

export default new FiltersManager();