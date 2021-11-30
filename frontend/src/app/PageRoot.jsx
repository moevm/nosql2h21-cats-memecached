import './PageRoot.css';

export default function PageRoot(props) {
  return <div className="limited-container">
    {props.children}
  </div>;
}