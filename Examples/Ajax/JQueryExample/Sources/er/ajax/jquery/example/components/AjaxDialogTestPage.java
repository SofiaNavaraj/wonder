package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimestamp;

public class AjaxDialogTestPage extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AjaxDialogTestPage(WOContext context) {
        super(context);
    }

	public NSTimestamp now() {
		return new NSTimestamp();
	}

}