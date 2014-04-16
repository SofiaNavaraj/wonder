package er.ajax;

import com.webobjects.foundation.NSKeyValueCoding;


public class JQAjaxValue extends AjaxValue {


	public JQAjaxValue(Object value) {
		super(value);
	}
	
	public JQAjaxValue(AjaxOption.Type type, Object object) {
		super(type, object);
	}
	
	/**
	 * @param obj Object to convert to String and escape
	 * @return obj converted to a string and escaped for use as a quoted JS string
	 */
	public static String javaScriptEscaped(Object obj) {
		String escapedValue = String.valueOf(obj);
		escapedValue = escapedValue.replaceAll("\\\\", "\\\\\\\\");
		escapedValue = escapedValue.replaceAll("'", "\\\\'");

		// Handle line breaks
        escapedValue = escapedValue.replaceAll("\\r\\n", "\\\\n");
        escapedValue = escapedValue.replaceAll("\\n", "\\\\n");

//		escapedValue = "'" + escapedValue + "'";
		return escapedValue;
	}	
	
	/**
	 * @return a String representing this AjaxValue in a form suitable for use in JavaScript
	 */
	public String javascriptValue() {

		AjaxOption.Type type = type();
		String javascriptValue = null;
		
		if ( value() != null && value() != NSKeyValueCoding.NullValue && type == AjaxOption.STRING) {
			javascriptValue = javaScriptEscaped(value());
		} else if(value() != null && value() != NSKeyValueCoding.NullValue && type == JQAjaxOption.FUNCTION_3) {
			javascriptValue = "function(v1, v2, v3) {" + value().toString() + "}";
		} else {
			javascriptValue = super.javascriptValue();
		}

		return javascriptValue;
		
	}
	
	
}
