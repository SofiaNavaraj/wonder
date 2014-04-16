package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.appserver.WOActionResults;

public class PeriodicalUpdaterPage extends Main {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int counter;
	private boolean display = false;
	public Boolean _showDate;

	public PeriodicalUpdaterPage(WOContext context) {
        super(context);
        counter = 0;
	}
	
	@Override
	public void awake() {
		super.awake();
		_showDate = false;
	}
	
	public Boolean showDate() {
		return _showDate;
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

	public WOActionResults changeShowDate() {
		_showDate = true;
		return null;
	}

}