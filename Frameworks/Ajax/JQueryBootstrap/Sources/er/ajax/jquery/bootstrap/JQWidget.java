package er.ajax.jquery.bootstrap;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.ajax.jquery.JQAjaxUtils;
import er.extensions.components.ERXStatelessComponent;

public class JQWidget extends ERXStatelessComponent {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQWidget(WOContext context) {
        super(context);
    }

	public String elementName() {
		return stringValueForBinding("elementName", "div");
	}

	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		super.appendToResponse(aResponse, aContext);
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addStylesheetResourceInHead(aContext, aResponse, "JQueryBootstrap", "css/bootstrap.min.css");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/core/bootstrap.min.js");
		JQAjaxUtils.addScriptResourceInHead(aContext, aResponse, "JQueryBootstrap", "javascript/plugins/widget/jarvis.widget.js");
	}

	public String widgetClass() {
		return stringValueForBinding("widgetClass", "jarviswidget");
	}
	
}