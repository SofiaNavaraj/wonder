package er.ajax.jquery.utils;

import org.apache.commons.lang.StringEscapeUtils;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.JQAjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.components.ERXStatelessComponent;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQSelect2 extends ERXStatelessComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _id;

	public JQSelect2(WOContext context) {
        super(context);
    }

	@Override
	public void reset() {
		super.reset();
		_id = null;
	}

	@Override
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		super.appendToResponse(aResponse, aContext);
		addRequiredWebResources(aResponse, aContext);
	}

	protected void addRequiredWebResources(WOResponse aResponse, WOContext aContext) {
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", "javascript/plugins/select2/select2.js");
		if(includeStylesheet()) {
			JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQuery", "javascript/plugins/select2/select2.css");
		}
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
	}

	public boolean includeStylesheet() {
		return booleanValueForBinding("includeStylesheet", true);
	}
	
	/**
	 * @return the id
	 */
	public String id() {
		if(_id == null) {
			_id = valueForStringBinding("id", null);
		}
		return _id;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private NSDictionary _options() {
		NSMutableArray ajaxOptionsArray = new NSMutableArray();
		ajaxOptionsArray.addObject(new JQAjaxOption("width", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("minimumInputLength", JQAjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("maximumInputLength", JQAjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("minimumResultsForSearch", JQAjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("maximumSelectionSize", JQAjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("placeholder", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("placeholderOption", JQAjaxOption.FUNCTION));
		ajaxOptionsArray.addObject(new JQAjaxOption("separator", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("allowClear", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("closeOnSelect", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("openOnEnter", JQAjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("id", JQAjaxOption.FUNCTION_1));
		ajaxOptionsArray.addObject(new JQAjaxOption("matcher", JQAjaxOption.FUNCTION_3));
		ajaxOptionsArray.addObject(new JQAjaxOption("sortResults", JQAjaxOption.FUNCTION_3));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatSelection", JQAjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatResult", JQAjaxOption.FUNCTION_3));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatResultCssClass", JQAjaxOption.FUNCTION_1));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatNoMatches", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatSearching", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatInputTooShort", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatInputTooLong", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatSelectionTooBig", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("formatLoadMore", JQAjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("createSearchChoice", JQAjaxOption.FUNCTION_1));

		NSMutableDictionary options = JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, this);
		return options;		
	}
	
	public String options() {
		return StringEscapeUtils.escapeHtml(ERXPropertyListSerialization.jsonStringFromPropertyList(_options()));
	}

	public boolean hasSelectedValueBinding() {
		return hasBinding("selectedValue");
	}

	public boolean hasSelectionBinding() {
		return hasBinding("selection");
	}

	public boolean hasSelectionsBinding() {
		return hasBinding("selections");
	}	
	
}