package er.ajax.jquery;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.ajax.AjaxUtils;
import er.extensions.appserver.ERXResponseRewriter;
import er.extensions.foundation.ERXProperties;

public class JQAjaxUtils extends AjaxUtils {

	private static final String JQUERY_MIN_JS = "scripts/core/jquery-1.9.1.min.js";
	public static final String FRAMEWORK = "JQuery";
	public static final String JQUERY_JS = "javascript/core/jquery-1.9.1.js";
	public static final String JQUERY_WONDER_JS = "javascript/core/jquery.wonder.js";
	public static final String JQUERY_UI_JS = "javascript/core/jquery-ui.js";
	public static final String JQUERY_UI_WONDER_JS = "javascript/core/jquery-ui.wonder.js";
	
	public static void addScriptResourceInHead(WOContext aContext, WOResponse aResponse, String framework, String fileName) {
		String processedFileName = fileName;
		if(ERXProperties.booleanForKey("er.ajax.jquery.compressed") && FRAMEWORK.equals(framework) && JQUERY_JS.equals(fileName)) {
			processedFileName = JQUERY_MIN_JS;
		}
		AjaxUtils.addScriptResourceInHead(aContext, aResponse, framework, processedFileName);
		ERXResponseRewriter.addScriptResourceInHead(aResponse, aContext, framework, processedFileName);
	}

	public static void addUIStylesheetResourceInHead(WOContext aContext, WOResponse aResponse, String framework, String fileName) {

		if(framework == null) {
			framework = ERXProperties.stringForKey("er.ajax.jquery-ui.theme.framework");
		}
		
		if(fileName == null) {
			fileName = ERXProperties.stringForKey("er.ajax.jquery-ui.theme.fileName");
		}
		
		ERXResponseRewriter.addStylesheetResourceInHead(aResponse, aContext, framework, fileName);

	}
	
}
