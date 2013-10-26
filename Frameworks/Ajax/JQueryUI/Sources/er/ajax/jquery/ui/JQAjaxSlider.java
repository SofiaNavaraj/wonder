package er.ajax.jquery.ui;

import java.text.ParseException;

import org.apache.log4j.Logger;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNumberFormatter;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxOption;
import er.ajax.AjaxSubmitButton;
import er.ajax.AjaxUpdateContainer;
import er.ajax.AjaxUtils;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.foundation.ERXPropertyListSerialization;
import er.extensions.foundation.ERXStringUtilities;

public class JQAjaxSlider extends AjaxDynamicElement {

	private static final Logger log = Logger.getLogger(JQAjaxSlider.class);
	
	public JQAjaxSlider(String name, NSDictionary associations, WOElement children) {
		super(name, associations, children);
	}
	
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {

		WOComponent component = aContext.component();
		NSDictionary options = _options(component);
		options.takeValueForKey(aContext.elementID(), "elementID");
		options.takeValueForKey(AjaxUtils.ajaxComponentActionUrl(aContext), "url");

		String elementName = stringValueForBinding("elementName", "div", component);		
		aResponse.appendContentString("<" + elementName);
		
		String id = stringValueForBinding("id", component);
		if(ERXStringUtilities.stringIsNullOrEmpty(id) == false) {
			appendTagAttributeToResponse(aResponse, "id", id);
		}
		appendTagAttributeToResponse(aResponse, "data-wonder-id", "Slider");
		appendTagAttributeToResponse(aResponse, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	
		appendTagAttributeToResponse(aResponse, "name", aContext.elementID());
		aResponse.appendContentString("></" + elementName + ">");

		super.appendToResponse(aResponse, aContext);

	}

	@Override
	protected void addRequiredWebResources(WOResponse response, WOContext context) {

		WOComponent component = context.component();
		String framework = stringValueForBinding("framework", null, component);
		String filename = stringValueForBinding("fileName", null, component);

		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_WONDER_JS);
		JQAjaxUtils.addUIStylesheetResourceInHead(context, response, framework, filename);

	}
	
	public boolean isStateless() {
		return true;
	}
	
	@Override
	public void takeValuesFromRequest(WORequest aRequest, WOContext aContext) {
		
		_takeValuesFromRequest(aRequest, aContext);
		super.takeValuesFromRequest(aRequest, aContext);

	}

	public void _takeValuesFromRequest(WORequest aRequest, WOContext aContext) {

		WOComponent component = aContext.component();

		Object partialFormSenderId = null;
		
		if(aRequest.formValues() != null) {
			if(aRequest.formValues().containsKey(AjaxSubmitButton.KEY_PARTIAL_FORM_SENDER_ID)) {
				partialFormSenderId = aRequest.formValueForKey(AjaxSubmitButton.KEY_PARTIAL_FORM_SENDER_ID);
			}
		}
		
		if(partialFormSenderId != null && partialFormSenderId.equals(aContext.elementID())) {

			try {

				String format = stringValueForBinding("numberformat", "0", component);
				NSNumberFormatter numericFormatter = new NSNumberFormatter(format);
				String requestString = (String) aRequest.formValueForKey(aContext.elementID());
				
				if(requestString.indexOf(":") > 0) {
					
					String[] values = requestString.split(":");

					Number minValue = numericValue(values[0], numericFormatter);
					if(minValue != null) {
						setValueForBinding(minValue, "minValue", component);
					}
					
					Number maxValue = numericValue(values[1], numericFormatter);
					if(maxValue != null) {
						setValueForBinding(maxValue, "maxValue", component);
					}
					
				} else {

					Number number = aRequest.numericFormValueForKey(aContext.elementID(), numericFormatter);
					if(number != null) {
						setValueForBinding(number, "value", component);
					}

				}

			} catch(NumberFormatException e) {
				log.error(e);
			}
			
			
		}
	}
	
	public Number numericValue(String numberString, NSNumberFormatter numericFormatter) {
		Number number = null;
		if ((numberString != null) && (numericFormatter != null)) {
			try {
				number = (Number) numericFormatter.parseObject(numberString);
			} catch (ParseException e) {
				if (NSLog.debugLoggingAllowedForLevelAndGroups(2, 128L)) {
					NSLog.err.appendln(e);
				}
			}
		}
		return number;
	}
	
	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {

		WOResponse result = AjaxUtils.createResponse(request, context);
		
		if(AjaxUtils.isAjaxRequest(request)) {
			_takeValuesFromRequest(request, context);
		}
		
		return result;
	}

	protected NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new AjaxOption("value", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("animate", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("disabled", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("max", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("min", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("orientation", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new AjaxOption("range", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("step", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("maxValue", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("minValue", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new AjaxOption("trigger", AjaxOption.BOOLEAN));
		
		NSDictionary options = AjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		String updateContainerID = AjaxUpdateContainer.updateContainerID(this, component); 
		options.takeValueForKey(updateContainerID, "updateContainer");
		if(hasBinding("triggerName")) {
			options.takeValueForKey(stringValueForBinding("triggerName", component), "triggerName");
		}
		return options;
		
	}
	
}