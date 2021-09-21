import Slider, {createSliderWithTooltip} from 'rc-slider';
import './LabeledSliderFilter.css';

const Range = createSliderWithTooltip(Slider.Range);

export default function LabeledSliderFilter(props) {
  return <div className=" labeled-slider-wrapper uk-text-center uk-display-inline-block">
    <label>
      {props.label}
      <Range min={props.min}
             max={props.max}
             value={props.value}
             className="slider"
             onChange={props.onChange}/>
    </label>
  </div>;
}