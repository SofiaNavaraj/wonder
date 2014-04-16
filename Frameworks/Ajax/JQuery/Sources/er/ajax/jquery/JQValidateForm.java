package er.ajax.jquery;

import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import er.ajax.AjaxOption;
import er.ajax.JQAjaxOption;
import er.extensions.components._private.ERXWOForm;
import er.extensions.foundation.ERXPropertyListSerialization;

public class JQValidateForm extends ERXWOForm {

	public JQValidateForm(String name, NSDictionary<String, WOAssociation> associations, WOElement element) {
		super("form", associations, element);
	}

	@Override
	public void appendAttributesToResponse(WOResponse response, WOContext context) {
		super.appendAttributesToResponse(response, context);
		WOComponent component = context.component();
		response._appendTagAttributeAndValue("data-wonder-id", "Validate", true);
		response._appendTagAttributeAndValue("data-wonder-options", ERXPropertyListSerialization.jsonStringFromPropertyList(_options(component)), true);
	}
	
	private NSDictionary _options(WOComponent component) {

		NSMutableArray<AjaxOption> optionsArray = new NSMutableArray<AjaxOption>();
		optionsArray.addObject(new JQAjaxOption("debug", AjaxOption.BOOLEAN));
		optionsArray.addObject(new JQAjaxOption("submitHandler", AjaxOption.FUNCTION_1));
		optionsArray.addObject(new JQAjaxOption("invalidHandler", AjaxOption.FUNCTION_2));
		optionsArray.addObject(new JQAjaxOption("ignore", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("rules", AjaxOption.DICTIONARY));
		optionsArray.addObject(new JQAjaxOption("messages", AjaxOption.DICTIONARY));
		optionsArray.addObject(new JQAjaxOption("groups", AjaxOption.DICTIONARY));
		optionsArray.addObject(new JQAjaxOption("onfocusout", AjaxOption.BOOLEAN));
		optionsArray.addObject(new JQAjaxOption("onkeyup", AjaxOption.BOOLEAN));
		optionsArray.addObject(new JQAjaxOption("onclick", AjaxOption.BOOLEAN));
		optionsArray.addObject(new JQAjaxOption("focusInvalid", AjaxOption.BOOLEAN));
		optionsArray.addObject(new JQAjaxOption("focusCleanup", AjaxOption.BOOLEAN));
		optionsArray.addObject(new JQAjaxOption("errorClass", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("validClass", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("errorElement", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("wrapper", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("errorLabelContainer", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("errorContainer", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("showErrors", AjaxOption.FUNCTION));
		optionsArray.addObject(new JQAjaxOption("showErrors", AjaxOption.FUNCTION));
		optionsArray.addObject(new JQAjaxOption("errorPlacement", AjaxOption.FUNCTION_2));
		optionsArray.addObject(new JQAjaxOption("success", AjaxOption.STRING));
		optionsArray.addObject(new JQAjaxOption("highlight", AjaxOption.FUNCTION_2));
		optionsArray.addObject(new JQAjaxOption("unhighlight", AjaxOption.FUNCTION));
		optionsArray.addObject(new JQAjaxOption("ignoreTitle", AjaxOption.BOOLEAN));

		NSDictionary options = JQAjaxOption.createAjaxOptionsDictionary(optionsArray, component);
		return options;

	}

	@Override
	public void appendToResponse(WOResponse response, WOContext context) {
		super.appendToResponse(response, context);
		addRequiredWebResources(response, context);
		
	}

	private void addRequiredWebResources(WOResponse response, WOContext context) {
		// TODO Auto-generated method stub
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", "javascript/plugins/validation/jquery.validate.min.js");
		boolean additionalMethods = context.component().valueForBooleanBinding("additionalMethods", false);
		if(additionalMethods) {
			JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", "javascript/plugins/validation/additional-methods.js");
		}
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
	}
	
	
}