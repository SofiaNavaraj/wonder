package er.ajax.jquery;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.appserver._private.WODynamicElementCreationException;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxOption;
import er.ajax.AjaxSubmitButton;
import er.ajax.AjaxUpdateContainer;
import er.ajax.AjaxUtils;
import er.ajax.IAjaxElement;
import er.extensions.appserver.ERXWOContext;
import er.extensions.appserver.ajax.ERXAjaxApplication;
import er.extensions.appserver.ajax.ERXAjaxSession;
import er.extensions.components._private.ERXWOForm;
import er.extensions.foundation.ERXProperties;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQAjaxSubmitButton extends AjaxDynamicElement {

	public JQAjaxSubmitButton(String name, NSDictionary associations, WOElement children) {
        super(name, associations, children);
    }

	@Override
	protected void addRequiredWebResources(WOResponse response,
			WOContext context) {
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
	}
	
	public String nameInContext(WOContext context, WOComponent component) {
		return stringValueForBinding("name", context.elementID(), component);
	}
	
	public boolean disabledInComponent(WOComponent component) {
		return booleanValueForBinding("disabled", false, component);
	}
	
	protected NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new AjaxOption("async", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("cache", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("delegate", AjaxOption.STRING));
		
		NSMutableDictionary options = AjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		options.takeValueForKey(AjaxUtils.ajaxComponentActionUrl(component.context()), "url");
		String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component); 
		options.takeValueForKey(updateContainerID, "updateContainer");

		String name = nameInContext(component.context(), component);
		JQAjaxSubmitButton.fillInAjaxOptions(this, component, name, options);

		return options;
		
	}
	
	public static void fillInAjaxOptions(IAjaxElement element, WOComponent component, String submitButtonName, NSMutableDictionary options) {

		String systemDefaultFormSerializer = "Form.serializeWithoutSubmits";
		String defaultFormSerializer = ERXProperties.stringForKeyWithDefault("er.ajax.formSerializer", systemDefaultFormSerializer);
		String formSerializer = (String) element.valueForBinding("formSerializer", defaultFormSerializer, component);

		if(! defaultFormSerializer.equals(systemDefaultFormSerializer)) {
			options.setObjectForKey(formSerializer, "_fs");
		}
		
		options.setObjectForKey("'" + submitButtonName + "'", "_asbn");
	
		if("true".equals(options.objectForKey("async"))) {
			options.removeObjectForKey("async");
		}
		
		if("true".equals(options.objectForKey("evalScripts"))) {
			options.removeObjectForKey("evalScripts");
		}
		
	}
	
	public void appendToResponse(WOResponse response, WOContext context) {

		WOComponent component = context.component();
		NSDictionary options = _options(component);

		String functionName = stringValueForBinding("functionName", null, component);
		String formName = stringValueForBinding("formName", component);
		
		boolean showUI = (functionName == null || booleanValueForBinding("showUI", false, component));		
		boolean showButton = showUI && booleanValueForBinding("button", true, component);

		String formReference;
		if((!showButton || functionName != null) && formName == null) {
			formName = ERXWOForm.formName(context, null);
			if(formName == null) {
				throw new WODynamicElementCreationException("If button = false or functionName is not null, the containing form must have an explicit name.");
			}
		}

		if(formName == null) {
			formReference = "this.form";
		}
		else {
			formReference = "document." + formName;
		}
	
		String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component);
		String replaceID = stringValueForBinding("replaceID", component);
		
		if(showUI) {
			
			boolean disabled = disabledInComponent(component);
			String elementName = stringValueForBinding("elementName", "a", component);
	    	boolean useButtonTag = ERXProperties.booleanForKeyWithDefault("er.extensions.foundation.ERXPatcher.DynamicElementsPatches.SubmitButton.useButtonTag", false);

	    	if(showButton) {
	    		
	    		elementName = useButtonTag ? "button" : "input";
	    		response.appendContentString("<" + elementName);
	    		
	    		appendTagAttributeToResponse(response, "type", "button");
	    		String name = nameInContext(context, component);
	    		appendTagAttributeToResponse(response, "name", name);
	    		appendTagAttributeToResponse(response, "value", valueForBinding("value", component));
	    		appendTagAttributeToResponse(response, "accesskey", valueForBinding("accesskey", component));
	    		
	    		if(disabled) {
	    			appendTagAttributeToResponse(response, "disabled", "disabled");
	    		}
	    		
	    	}
	    	else {
	    		
				boolean isATag = "a".equalsIgnoreCase(elementName);
				if(isATag) {
					appendTagAttributeToResponse(response, "href", "javascript:void(0)");
				}
				else {
					response.appendContentString("<" + elementName);
				}
	    	}

	    	appendTagAttributeToResponse(response, "class", valueForBinding("class", component));
	    	appendTagAttributeToResponse(response, "style", valueForBinding("style", component));
	    	appendTagAttributeToResponse(response, "id", valueForBinding("id", component));
	    	appendTagAttributeToResponse(response, "title", valueForBinding("title", component));

	    	if(! disabled) {
				appendTagAttributeToResponse(response, "data-wonder-id", "ASB");
				appendTagAttributeToResponse(response, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	
			}
	    	
	    	
	    	if(showButton && !useButtonTag) {
	    		response.appendContentString("/>");
	    	}
	    	else {
	    		response.appendContentString(">");
	    		if(hasChildrenElements()) {
	    			appendChildrenToResponse(response, context);
	    		} else {
	    			response.appendContentString(stringValueForBinding("value", component));
	    		}
	    		response.appendContentString("</" + elementName + ">");
	    	}
		}

		super.appendToResponse(response, context);
		
	}
	
	@Override
	public WOActionResults invokeAction(WORequest request, WOContext context) {
						
		WOActionResults result = null;
		WOComponent component = context.component();
	
		String nameInContext = nameInContext(context, component);
		String requestName = (String) request.formValueForKey(AjaxSubmitButton.KEY_AJAX_SUBMIT_BUTTON_NAME);
		if(requestName != null) {
			requestName = requestName.replace("'", "");
		}
		
		boolean shouldHandleRequest = (!disabledInComponent(component) && context.wasFormSubmitted()) && 
	    		((context.isMultipleSubmitForm() && nameInContext.equals(requestName)) || ! context.isMultipleSubmitForm());

		if(shouldHandleRequest) {
			String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component);
			AjaxUpdateContainer.setUpdateContainerID(request, updateContainerID);
			context.setActionInvoked(true);
			result = handleRequest(request, context);
			ERXWOContext.contextDictionary().takeValueForKey(ERXAjaxSession.DONT_STORE_PAGE, ERXAjaxSession.DONT_STORE_PAGE);
		}
		
		return result;
		
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		
		WOComponent component = context.component();
		WOActionResults result = (WOActionResults) valueForBinding("action", component);

		if(ERXAjaxApplication.isAjaxReplacement(request)) {
			AjaxUtils.setPageReplacementCacheKey(context, stringValueForBinding("replaceID", component));
		}

		else if(result == null || booleanValueForBinding("ignoreActionResponse", false, component)) {
			WOResponse response = AjaxUtils.createResponse(request, context);
			String onClickServer = stringValueForBinding("onClickServer", component);
			if(onClickServer != null) {
				AjaxUtils.appendScriptHeader(response);
				response.appendContentString(onClickServer);
				AjaxUtils.appendScriptFooter(response);
			}
			result = response;
		}
		
		else {
			String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component);
			if(updateContainerID != null) {
				AjaxUtils.setPageReplacementCacheKey(context, updateContainerID);

			}
		}
		
		return result;

	}

}