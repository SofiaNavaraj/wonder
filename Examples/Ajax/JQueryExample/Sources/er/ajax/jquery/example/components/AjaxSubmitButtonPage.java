package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

public class AjaxSubmitButtonPage extends Main {

	private String _guestName;

	public AjaxSubmitButtonPage(WOContext context) {
        super(context);
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

}