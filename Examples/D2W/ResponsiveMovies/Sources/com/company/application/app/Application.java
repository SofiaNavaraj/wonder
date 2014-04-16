package com.company.application.app;

import sa.directtoweb.components.navigation.SAXNavigationManager;

import com.company.application.ui.components.Main;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.extensions.appserver.ERXApplication;

public class Application extends ERXApplication {

	public static void main(String[] argv) {
		ERXApplication.main(argv, Application.class);
	}

	public Application() {
		ERXApplication.log.info("Welcome to " + name() + " !");
		setDefaultRequestHandler(requestHandlerForKey(directActionRequestHandlerKey()));
		setAllowsConcurrentRequestHandling(true);
	}

	@Override
	public void finishInitialization() {
		super.finishInitialization();

		// Setup main navigation
		SAXNavigationManager.manager().configureNavigation();

	}

	@Override
	public WOResponse handleSessionRestorationErrorInContext(WOContext aContext) {

		WOResponse response = null;

		if (isAjaxRequest(aContext.request())) {

			response = new WOResponse();
			StringBuilder sb = new StringBuilder();
			sb.append("<script type=\"text/javascript\">");
			sb.append("document.location.reload();");
			sb.append("</script>");
			response.setContent(sb.toString());

		} else {

			Main nextPage = pageWithName(Main.class);
			nextPage.setErrorMessage("<i class = \"fa-fw fa fa-warning\"></i> Please log back in your session has timed out.");
			nextPage.setErrorMessageType("alert alert-warning fade in");
			response = nextPage.generateResponse();

		}

		return response;

	}

}
