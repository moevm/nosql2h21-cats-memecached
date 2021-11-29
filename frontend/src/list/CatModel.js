class CatModel {
  constructor(raw, id = null) {
    if (raw['breedName'] == null)
      throw Error("AAAAAAAA breedName is null!");

    if (id != null)
      this.id = id;
    else
      this.id = raw['breedName'].replaceAll(" ", "_");

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