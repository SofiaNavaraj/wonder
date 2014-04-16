package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class DateComponent extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateComponent(WOContext context) {
        super(context);
    }
	
	@Override
	public boolean isStateless() {
		return true;
	}

	public NSTimestamp now() {
		return new NSTimestamp();
	}

	public boolean shouldDisplay() {
		return true;
	}
	
}