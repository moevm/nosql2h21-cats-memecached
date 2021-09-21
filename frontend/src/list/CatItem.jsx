import './Cat.css';
import {Link} from "react-router-dom";

export default function CatItem(props) {
  let model = props.model;
  return <Link to={`/cats/${model.name}`} className="uk-panel uk-panel-box uk-panel-hover uk-border-rounded uk-margin">
    <div className="uk-flex">
      <div className="cat-list-image-wrapper uk-flex-item-none uk-margin-right">
        <img src={model.image} className="cat-list-image"/>
      </div>
      <div className="uk-flex-item-auto">
        <h3 className="uk-panel-title">{model.name}</h3>
        <p>{model.short_description}</p>
      </div>
    </div>
  </Link>
}