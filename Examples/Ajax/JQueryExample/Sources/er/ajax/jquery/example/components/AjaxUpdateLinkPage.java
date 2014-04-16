package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class AjaxUpdateLinkPage extends Main {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AjaxUpdateLinkPage(WOContext context) {
        super(context);
    }

	public NSTimestamp now() {
		return new NSTimestamp();
	}
	
	public WOActionResults updateTime() {
		return null;
	}

}