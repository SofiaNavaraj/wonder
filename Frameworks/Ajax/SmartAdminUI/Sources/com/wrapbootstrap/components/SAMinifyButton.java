package com.wrapbootstrap.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;

import er.ajax.AjaxComponent;
import er.ajax.jquery.JQAjaxUtils;

@SuppressWarnings("serial")
public class SAMinifyButton extends AjaxComponent {

	public SAMinifyButton(WOContext context) {
        super(context);
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
		JQAjaxUtils.addScriptResourceInHead(context(), aResponse, "JQueryUI", JQAjaxUtils.JQUERY_UI_JS);
		JQAjaxUtils.addScriptResourceInHead(context(), aResponse, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		addScriptResourceInHead(aResponse, "SmartAdminUI", "javascript/core/smartadmin.wonder.js");		
		
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
}