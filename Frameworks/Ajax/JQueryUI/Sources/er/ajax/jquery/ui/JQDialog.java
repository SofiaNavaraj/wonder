package er.ajax.jquery.ui;

import java.net.MalformedURLException;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSForwardException;
import com.webobjects.foundation.NSMutableArray;

import er.ajax.AjaxDynamicElement;
import er.ajax.AjaxOption;
import er.ajax.AjaxUtils;
import er.ajax.JQAjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.appserver.ERXRequest;
import er.extensions.appserver.ERXWOContext;
import er.extensions.foundation.ERXMutableURL;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQDialog extends AjaxDynamicElement {
    
	public JQDialog(String name, NSDictionary associations, WOElement children) {
        super(name, associations, children);
    }
	
	@Override
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {

		addRequiredWebResources(aResponse, aContext);

		WOComponent component = aContext.component();
		
		String linkID = (String) valueForBinding("id", component);
		if(linkID == null) {
			linkID = ERXWOContext.safeIdentifierName(aContext, false);
		}

		String linkElementName = stringValueForBinding("linkElementName", "a", component);
		String linkLabel = stringValueForBinding("label", "Open", component);
		
		String dialogID = (String) valueForBinding("dialogID", linkID + "Dialog", component);
		
		String href = stringValueForBinding("href", component);
		if(href == null) {
		
			String directActionName = stringValueForBinding("directActionName", component);

			if(directActionName != null) {
				NSDictionary queryDictionary = (NSDictionary) valueForBinding("queryDictionary", component);
				boolean secure = booleanValueForBinding("secure", ERXRequest.isRequestSecure(aContext.request()), component);
				if(secure) {
					boolean generatingCompleteURLs = aContext.doesGenerateCompleteURLs();
					if(! generatingCompleteURLs) {
						aContext.generateCompleteURLs();
					}
					try {
						href = aContext._directActionURL(directActionName, queryDictionary, secure, 0, false);
						ERXMutableURL u = new ERXMutableURL(href);
						u.addQueryParameter(String.valueOf(System.currentTimeMillis()), null);
						href = u.toExternalForm();
					} catch (MalformedURLException e) {
						throw new NSForwardException(e);
					} finally {
						if(! generatingCompleteURLs) {
							aContext.generateRelativeURLs();
						}
					}
				} else {
					href = aContext.directActionURLForActionNamed(directActionName, queryDictionary);
				}
			}
		
		}
		
		boolean isAjax = booleanValueForBinding("ajax", false, component);
		if(href == null) {
			
			if(isAjax) {
				if(valueForBinding("id", component) == null) {
					throw new IllegalArgumentException("If ajax = 'true', you must also bind 'id'");
				}
				href = AjaxUtils.ajaxComponentActionUrl(aContext);
			} else if(associations().objectForKey("action") != null) {
				href = aContext.componentActionURL();
			}
			
			if(href == null) {
				href = "#" + dialogID;
			}

		}

		aResponse.appendContentString("<");
		aResponse.appendContentString(linkElementName);
        appendTagAttributeToResponse(aResponse, "class", valueForBinding("class", component));
        appendTagAttributeToResponse(aResponse, "href", href);
        appendTagAttributeToResponse(aResponse, "id", linkID);
        appendTagAttributeToResponse(aResponse, "style", valueForBinding("style", component));
        appendTagAttributeToResponse(aResponse, "data-wonder-id", "Dialog");
		NSDictionary options = _options(component);
		options.takeValueForKey(dialogID, "dialogID");
		appendTagAttributeToResponse(aResponse, "data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(options));	
		aResponse.appendContentString(">");
		aResponse.appendContentString(linkLabel);
		aResponse.appendContentString("</");
		aResponse.appendContentString(linkElementName);
		aResponse.appendContentString(">");
		
		aResponse.appendContentString("<div");
		appendTagAttributeToResponse(aResponse, "id", dialogID);
		appendTagAttributeToResponse(aResponse, "title", valueForBinding("title", component));
		aResponse.appendContentString(">");

		if(href.startsWith("#")) {
			appendChildrenToResponse(aResponse, aContext);
		}

		aResponse.appendContentString("</div>");

	}

	@Override
	protected String _containerID(WOContext context) {
		String id = (String) valueForBinding("id", context.component());
		return id;
	}	
	
	private NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		
		ajaxOptionsArray.addObject(new JQAjaxOption("appendTo", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("autoOpen", false, AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("buttons", AjaxOption.ARRAY));
		ajaxOptionsArray.addObject(new JQAjaxOption("closeOnEscape",AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("closeText", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("dialogClass", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("draggable", AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("height", AjaxOption.DEFAULT));
		ajaxOptionsArray.addObject(new JQAjaxOption("hide", AjaxOption.DEFAULT));
		ajaxOptionsArray.addObject(new JQAjaxOption("maxHeight", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("maxWidth", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("minHeight", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("minWidth", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("modal",AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("position", AjaxOption.DEFAULT));
		ajaxOptionsArray.addObject(new JQAjaxOption("resizable",AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new JQAjaxOption("show", AjaxOption.DEFAULT));
		ajaxOptionsArray.addObject(new JQAjaxOption("showEffect", AjaxOption.NUMBER));
		ajaxOptionsArray.addObject(new JQAjaxOption("title",AjaxOption.STRING));
		ajaxOptionsArray.addObject(new JQAjaxOption("width", AjaxOption.NUMBER));

		NSDictionary options = JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, component, associations());
		return options;
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

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {

		WOComponent component = context.component();
		
		WOResponse response = null;
		WOAssociation action = associations().objectForKey("action");
		if(action != null) {
			action.valueInComponent(component);
		}
		
		if(booleanValueForBinding("ajax", false, component) && hasChildrenElements()) {
			response = AjaxUtils.createResponse(request, context);
			AjaxUtils.setPageReplacementCacheKey(context, _containerID(context));
			appendChildrenToResponse(response, context);
		}
		
		return response;
		
	}
    
    @Override
    public WOActionResults invokeAction(WORequest worequest, WOContext wocontext) {
        WOComponent component = wocontext.component();
    	if (!booleanValueForBinding("ajax", false, component)) {
	        WOAssociation action = associations().objectForKey("action");
	        if(action != null && wocontext.elementID().equals(wocontext.senderID())) {
	            return (WOActionResults) action.valueInComponent(component);
	        }
    	}
        return super.invokeAction(worequest, wocontext);
    }
	
    public boolean shouldHandle(WOContext context) {
    	return context.elementID().equals(context.senderID());
    }

}