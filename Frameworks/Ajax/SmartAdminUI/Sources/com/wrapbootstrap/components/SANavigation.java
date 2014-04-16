package com.wrapbootstrap.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import er.ajax.AjaxComponent;
import er.ajax.AjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.foundation.ERXPropertyListSerialization;

@SuppressWarnings("serial")
public class SANavigation extends AjaxComponent {
    
	public SANavigation(WOContext context) {
        super(context);
    }
	
	@Override
	public boolean isStateless() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void addRequiredWebResources(WOResponse aResponse) {

		// TODO Auto-generated method stub
		addStylesheetResourceInHead(aResponse, "JQueryBootstrap", "css/bootstrap.min.css");
		addStylesheetResourceInHead(aResponse, "SmartAdminUI", "css/font-awesome.min.css");
		addStylesheetResourceInHead(aResponse, "SmartAdminUI", "css/smartadmin-production_unminified.css");
		addStylesheetResourceInHead(aResponse, "SmartAdminUI", "css/smartadmin-skins.css");

		JQAjaxUtils.addScriptResourceInHead(context(), aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context(), aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		addScriptResourceInHead(aResponse, "SmartAdminUI", "js/smartwidgets/jarvis.menu.js");
		addScriptResourceInHead(aResponse, "SmartAdminUI", "javascript/core/smartadmin.wonder.js");
		
	}
	
	public String elementName() {
		return valueForStringBinding("elementName", "nav");
	}

	@SuppressWarnings("rawtypes")
	protected NSDictionary _options() {

		NSMutableArray<AjaxOption> optionsArray = new NSMutableArray<AjaxOption>();
		optionsArray.addObject(new AjaxOption("accordion", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("speed", AjaxOption.NUMBER));
		optionsArray.addObject(new AjaxOption("closedSign", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("openSign", AjaxOption.STRING));
		return AjaxOption.createAjaxOptionsDictionary(optionsArray, context().component());

	}
	
	public String options() {
		return ERXPropertyListSerialization.jsonStringFromPropertyList(_options()).replace("\"", "&quot;");
	}
	
	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}