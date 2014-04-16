package er.ajax.jquery.components;

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
import er.extensions.foundation.ERXStringUtilities;

public class JQGravatar extends AjaxDynamicElement {

	public JQGravatar(String name,
			NSDictionary<String, WOAssociation> associations,
			NSMutableArray<WOElement> children) {
		super(name, associations, children);
		// TODO Auto-generated constructor stub
	}
	
	public JQGravatar(String name, NSDictionary associations, WOElement children) {
		super(name, associations, children);
	}
	
	@Override
	protected void addRequiredWebResources(WOResponse response,
			WOContext context) {

//		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
//		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
//		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", "javascript/plugins/gravatar/gravatar.js");
		
	}

	public void appendToResponse(WOResponse aResponse, WOContext aContext) {

		WOComponent component = aContext.component();
		String elementName = stringValueForBinding("elementName", "img", component);
		aResponse.appendContentString("<");
		aResponse.appendContentString(elementName);
		appendTagAttributeToResponse(aResponse, "class", valueForBinding("class", component));
		appendTagAttributeToResponse(aResponse, "id", valueForBinding("class", component));
		appendTagAttributeToResponse(aResponse, "style", valueForBinding("class", component));
		String hash = new String(ERXStringUtilities.md5(stringValueForBinding("profile", component), "UTF-8"));
		appendTagAttributeToResponse(aResponse, "src", "http://www.gravatar.com/avatar/" + hash);
		//		appendTagAttributeToResponse(aResponse, "data-wonder-id", "Gravatar");
//		appendTagAttributeToResponse(aResponse, "data-wonder-options", 
//				ERXPropertyListSerialization.jsonStringFromPropertyList(options(component)));	

		if(elementName.equals("img")) {
			aResponse.appendContentString("/>");
		} else {
			aResponse.appendContentString("></");
			aResponse.appendContentString(elementName);
			aResponse.appendContentString(">");
		}

		super.appendToResponse(aResponse, aContext);

	}
	
	protected NSDictionary options(WOComponent component) {
		
		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new JQAjaxOption("profile", JQAjaxOption.STRING));
		return JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		
	}
	
	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}



}