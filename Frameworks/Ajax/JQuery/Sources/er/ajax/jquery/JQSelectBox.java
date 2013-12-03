package er.ajax.jquery;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxOption;
import er.extensions.appserver.ERXWOContext;
import er.extensions.components.ERXStatelessComponent;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQSelectBox extends ERXStatelessComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;

	public JQSelectBox(WOContext context) {
        super(context);
    }

	@Override
	public void reset() {
		super.reset();
		_id = null;
	}
	
	/**
	 * @return the id
	 */
	public String id() {

		if(_id == null) {
			_id = (String) valueForBinding("id");
		}
		
		if (_id == null) {
			_id = ERXWOContext.safeIdentifierName(context(), false);
		}
		
		return _id;
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private NSDictionary _options() {
		NSMutableArray ajaxOptionsArray = new NSMutableArray();
		NSMutableDictionary options = AjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, this);
		return options;		
	}
	
	public boolean selectionIsDefined() {
		return hasBinding("selection");
	}

	public boolean selectedValueIsDefined() {
		return hasBinding("selectedValue");
	}
	
	@Override
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
			
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", "javascript/plugins/selectBox/jquery.selectBox.js");

		if(useDefaultCSS()) {
			JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQuery", "javascript/plugins/selectBox/selectBox.css");
		}

		super.appendToResponse(aResponse, aContext);

	}
	
	public boolean useDefaultCSS() {
		return booleanValueForBinding("useDefaultCSS", true);
	}
	
	public String options() {
		return ERXPropertyListSerialization.jsonStringFromPropertyList(_options());
	}	
	

}