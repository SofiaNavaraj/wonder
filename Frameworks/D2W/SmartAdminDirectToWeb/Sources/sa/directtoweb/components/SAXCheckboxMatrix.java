package sa.directtoweb.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.ajax.jquery.JQAjaxUtils;
import er.extensions.components.ERXCheckboxMatrix;

public class SAXCheckboxMatrix extends ERXCheckboxMatrix {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SAXCheckboxMatrix(WOContext context) {
        super(context);
    }
	

    
    @Override
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		super.appendToResponse(aResponse, aContext);
		addRequiredWebResources(aResponse, aContext);
	}



	protected void addRequiredWebResources(WOResponse aResponse,
			WOContext aContext) {
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "SmartAdminDirectToWeb", "javascript/plugins/CheckboxScript.js");
	}



	public String onClick() {
        return "SAXCheckboxMatrix.checkAll(this.form, '" + wrapperElementID + "', this)";
    }
}