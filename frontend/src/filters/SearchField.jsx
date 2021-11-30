import './SearchField.css';

export default function SearchField(props) {
  return <div className="uk-form-icon">
    <i className="uk-icon-search"/>
    <input type="text"
           value={props.value}
           placeholder="Cat's name or anything else"
           className="search-input"
           onChange={props.onChange}/>
  </div>
}