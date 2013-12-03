package er.ajax.jquery.bootstrap;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.appserver.ERXWOContext;
import er.extensions.components.ERXStatelessComponent;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQBSelectBox extends ERXStatelessComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _id;
	
	public JQBSelectBox(WOContext context) {
        super(context);
    }

	@Override
	public void reset() {
		super.reset();
		_id = null;
	}
	
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {

		super.appendToResponse(aResponse, aContext);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/plugins/selectpicker/bootstrap-select.js");
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/plugins/selectpicker/bootstrap-select.css");
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.css");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.js");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.wonder.js");

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
	
	public String options() {
		return ERXPropertyListSerialization.jsonStringFromPropertyList(_options());
	}	
	
}