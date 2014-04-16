package er.ajax.jquery.ui;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxOption;
import er.ajax.AjaxUtils;
import er.ajax.JQAjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.foundation.ERXPropertyListSerialization;
import er.extensions.foundation.ERXStringUtilities;
import er.extensions.foundation.ERXValueUtilities;

public class JQAutocomplete extends AjaxDynamicElement {

	private static final long serialVersionUID = 1L;

	public JQAutocomplete(String name,
			NSDictionary<String, WOAssociation> associations,
			WOElement children) {
		super(name, associations, children);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void appendToResponse(WOResponse response, WOContext context) {
		// TODO Auto-generated method stub
		super.appendToResponse(response, context);
		WOComponent component = context.component();
		response.appendContentString("<input");
		appendTagAttributeToResponse(response, "type", "input");
		appendTagAttributeToResponse(response, "data-wonder-id", "Autocomplete");
		NSDictionary options = options(component);
		appendTagAttributeToResponse(response, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	
		appendTagAttributeToResponse(response,  "name", context.elementID());
		appendTagAttributeToResponse(response,  "class", valueForBinding("class", component));
		appendTagAttributeToResponse(response,  "style", valueForBinding("style", component));
		String id = stringValueForBinding("id", component);

		if(ERXStringUtilities.stringIsNullOrEmpty(id) == false) {
			appendTagAttributeToResponse(response, "id", id);
		}

		String value = stringValue(component);
		
		appendTagAttributeToResponse(response, "value", value);
		response.appendContentString("/>");
		
	}

	@Override
	public void takeValuesFromRequest(WORequest aRequest, WOContext aContext) {
		// TODO Auto-generated method stub
		_takeValuesFromRequest(aRequest, aContext);
		super.takeValuesFromRequest(aRequest, aContext);
	}

	public void _takeValuesFromRequest(WORequest aRequest, WOContext aContext) {

		WOComponent component = aContext.component();
		String strValue = aRequest.stringFormValueForKey(aContext.elementID());

		if(hasBinding("selection")) {
		
			Object selection = null;
			
			if(strValue != null) {
			
				@SuppressWarnings("unchecked")
				NSArray<Object> values = (NSArray<Object>) valueForBinding("list", component);
				int maxItems = maxItems(component);
				int itemsCount = 0;
				
				for(Enumeration<Object> e = values.objectEnumerator(); e.hasMoreElements() && itemsCount++ < maxItems;) {
					Object value = e.nextElement();
					setValueForBinding(value, "item", component);
					String displayString = displayStringForValue(component, value);
					if (ERXStringUtilities.stringEqualsString(displayString, strValue)) {
						selection = value;
						break;
					}

				}

			}

			setValueForBinding(selection, "selection", component);

		}

		setValueForBinding(strValue, "value", component);
		
	}
	
	
	@SuppressWarnings("rawtypes")
	protected NSDictionary options(WOComponent component) {
		
		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		NSMutableDictionary options = JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component);
		options.takeValueForKey(AjaxUtils.ajaxComponentActionUrl(component.context()), "source");
		return options;
		
	}

	public String stringValue(WOComponent component) {
		String strValue = null;
		if(hasBinding("selection")) {
			Object selection = valueForBinding("selection", component);
			if (selection != null) {
				if (hasBinding("displayString")) {
					setValueForBinding(selection, "item", component);
					strValue = displayStringForValue(component, valueForBinding("value", component));
				}
				else {
					strValue = String.valueOf(selection);
				}
			}
			else
				strValue = (String) valueForBinding("value", component);
		}
		else if (hasBinding("value")) {
			strValue = (String) valueForBinding("value", component);
		}
		return strValue;

	}
	
	protected String displayStringForValue(WOComponent component, Object value) {
		Object displayValue = valueForBinding("displayString", valueForBinding("item", value, component), component);
		String displayString = displayValue == null ? null : displayValue.toString();
		return displayString;
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {

		String fieldValue = context.request().stringFormValueForKey("term");
		WOComponent component = context.component();

		setValueForBinding(fieldValue, "value", component);
		WOResponse response = AjaxUtils.createResponse(request, context);
		
		int maxItems = maxItems(component);
		int itemsCount = 0;
		Object values = valueForBinding("list", component);
		response.appendContentString("[");
		boolean hasPrevious = false;
		if (values instanceof NSArray) {
			for(@SuppressWarnings("unchecked")
				Enumeration<Object> valueEnum = ((NSArray<Object>)values).objectEnumerator(); valueEnum.hasMoreElements() && itemsCount++ < maxItems;) {
					if(hasPrevious) {
						response.appendContentString(",");
					} else {
						hasPrevious = true;
					}
					response.appendContentString("{ \"value\":");
					
					Object value = valueEnum.nextElement();
					setValueForBinding(value, "item", component);
					response.appendContentString("\"" + displayStringForValue(component, value) + "\""); 
					response.appendContentString("}");
			}	
		} else if(values instanceof List) {
			for(@SuppressWarnings("unchecked")
			Iterator<Object> iter = ((List<Object>)values).iterator(); iter.hasNext() && itemsCount++ < maxItems;) {

			}	
		}
		response.appendContentString("]");
		
		return null;
	}

	private int maxItems(WOComponent component) {
		return ERXValueUtilities.intValueWithDefault(valueForBinding("maxItems", component), 50);
	}


	@Override
	protected void addRequiredWebResources(WOResponse response, WOContext context) {

		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_WONDER_JS);
		
	}


}