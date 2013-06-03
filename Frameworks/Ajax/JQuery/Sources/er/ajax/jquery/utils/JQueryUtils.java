package er.ajax.jquery.utils;

import com.webobjects.appserver.WOComponent;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;

import er.ajax.AjaxDynamicElement;

public class JQueryUtils {

	public static String cssClassForElementAndComponent(AjaxDynamicElement e, WOComponent c) {
		String clas = (String)e.valueForBinding("class", c);
		if ( clas != null ) {
			clas = e.getClass().getSimpleName() + " " + e.getClass().getSimpleName();
		} else {
			clas = e.getClass().getSimpleName();
		}
		return clas;
	}

	public static String simpleSerialize(NSDictionary<String, Object> dict) {
		return simpleSerialize(dict, false);
	}
	
	/**
	 * Simply serialize a dictionary into a JSON compliant string
	 * Keys and string values are quoted, though values are not rigorously tested for type.
	 * @param dict - the Dictionary to serialize
	 * @param useRealQuote - boolean - use " instead of &quot; 
	 * @return String - the resulting JSON.
	 */
	public static String simpleSerialize(NSDictionary<String,Object> dict, boolean escapeQuotes) {
		StringBuffer b = new StringBuffer();
		if (dict != null && dict.allKeys().count() != 0) {
			String quote = escapeQuotes ? "&quot;" : "\"";
			for (String akey : dict.allKeys()) {
				if (b.length() == 0) {
					b.append("{");
				} else {
					b.append(",");
				}
				Object avalue = dict.valueForKey(akey);
				boolean isString = avalue instanceof String;
				boolean isCollection = false;
				if (isString) {
					String astring = (String)avalue;
					isCollection = astring.indexOf("[") == 0 || astring.indexOf("{") == 0 || astring.indexOf("function") == 0;
				}
				b.append(quote);
				b.append(akey);
				b.append(quote);
				b.append(":");
				if (isString && !isCollection)
					b.append(quote);
				b.append(avalue.toString());
				if (isString && !isCollection)
					b.append(quote);
			}
			b.append("}");
		}
		return b.toString();
	}
	
	/**
	 * Add the value received by calling the binding on the given component to the supplied mutable dictionary.
	 * @param dict - NSMutableDictionary to add the value to.
	 * @param binding - String, binding to obtain the value, key to use in the dictionary
	 * @param c - WOComponent to call the binding on
	 */
	public static void addValueToDictionaryFromBinding(NSMutableDictionary<String, Object> dict, String binding, WOComponent c) {
		addValueToDictionaryFromBinding(dict, binding, binding, null, c);
	}
	
	/**
	 * Add the value received by calling the binding on the given component to the supplied mutable dictionary. If the binding returns null,
	 * add the default value instead.
	 * @param dict - NSMutableDictionary to add the value to.
	 * @param binding - String, binding to obtain the value, key to use in the dictionary
	 * @param defaultValue - String default value to use if the binding returns null
	 * @param c - WOComponent to call the binding on
	 */
	public static void addValueToDictionaryFromBinding(NSMutableDictionary<String, Object> dict, String binding, String defaultValue, WOComponent c) {
		addValueToDictionaryFromBinding(dict, binding, binding, defaultValue, c);
	}
	
	/**
	 * Add the value received by calling the binding on the given component to the supplied mutable dictionary with the supplied key. 
	 * If the binding returns null, add the default value instead.
	 * @param dict - NSMutableDictionary to add the value to.
	 * @param binding - String, binding to obtain the value, key to use in the dictionary
	 * @param key - String, key to use in the dictionary
	 * @param defaultValue - String default value to use if the binding returns null
	 * @param c - WOComponent to call the binding on
	 */
	public static void addValueToDictionaryFromBinding(NSMutableDictionary<String, Object> dict, String binding, String key, Object defaultValue, WOComponent c) {
		if (c.hasBinding(binding) || defaultValue != null) {
			Object value = c.valueForBinding(binding);
			if (value == null && defaultValue != null) 
				value = defaultValue;
			if (value != null) {
				dict.setObjectForKey(value, key);
			}
		}
	}
	
}
