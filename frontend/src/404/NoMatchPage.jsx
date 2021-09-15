import {Link} from "react-router-dom";
import './NoMatch.css';

export default function NoMatchPage() {
  return <div className="no-match-root uk-flex uk-flex-center uk-flex-middle">
        <div className="uk-text-center">
          <h1 className="uk-text-center">
            404
          </h1>
          <span>Go to cats <Link to="/" className="uk-text-center">home</Link></span>
        </div>
  </div>;
}