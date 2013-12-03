package er.ajax.jquery.bootstrap;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.ajax.jquery.JQAjaxUtils;
import er.extensions.components.ERXStatelessComponent;

public class JQBButtonDropdown extends ERXStatelessComponent {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQBButtonDropdown(WOContext context) {
        super(context);
    }
	
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		super.appendToResponse(aResponse, aContext);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.css");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.js");
	}

    public String buttonClass() {
    	return stringValueForBinding("dropDownButtonClass", "btn btn-default");
    }
    
	public String dropdownButtonClass() {
		String dropDownButtonClass = buttonClass();
		dropDownButtonClass += "  ";
		dropDownButtonClass += "dropdown-toggle";
		return dropDownButtonClass;
	}

}