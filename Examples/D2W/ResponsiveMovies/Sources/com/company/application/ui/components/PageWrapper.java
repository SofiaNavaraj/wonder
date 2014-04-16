package com.company.application.ui.components;

import sa.directtoweb.components.navigation.SAXNavigationManager;
import sa.directtoweb.components.navigation.SAXNavigationState;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORedirect;
import com.webobjects.appserver.WOResponse;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WContext;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableDictionary;

import er.extensions.appserver.ERXResponseRewriter;
import er.extensions.components.ERXComponent;

public class PageWrapper extends ERXComponent {
	
    public PageWrapper(WOContext context) {
        super(context);
    }
    
	@Override
	public void appendToResponse(WOResponse response, WOContext context) {
		// TODO Auto-generated method stub
		super.appendToResponse(response, context);
		_addRequiredWebResources(response, context);
	}

	protected void _addRequiredWebResources(WOResponse response, WOContext context) {

		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/bootstrap.min.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/font-awesome.min.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/smartadmin-production.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/smartadmin-skins.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "app", "css/styles.css");
		
	}
	
    // Actions
    
    public WOComponent logout() {
        WOComponent redirectPage = pageWithName("WORedirect");
        ((WORedirect) redirectPage).setUrl(D2W.factory().homeHrefInContext(context()));
        session().terminate();
        return redirectPage;
    }	
    
    public NSKeyValueCoding navigationContext() {
    	
        NSKeyValueCoding context = (NSKeyValueCoding)session().objectForKey("navigationContext");

        if (context().page() instanceof D2WPage) {
            context = ((D2WPage)context().page()).d2wContext();
        }

        //log.debug(ERXNavigationManager.manager().navigationStateForSession(session()));
        if(context == null) {
            context = new NSMutableDictionary<Object, String>();
            session().setObjectForKey(context, "navigationContext");
        }
        @SuppressWarnings("unused")
		SAXNavigationState state = SAXNavigationManager.manager().navigationStateForSession(session());
        // log.debug("NavigationState:" + state + "," + state.state() + "," + state.stateAsString());
        //log.info("navigationContext:" + session().objectForKey("navigationContext"));
        return context;
    }	
	
    public D2WContext d2wContext() {
    	if (context().page() instanceof D2WPage) {
			D2WPage d2wPage = (D2WPage) context().page();
			return d2wPage.d2wContext();
		}
    	return null;
    }

	public String bodyClass() {
		String result = null;
		String pageConfig = (String)d2wContext().valueForKey("pageConfiguration");
		if (pageConfig != null && pageConfig.length() > 0) {
			result = pageConfig + "Body";
		}
		return result;
	}
}