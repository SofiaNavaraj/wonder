package er.ajax.jquery.ui;

import java.text.Format;

import com.ibm.icu.text.SimpleDateFormat;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestampFormatter;

import er.ajax.AjaxOption;
import er.ajax.jquery.JQAjaxUtils;
import er.ajax.jquery.utils.JQueryUtils;
import er.extensions.components.ERXComponent;
import er.extensions.formatters.ERXJodaFormat;

public class JQDatePicker extends ERXComponent {

	
	private Format _formatter;
	private String _format;
	
	public JQDatePicker(WOContext context) {
        super(context);
    }

	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		addRequiredWebResources(aResponse, aContext);
		super.appendToResponse(aResponse, aContext);
	}
	
	protected void addRequiredWebResources(WOResponse response, WOContext context) {

		WOComponent component = context.component();
		String framework = component.valueForStringBinding("framework", null);
		String filename = component.valueForStringBinding("filename", null);

		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQuery", JQAjaxUtils.JQUERY_WONDER_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_JS);
		JQAjaxUtils.addScriptResourceInHead(context, response, "JQueryUI", JQAjaxUtils.JQUERY_UI_WONDER_JS);
		if(valueForBooleanBinding("includeUIStylesheet", true)) {
			JQAjaxUtils.addUIStylesheetResourceInHead(context, response, framework, filename);
		}

	}
	
	@Override
	public void awake() {
		super.awake();
		
		if(! (hasBinding("formatter") || hasBinding("format"))) {
			_format = "%m/%d/%Y";
			_formatter = new NSTimestampFormatter(_format);
		} else if(hasBinding("formatter")) {
			_formatter = (Format) valueForBinding("formatter");
			if(_formatter instanceof NSTimestampFormatter) {
				_format = translateSimpleDateFormatSymbols(((NSTimestampFormatter)_formatter).pattern());
			} else if(_formatter instanceof SimpleDateFormat) {
				_format = ((SimpleDateFormat) _formatter).toPattern();
			} else if(_formatter instanceof ERXJodaFormat) {
				_format = ((ERXJodaFormat) _formatter).pattern();
			} else {
				throw new RuntimeException("Can't handle formatter of class " + _formatter.getClass().getCanonicalName());
			}
		} else {
			_format = (String) valueForBinding("format");
			_formatter = new NSTimestampFormatter(_format);
		}
		
		_format = translateSimpleDateFormatSymbols(_format);
		
	}
	
	@Override
	public void reset() {
		_formatter = null;
		_format = null;
		super.reset();
	}
	
	public String options() {

		NSMutableArray<AjaxOption> ajaxOptionsArray = new NSMutableArray<AjaxOption>();
		ajaxOptionsArray.addObject(new AjaxOption("showOtherMonths", AjaxOption.BOOLEAN));
		ajaxOptionsArray.addObject(new AjaxOption("selectOtherMonths", AjaxOption.BOOLEAN));
		
		NSMutableDictionary<String, Object> options = new NSMutableDictionary<String, Object>();
		options.takeValueForKey(altFormat(format()), "dateFormat");
		return JQueryUtils.simpleSerialize(options, true);
	}

	public boolean isStateless() {
		return true;
	}
	
	public String format() {
		return _format;
	}
	
	public Format formatter() {
		return _formatter;
	}
	
	private static String[][] conversionTable = new String[][]{
		{"%a", "D" }, 
		{"%A", "DD"}, 
		{"%b", "M" }, 
		{"%B", "MM"},
		{"%d", "dd"},
		{"%e", "d"},
		{"%j", "oo"},
		{"%m", "mm"}, 
		{"%y", "y"}, 
		{"%Y", "yy"}
	};
	
	private static String altFormat(String format) {
		String result = format;
		for(int i = 0; i < conversionTable.length; i++) {
			result = result.replaceAll(conversionTable[i][0], conversionTable[i][1]);
		}
		return result;
	}
	
    /**
     * Quick and rude translation of formatting symbols from SimpleDateFormat to the symbols
     * that this component uses.
     *
     * @param symbols the date format symbols to translate
     * @return translated date format symbols 
     */
    public String translateSimpleDateFormatSymbols(String symbols) {
    	// Wildly assume that there is no translation needed if we see a % character
    	if (symbols.indexOf('%') > -1) {
    		return symbols;
    	}
    	
    	StringBuilder sb = new StringBuilder(symbols);
    	replace(sb, "dd", "%~");
    	replace(sb, "d", "%d");
    	replace(sb, "%~", "%d");
    	replace(sb, "MMMM", "%B");
    	replace(sb, "MMM", "%b");
    	replace(sb, "MM", "%m");
    	replace(sb, "M", "%m");
    	replace(sb, "yyyy", "%Y");
    	replace(sb, "yyy", "%~");
    	replace(sb, "yy", "%~");
    	replace(sb, "y", "%y");
    	replace(sb, "%~", "%y");
    	
    	return sb.toString();
    }
    
    /**
     * Helper method for translateSimpleDateFormatSymbols.
     */
    private void replace(StringBuilder builder, String original, String replacement) {
    	int index = builder.indexOf(original);
    	if (index > -1) {
    		builder.replace(index, index + original.length(), replacement);
    	}
    }
	
}