package er.ajax.jquery.ui;

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
import er.ajax.AjaxOption;
import er.ajax.JQAjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQAccordion extends AjaxDynamicElement {

	public JQAccordion(String name, NSDictionary<String, WOAssociation> associations, WOElement template) {
		super(name, associations, template);
		// TODO Auto-generated constructor stub
	}
	
	public void appendToResponse(WOResponse response, WOContext context) {

		super.appendToResponse(response, context);
		WOComponent component = context.component();
		String elementName = stringValueForBinding("elementName", "div", component);
		response.appendContentString("<");
		response.appendContentString(elementName);
		appendTagAttributeToResponse(response, "id", id(context));
		appendTagAttributeToResponse(response, "class", valueForBinding("class", component));
		appendTagAttributeToResponse(response, "style", valueForBinding("style", component));
		appendTagAttributeToResponse(response, "role", valueForBinding("role", component));
		appendTagAttributeToResponse(response, "data-wonder-id", "Accordion");
		appendTagAttributeToResponse(response, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(_options(component)));
		response.appendContentString(">");
		appendChildrenToResponse(response, context);
		response.appendContentString("</");
		response.appendContentString(elementName);
		response.appendContentString(">");


	}

	
	protected NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();

		ajaxOptionsArray.addObject(new JQAjaxOption("active", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("activate", AjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new JQAjaxOption("animate", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("beforeActivate", AjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new JQAjaxOption("collapsible", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("disabled", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("create", AjaxOption.FUNCTION_2));
		ajaxOptionsArray.addObject(new JQAjaxOption("event", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("heightStyle", AjaxOption.STRING));
		
		NSDictionary options = JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		NSDictionary icons = _icons(component);

		if(icons != null) {
			options.takeValueForKey(_icons(component), "icons");
		}
		
		return options;

	}
	
	private NSDictionary _icons(WOComponent component) {

		NSMutableDictionary<String, Object> options = null;

		if(hasBinding("header") || hasBinding("activeHeader")) {

			options = new NSMutableDictionary<String, Object>();

			if(hasBinding("header")) {
				options.takeValueForKey(valueForBinding("header", component), "header");
			}
			
			if(hasBinding("activeHeader")) {
				options.takeValueForKey(valueForBinding("activeHeader", component), "activeHeader");
			}

		}

		return options;
		
	}
	
	@Override
	protected void addRequiredWebResources(WOResponse response,
			WOContext context) {

		WOComponent component = context.component();

		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_WONDER_JS);
		boolean include = (Boolean) valueForBinding("includeUIStylesheet", true, component);
		if(include) {
			String framework = stringValueForBinding("framework", null, component);
			String filename = stringValueForBinding("fileName", null, component);
			JQAjaxUtils.addUIStylesheetResourceInHead(context, response, framework, filename);
		}		

	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}



}