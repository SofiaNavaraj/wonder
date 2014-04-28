package er.ajax.jquery.components;

import org.apache.commons.lang.StringEscapeUtils;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxComponent;
import er.ajax.AjaxOption;
import er.ajax.JQAjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQMaskedInput extends AjaxComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQMaskedInput(WOContext context) {
        super(context);
    }

	public boolean noFormatBound() {
		return (! hasBinding("numberformat")) && (! hasBinding("formatter")) && (! hasBinding("dateformat"));
	}

	@Override
	protected void addRequiredWebResources(WOResponse response) {

		// TODO Auto-generated method stub
		JQAjaxUtils.addScriptResourceInHead(context(), response, JQAjaxUtils.FRAMEWORK, JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context(), response, JQAjaxUtils.FRAMEWORK, "javascript/plugins/maskedinput/jquery.maskedinput.js");
		JQAjaxUtils.addScriptResourceInHead(context(), response, JQAjaxUtils.FRAMEWORK, JQAjaxUtils.JQUERY_WONDER_JS);

	}
	
	public String options() {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new JQAjaxOption("mask", AjaxOption.STRING));

		NSMutableDictionary options = JQAjaxOption.createAjaxOptionsDictionary(ajaxOptionsArray, context().component());
		return  StringEscapeUtils.escapeHtml(ERXPropertyListSerialization.jsonStringFromPropertyList(options));

	}

	public boolean isStateless() {
		return true;
	}

	@Override
	public WOActionResults handleRequest(WORequest request, WOContext context) {
		// TODO Auto-generated method stub
		return null;
	}
}