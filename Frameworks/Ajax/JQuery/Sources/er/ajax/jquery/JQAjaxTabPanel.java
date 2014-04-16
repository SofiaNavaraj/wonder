package er.ajax.jquery;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXTabPanel;
import er.extensions.foundation.ERXValueUtilities;

public class JQAjaxTabPanel extends ERXTabPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQAjaxTabPanel(WOContext context) {
        super(context);
    }
    
    public boolean dontSubmitForm() {
    	return !useFormSubmit();
    }

	private boolean useFormSubmit() {
		return ERXValueUtilities.booleanValue(valueForBinding("useFormSubmit"));
	}

	public boolean isDisabled() {
		return currentTab.equals(selectedTab());
	}

	public String tabContainerClassNames() {
		return valueForStringBinding("tabContainerClassNames", "nav nav-tabs");
	}

	public String tabClassName() {
		return currentTab == selectedTab() ? selectedTabClassName() : null;
	}

	private String selectedTabClassName() {
		return valueForStringBinding("selectedTabClassNames", "active");
	}

	public String tabWrapperComponentName() {
		return valueForStringBinding("tabWrapperComponentName", "JQEmptyPageWrapper");
	}

	public String contentWrapperComponentName() {
		return valueForStringBinding("contentWrapperComponentName", "JQEmptyPageWrapper");
	}

	public String formName() {
		return valueForStringBinding("formName", null);
	}

	public String delegate() {
		return valueForStringBinding("delegate", useFormSubmit() ? "asb"  : null);
	}


} 