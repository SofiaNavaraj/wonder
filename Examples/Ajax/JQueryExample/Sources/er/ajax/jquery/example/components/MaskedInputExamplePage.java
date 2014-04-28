package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOActionResults;
import com.webobjects.foundation.NSTimestamp;

public class MaskedInputExamplePage extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String simpleValue;
	private NSTimestamp date;

	public MaskedInputExamplePage(WOContext context) {
        super(context);
    }

	/**
	 * @return the simpleValue
	 */
	public String simpleValue() {
		return simpleValue;
	}

	/**
	 * @param simpleValue the simpleValue to set
	 */
	public void setSimpleValue(String simpleValue) {
		this.simpleValue = simpleValue;
	}

	public WOActionResults submit() {
		return null;
	}

	/**
	 * @return the date
	 */
	public NSTimestamp date() {
		if(date == null) {
			date = new NSTimestamp();
		}
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(NSTimestamp date) {
		this.date = date;
	}
}