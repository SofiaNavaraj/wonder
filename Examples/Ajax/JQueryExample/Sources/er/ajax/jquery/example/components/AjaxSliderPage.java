package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

public class AjaxSliderPage extends Main {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer _sliderValue;
	private Integer _maxValue;
	private Integer _minValue;
	
	public AjaxSliderPage(WOContext context) {
        super(context);
    }

	/**
	 * @return the sliderValue
	 */
	public Integer sliderValue() {
		if(_sliderValue == null) {
			_sliderValue = 42;
		}
		return _sliderValue;
	}

	/**
	 * @param sliderValue the sliderValue to set
	 */
	public void setSliderValue(Integer sliderValue) {
		this._sliderValue = sliderValue;
	}

	public WOActionResults update() {
		return null;
	}

	/**
	 * @return the maxValue
	 */
	public Integer maxValue() {
		if(_maxValue == null) {
			_maxValue = 84;
		}
		return _maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(Integer maxValue) {
		this._maxValue = maxValue;
	}

	public Integer minValue() {
		if(_minValue == null) {
			_minValue = 21;
		}
		return _minValue;
	}

	public void setMinValue(Integer minValue) {
		this._minValue = minValue;
	}
}