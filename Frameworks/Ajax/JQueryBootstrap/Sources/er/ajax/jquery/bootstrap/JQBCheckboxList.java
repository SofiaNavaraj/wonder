package er.ajax.jquery.bootstrap;

import com.webobjects.appserver.WOContext;
import com.webobjects.woextensions.WXCheckboxList;

public class JQBCheckboxList extends WXCheckboxList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQBCheckboxList(WOContext context) {
        super(context);
    }

	public String containerClassName() {
		return valueForStringBinding("containerClassName", "checkbox");
	}

}