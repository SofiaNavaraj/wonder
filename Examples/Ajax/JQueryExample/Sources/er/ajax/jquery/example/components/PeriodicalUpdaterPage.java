package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class PeriodicalUpdaterPage extends Main {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int counter;
	private boolean display = false;

	public PeriodicalUpdaterPage(WOContext context) {
        super(context);
        counter = 0;
	}
	
	public NSTimestamp now() {
		return new NSTimestamp();
	}

	/**
	 * @return the display
	 */
	public boolean display() {
		if(! display) {
			display = true;
			return false;
		}
		return display;
	}

}