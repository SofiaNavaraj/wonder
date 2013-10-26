package er.ajax.jquery;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxOption;
import er.ajax.AjaxUpdateContainer;
import er.ajax.AjaxUtils;
import er.extensions.appserver.ajax.ERXAjaxApplication;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQAjaxUpdateLink extends AjaxDynamicElement {

	public JQAjaxUpdateLink(String name, NSDictionary associations, WOElement children) {
		super(name, associations, children);
	}

	public void appendToResponse(WOResponse response, WOContext context) {

		WOComponent component = context.component();
		NSDictionary options = _options(component);

		boolean disabled = booleanValueForBinding("disabled", false, component);
		Object stringValue = valueForBinding("string", component);
		String functionName = (String) valueForBinding("functionName", component);

		if(functionName == null) {
			
			String elementName;
			boolean button = booleanValueForBinding("button", false, component);
			if(button) {
				elementName = "input";
			} else {
				elementName = stringValueForBinding("elementName", "a", component);
			}
			
			boolean isATag = "a".equalsIgnoreCase(elementName);
			boolean renderTags = (isATag);

			if(renderTags) {
			
				response.appendContentString("<");
				response.appendContentString(elementName);
				
				if(button) {
					appendTagAttributeToResponse(response, "type", button);
				}
				
				if(isATag) {
					appendTagAttributeToResponse(response, "href", "javascript:void(0)");
				}
				
				appendTagAttributeToResponse(response, "title", valueForBinding("title", component));
				appendTagAttributeToResponse(response, "value", valueForBinding("value", component));
				appendTagAttributeToResponse(response, "class", valueForBinding("class", component));
				appendTagAttributeToResponse(response, "style", valueForBinding("style", component));
				appendTagAttributeToResponse(response, "id", valueForBinding("id", component));
				appendTagAttributeToResponse(response, "accesskey", valueForBinding("accesskey", component));
				appendTagAttributeToResponse(response, "data-wonder-id", "AUL");
				appendTagAttributeToResponse(response, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	

				if(disabled) {
					appendTagAttributeToResponse(response, "disabled", true);
				}
				
				if(button) {
					if(stringValue != null) {
						appendTagAttributeToResponse(response, "value", stringValue);
					}
					if(disabled) {
						response.appendContentString(">");
					}
				}

				response.appendContentString(">");
				
			}

			if(stringValue != null && !button) {
				response.appendContentHTMLString(stringValue.toString());
			}

			appendChildrenToResponse(response, context);
			
			if(renderTags) {
				response.appendContentString("</");
				response.appendContentString(elementName);
				response.appendContentString(">");
			}
		
		} else {
			// TODO ... interesting.
		}

		super.appendToResponse(response, context);
	
	}
	
	protected NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new AjaxOption("async", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("cache", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("callback", AjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new AjaxOption("complete", AjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new AjaxOption("delegate", AjaxOption.STRING));

		NSDictionary options = AjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		options.takeValueForKey(AjaxUtils.ajaxComponentActionUrl(component.context()), "url");
		String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component); 
		options.takeValueForKey(updateContainerID, "updateContainer");
		options.takeValueForKey(component.context().contextID() + "." + component.context().elementID(), "elementID");
		return options;
		
	}
	
	
	@Override
	protected void addRequiredWebResources(WOResponse response,
			WOContext context) {
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		WOComponent component = context.component();
		boolean disabled = booleanValueForBinding("disabled", false, component);
		String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component); 
		AjaxUpdateContainer.setUpdateContainerID(request, updateContainerID);
		WOActionResults results = null;
		if (!disabled) {
			results = (WOActionResults) valueForBinding("action", component);
		}

		if (ERXAjaxApplication.isAjaxReplacement(request)) {
			AjaxUtils.setPageReplacementCacheKey(context, (String)valueForBinding("replaceID", component));
		}
		else if (results == null || booleanValueForBinding("ignoreActionResponse", false, component)) {
			String script = (String) valueForBinding("onClickServer", component);
			if (script != null) {
				WOResponse response = AjaxUtils.createResponse(request, context);
				AjaxUtils.appendScriptHeaderIfNecessary(request, response);
				response.appendContentString(script);
				AjaxUtils.appendScriptFooterIfNecessary(request, response);
				results = response;
			}
		}
		else if (updateContainerID != null) {
			AjaxUtils.setPageReplacementCacheKey(context, updateContainerID);
		}

		return results;
	}
	
}