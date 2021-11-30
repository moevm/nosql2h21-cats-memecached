class Notifier {
  constructor() {
    this._listeners = new Set();
  }

  addListener(listener) {
    if (typeof listener !== 'function') {
      console.error(`${listener} is not a function`);
      return;
    }

    this._listeners.add(listener);
  }

  removeListener(listener) {
    this._listeners.delete(listener);
  }

  notifyListeners() {
    this._listeners.forEach(listener => listener());
  }
}

export default Notifier;