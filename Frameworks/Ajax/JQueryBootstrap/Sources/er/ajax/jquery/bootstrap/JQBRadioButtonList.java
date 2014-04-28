package er.ajax.jquery.bootstrap;

import com.webobjects.appserver.WOContext;
import com.webobjects.woextensions.WXRadioButtonList;

public class JQBRadioButtonList extends WXRadioButtonList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQBRadioButtonList(WOContext context) {
        super(context);
    }

	public String containerClassName() {
		return valueForStringBinding("containerClassName", "radio");
	}

}