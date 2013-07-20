package er.ajax.jquery;

import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOElement;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;

import er.ajax.AjaxOption;
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
		optionsArray.addObject(new AjaxOption("debug", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("submitHandler", AjaxOption.FUNCTION_1));
		optionsArray.addObject(new AjaxOption("invalidHandler", AjaxOption.FUNCTION_2));
		optionsArray.addObject(new AjaxOption("ignore", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("rules", AjaxOption.DICTIONARY));
		optionsArray.addObject(new AjaxOption("messages", AjaxOption.DICTIONARY));
		optionsArray.addObject(new AjaxOption("groups", AjaxOption.DICTIONARY));
		optionsArray.addObject(new AjaxOption("onfocusout", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("onkeyup", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("onclick", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("focusInvalid", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("focusCleanup", AjaxOption.BOOLEAN));
		optionsArray.addObject(new AjaxOption("errorClass", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("validClass", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("errorElement", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("wrapper", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("errorLabelContainer", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("errorContainer", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("showErrors", AjaxOption.FUNCTION));
		optionsArray.addObject(new AjaxOption("showErrors", AjaxOption.FUNCTION));
		optionsArray.addObject(new AjaxOption("errorPlacement", AjaxOption.FUNCTION_2));
		optionsArray.addObject(new AjaxOption("success", AjaxOption.STRING));
		optionsArray.addObject(new AjaxOption("highlight", AjaxOption.FUNCTION_2));
		optionsArray.addObject(new AjaxOption("unhighlight", AjaxOption.FUNCTION));
		optionsArray.addObject(new AjaxOption("ignoreTitle", AjaxOption.BOOLEAN));

		NSDictionary options = AjaxOption.createAjaxOptionsDictionary(optionsArray, component);
		return options;

	}

	@Override
	public void appendToResponse(WOResponse response, WOContext context) {
		addRequiredWebResources(response, context);
		super.appendToResponse(response, context);
		
	}

	private void addRequiredWebResources(WOResponse response, WOContext context) {
		// TODO Auto-generated method stub
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", "javascript/plugins/validation/jquery.validate.js");
		boolean additionalMethods = context.component().valueForBooleanBinding("additionalMethods", false);
		if(additionalMethods) {
			JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", "javascript/plugins/validation/additional-methods.js");
		}
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
	}
	
	
}