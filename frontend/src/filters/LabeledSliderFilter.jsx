import Slider, {createSliderWithTooltip} from 'rc-slider';
import './LabeledSliderFilter.css';

const Range = createSliderWithTooltip(Slider.Range);

export default function LabeledSliderFilter(props) {
  console.log(props);
  return <div className=" labeled-slider-wrapper uk-text-center uk-display-inline-block">
    <label>
      {props.label}
      <Range min={props.min}
             max={props.max}
             defaultValue={[props.min, props.max]}
             className="slider"/>
    </label>
  </div>;
}