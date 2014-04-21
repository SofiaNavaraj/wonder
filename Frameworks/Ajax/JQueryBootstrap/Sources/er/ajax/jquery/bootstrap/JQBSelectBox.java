package er.ajax.jquery.bootstrap;

import org.apache.commons.lang.StringEscapeUtils;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxOption;
import er.ajax.JQAjaxOption;
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
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "css/bootstrap.min.css");
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/plugins/selectpicker/bootstrap-select.css");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.js");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/plugins/selectpicker/bootstrap-select.js");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.wonder.js");

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private NSDictionary _options() {
		NSMutableArray ajaxOptionsArray = new NSMutableArray();
		ajaxOptionsArray.addObject(new JQAjaxOption("container", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("countSelectedText", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("dropupAuto", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("header", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("hideDisabled", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("selectedTextFormat", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("size", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("showSubtext", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("showIcon", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("showContent", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("style", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("title", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("width",  JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("countSelectedText", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("countSelectedText", JQAjaxOption.STRING));

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
		return  StringEscapeUtils.escapeHtml(ERXPropertyListSerialization.jsonStringFromPropertyList(_options()));
	}	
	
}