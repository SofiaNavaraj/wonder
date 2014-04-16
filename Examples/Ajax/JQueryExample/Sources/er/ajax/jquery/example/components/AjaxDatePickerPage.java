package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class AjaxDatePickerPage extends Main {

	private NSTimestamp _date;

	public AjaxDatePickerPage(WOContext context) {
        super(context);
    }

	/**
	 * @return the date
	 */
	public NSTimestamp date() {
		if(_date == null) {
			_date = new NSTimestamp();
		}
		return _date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(NSTimestamp date) {
		this._date = date;
	}

	public WOActionResults selectDate() {
		return context().page();
	}
}