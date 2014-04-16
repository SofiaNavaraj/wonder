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

public class SAMainContent extends AjaxComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SAMainContent(WOContext context) {
        super(context);
    }

	public String componentId() {
		return valueForStringBinding("id", "main");
	}
	
	public String elementName() {
		return valueForStringBinding("elementName", "div");
	}
	
	public String role() {
		return valueForStringBinding("role", "main");
	}
	
	@Override
	public boolean isStateless() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	protected void addRequiredWebResources(WOResponse aResponse) {

		// TODO Auto-generated method stub
		JQAjaxUtils.addScriptResourceInHead(context(), aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context(), aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		addStylesheetResourceInHead(aResponse, "JQueryBootstrap", "css/bootstrap.min.css");
		addStylesheetResourceInHead(aResponse, "SmartAdminUI", "css/font-awesome.min.css");
		addStylesheetResourceInHead(aResponse, "SmartAdminUI", "css/smartadmin-production_unminified.css");
		addStylesheetResourceInHead(aResponse, "SmartAdminUI", "css/smartadmin-skins.css");
		addScriptResourceInHead(aResponse, "SmartAdminUI", "javascript/core/smartadmin.wonder.js");		

	}
	
	@SuppressWarnings("rawtypes")
	protected NSDictionary _options() {

		NSMutableArray<AjaxOption> optionsArray = new NSMutableArray<AjaxOption>();
		optionsArray.addObject(new AjaxOption("left-panel", AjaxOption.STRING));
		NSDictionary options = AjaxOption.createAjaxOptionsDictionary(optionsArray, context().component());
		return options;

	}	
	
	public String options() {
		return ERXPropertyListSerialization.jsonStringFromPropertyList(_options());
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}