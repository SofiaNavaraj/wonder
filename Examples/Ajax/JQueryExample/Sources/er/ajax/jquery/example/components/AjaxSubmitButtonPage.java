package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.ajax.jquery.JQAjaxUtils;

@SuppressWarnings("serial")
public class AjaxSubmitButtonPage extends Main {

	private String _guestName;

	public AjaxSubmitButtonPage(WOContext context) {
        super(context);
    }

	public void appendToResponse(WOResponse response, WOContext context) {
		super.appendToResponse(response, context);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "app", "js/smartwidgets/jarvis.widget.js");
	}
	
	/**
	 * @return the guestName
	 */
	public String guestName() {
		return _guestName;
	}

	/**
	 * @param guestName the guestName to set
	 */
	public void setGuestName(String guestName) {
		this._guestName = guestName;
	}

	public WOActionResults updateName() {
		return null;
	}

	public WOActionResults resetName() {
		setGuestName(null);
		return null;
	}

}