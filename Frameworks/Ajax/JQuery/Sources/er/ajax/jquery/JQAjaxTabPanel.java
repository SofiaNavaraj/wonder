package er.ajax.jquery;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXTabPanel;
import er.extensions.foundation.ERXValueUtilities;

public class JQAjaxTabPanel extends ERXTabPanel {

	public JQAjaxTabPanel(WOContext context) {
        super(context);
    }
    
    public boolean dontSubmitForm() {
    	return !ERXValueUtilities.booleanValue(valueForBinding("useFormSubmit"));
    }

	public boolean isDisabled() {
		return currentTab.equals(selectedTab());
	}


}