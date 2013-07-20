package er.ajax.jquery;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxModalDialog;
import er.ajax.AjaxOption;
import er.ajax.AjaxUpdateContainer;
import er.ajax.AjaxUtils;
import er.extensions.appserver.ERXWOContext;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQAjaxUpdateContainer extends AjaxDynamicElement {

	public JQAjaxUpdateContainer(String name, NSDictionary<String, WOAssociation> associations, WOElement children) {
		super(name, associations, children);
	}
	
	public static String updateContainerID(AjaxDynamicElement element, WOComponent component) {
		return AjaxUpdateContainer.updateContainerID(element, component);
	}	

	@Override
	protected String _containerID(WOContext context) {
		String id = (String) valueForBinding("id", context.component());
		if (id == null) {
			id = ERXWOContext.safeIdentifierName(context, false);
		}
		return id;
	}
	
	public void appendToResponse(WOResponse response, WOContext context) {
		WOComponent component = context.component();
		if(!shouldRenderContainer(component)) {
			if(hasChildrenElements()) {
				appendChildrenToResponse(response, context);
			}
			super.appendToResponse(response, context);
		}
		else {
			String previousUpdateContainerID = AjaxUpdateContainer.currentUpdateContainerID();
			try {
				
				NSDictionary options = _options(component);
				
				String elementName = (String) valueForBinding("elementName", "div", component);
				AjaxUpdateContainer.setCurrentUpdateContainerID(_containerID(context));
				response.appendContentString("<" + elementName);
				String id = _containerID(context);
				appendTagAttributeToResponse(response, "id", id);
				appendTagAttributeToResponse(response, "class", valueForBinding("class", component));
				appendTagAttributeToResponse(response, "style", valueForBinding("style", component));
				appendTagAttributeToResponse(response, "data-wonder-id", "AUC");
				appendTagAttributeToResponse(response, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	
				response.appendContentString(">");
			
				if(hasChildrenElements()) {
					appendChildrenToResponse(response, context);
				}
				
				response.appendContentString("</" + elementName + ">");
				
				super.appendToResponse(response, context);
				
//				Object frequency = valueForBinding("minTimeout", component);
//				String observeFieldID = (String) valueForBinding("observeFieldID", component);
				
//				boolean skipFunction = frequency == null && observeFieldID == null && booleanValueForBinding("skipFunction", false, component);

				/*
				 * 				if(! skipFunction && AjaxUtils.isAjaxRequest(context.request())) {
					AjaxUtils.appendScriptHeader(response);
					response.appendContentString("WOnder.AUC.initialize($j(\"#" + id + "\"));");
					AjaxUtils.appendScriptFooter(response);
				}

				 */
				
				
				/*
				
				if(! skipFunction) {
					
					AjaxUtils.appendScriptHeader(response);
					if(frequency != null) {
						boolean isNotZero = true;
						try {
							float numberFrequency = ERXValueUtilities.floatValue(frequency);
							if(numberFrequency == 0.0) {
								isNotZero = false;
							}
						}
						catch(RuntimeException e) {
							throw new IllegalStateException("Error parsing float from value: <" + frequency + ">"); 
						}
						if(isNotZero) {
							boolean canStop = false;
							boolean stopped = false;
							if(associations().objectForKey("stopped") != null) {
								canStop = true;
								stopped = booleanValueForBinding("stopped", false, component);
							}
							response.appendContentString("AUC.registerPeriodic('" + id + "'," + canStop + "," + stopped + ",");
							AjaxOptions.appendToResponse(options, response, context);
							response.appendContentString(");");
						}
					}
					
					if(observeFieldID != null) {
						boolean fullSubmit = booleanValueForBinding("fullSubmit", false, component);
						AjaxObserveField.appendToResponse(response, context, this, observeFieldID, false, id, fullSubmit, createObserveFieldOptions(component));
					}
				
					response.appendContentString("AUC.register('" + id + "'");
					NSDictionary nonDefaultOptions = AjaxUpdateContainer.removeDefaultOptions(options);
					if(nonDefaultOptions.count() > 0) {
						response.appendContentString(", ");
						AjaxOptions.appendToResponse(nonDefaultOptions, response, context);
					}
					
					response.appendContentString(");");	

					AjaxUtils.appendScriptFooter(response);
				}
				
				*/
				
			}
			finally {
				AjaxUpdateContainer.setCurrentUpdateContainerID(previousUpdateContainerID);
			}
		}
	}
	
	
	@Override
	protected void addRequiredWebResources(WOResponse response,
			WOContext context) {

		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		if(hasBinding("minTimeout")) {
			JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", "javascript/plugins/periodical/jquery.periodicalupdater.js");
		}

		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		
	}
	
	protected boolean shouldRenderContainer(WOComponent component) {
		return !booleanValueForBinding("optional", false, component) || AjaxUpdateContainer.currentUpdateContainerID() == null;
	}	
	
	protected NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new AjaxOption("async", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("cache", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("complete", AjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new AjaxOption("contents", AjaxOption.DICTIONARY));
		ajaxOptionsArray.addObject(new AjaxOption("contentType", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new AjaxOption("delegate", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new AjaxOption("minTimeout", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("maxTimeout", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("multiplier", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("maxCalls", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("observeFields", AjaxOption.ARRAY));
		ajaxOptionsArray.addObject(new AjaxOption("headers", AjaxOption.ARRAY));

		NSDictionary options = AjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		options.takeValueForKey(AjaxUtils.ajaxComponentActionUrl(component.context()), "updateUrl");

		if(hasBinding("subscribes")) {
			options.takeValueForKey(stringValueForBinding("subscribes", component), "subscribes");	
		}

		return options;
		
	}

	public NSMutableDictionary createObserveFieldOptions(WOComponent component) {
		NSMutableArray ajaxOptionsArray = new NSMutableArray();
		ajaxOptionsArray.addObject(new AjaxOption("observeFieldFrequency", AjaxOption.NUMBER));
		NSMutableDictionary options = AjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		return options;
	}
	
	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		
		WOComponent component = context.component();
		String id = _containerID(context);
		
		if(associations().objectForKey("action") != null) {
			@SuppressWarnings("unused")
			WOActionResults results = (WOActionResults) valueForBinding("action", component);
		}
		
		WOResponse response = AjaxUtils.createResponse(request, context);
		AjaxUtils.setPageReplacementCacheKey(context, id);
		
		if(hasChildrenElements()) {
			appendChildrenToResponse(response, context);
		}
		
		String onRefreshComplete = (String) valueForBinding("onRefreshComplete", component);
		if(onRefreshComplete != null) {
			AjaxUtils.appendScriptHeader(response);
			response.appendContentString(onRefreshComplete);
			AjaxUtils.appendScriptFooter(response);
		}
		
		if(AjaxModalDialog.isInDialog(context)) {
			AjaxUtils.appendScriptHeader(response);
			response.appendContentString("AMD.contentUpdated();");
			AjaxUtils.appendScriptFooter(response);
		}
		
		return null;
	
	}

	


}