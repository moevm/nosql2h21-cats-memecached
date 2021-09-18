class CatModel {
  constructor(raw) {
    this.name = raw['breed_name'];
    this.care = raw['care'];
    this.characteristics = raw['characteristics'];
    this.children = raw['children_and_pets'];
    this.grooming = raw['color_and_grooming'];
    this.health = raw['health'];
    this.history = raw['history'];
    this.personality = raw['personality'];
    this.image = raw['round_img_url'];
    this.short_description = raw['short_description'];
    this.size = raw['size'];
    this.stats = raw['vital_stats'];
  }
}

export default CatModel;