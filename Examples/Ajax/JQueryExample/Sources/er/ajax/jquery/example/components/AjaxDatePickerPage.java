package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class AjaxDatePickerPage extends Main {
    private NSTimestamp date;

	public AjaxDatePickerPage(WOContext context) {
        super(context);
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

	public WOActionResults selectDate() {
		return context().page();
	}
}