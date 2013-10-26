package er.ajax.jquery.ui;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;

import er.ajax.AjaxDynamicElement;

public class JQAccordionTab extends AjaxDynamicElement {
	
	public JQAccordionTab(String name,
			NSDictionary<String, WOAssociation> associations, WOElement template) {
		super(name, associations, template);
		// TODO Auto-generated constructor stub
	}

	public void appendToResponse(WOResponse response, WOContext context) {
		
		WOComponent component = context.component();

		String labelElementName = stringValueForBinding("labelElementName", "h3", component);
		String paneElementName = stringValueForBinding("paneElementName", "div", component);
		
		response.appendContentString("<");
		response.appendContentString(labelElementName);
		appendTagAttributeToResponse(response, "class", valueForBinding("labelClassName", component));
		appendTagAttributeToResponse(response, "style", valueForBinding("labelStyleName", component));
		response.appendContentString(">");
		response.appendContentString(stringValueForBinding("label", null, component));
		response.appendContentString("</");
		response.appendContentString(labelElementName);
		response.appendContentString(">");
		
		response.appendContentString("<");
		response.appendContentString(paneElementName);
		appendTagAttributeToResponse(response, "class", valueForBinding("paneClassName", component));
		appendTagAttributeToResponse(response, "style", valueForBinding("paneStyleName", component));
		response.appendContentString(">");
		
		appendChildrenToResponse(response, context);
		
		response.appendContentString("</");
		response.appendContentString(paneElementName);
		response.appendContentString(">");
		
		super.appendToResponse(response, context);
	}
	
	@Override
	protected void addRequiredWebResources(WOResponse response,
			WOContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}



}