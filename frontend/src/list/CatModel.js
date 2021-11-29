class CatModel {
  constructor(raw, id) {
    this.id = id;
    this.name = raw['breedName'];
    this.care = raw['care'];
    this.characteristics = raw['characteristics'];
    this.children = raw['childrenAndPets'];
    this.grooming = raw['colorAndGrooming'];
    this.health = raw['health'];
    this.history = raw['history'];
    this.personality = raw['personality'];
    this.image = raw['roundImgUrl'];
    this.short_description = raw['shortDescription'];
    this.size = raw['size'];
    this.stats = raw['vitalStats'];
  }
}

export default CatModel;