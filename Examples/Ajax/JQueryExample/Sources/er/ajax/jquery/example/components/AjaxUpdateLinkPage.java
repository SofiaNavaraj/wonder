package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class AjaxUpdateLinkPage extends Main {
   
	public AjaxUpdateLinkPage(WOContext context) {
        super(context);
    }

	public NSTimestamp now() {
		return new NSTimestamp();
	}

	public String test() {
		System.out.println("I should not be called.");
		return null;
	}
	
	public WOActionResults updateTime() {
		return null;
	}

}