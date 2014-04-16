package er.ajax.jquery.bootstrap;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxOption;
import er.ajax.JQAjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQBPopOver extends AjaxDynamicElement {

	private WOAssociation content;
	private WOAssociation title;
	
	public JQBPopOver(String name, NSDictionary<String, WOAssociation> associations, WOElement children) {
		super(name, associations, children);
		content = (WOAssociation) associations.valueForKey("content");
		title = (WOAssociation) associations.valueForKey("title");
	}

	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		super.appendToResponse(aResponse, aContext);

		WOComponent component = aContext.component();
		NSDictionary options = _options(component);

		String elementName = stringValueForBinding("elementName", "a", component);
		aResponse.appendContentString("<");
		aResponse.appendContentString(elementName);
		appendTagAttributeToResponse(aResponse, "href", "javascript:void(0)");
		appendTagAttributeToResponse(aResponse, "class", valueForBinding("class", component));
		appendTagAttributeToResponse(aResponse, "style", valueForBinding("style", component));
		appendTagAttributeToResponse(aResponse, "data-wonder-id", "PopOver");
		appendTagAttributeToResponse(aResponse, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	
		aResponse.appendContentString(">");
		appendChildrenToResponse(aResponse, aContext);
		aResponse.appendContentString("</");
		aResponse.appendContentString(elementName);
		aResponse.appendContentString(">");
		
		
	}

	protected void addRequiredWebResources(WOResponse aResponse, WOContext aContext) {
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/plugins/selectpicker/bootstrap-select.css");
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.css");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.js");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.wonder.js");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected NSDictionary _options(WOComponent component) {
		NSMutableArray ajaxOptionsArray = new NSMutableArray();
		ajaxOptionsArray.addObject(new JQAjaxOption("animation", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("html", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("placement", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("selector", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("trigger", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("delay", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("container", AjaxOption.STRING));
		NSDictionary options = JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		if(content != null) {
			options.takeValueForKey(content.valueInComponent(component), "content");
		}
		options.takeValueForKey(title.valueInComponent(component), "title");
		return options;		
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}	

}