package sa.directtoweb.components;

import com.webobjects.appserver.WOContext;

import er.directtoweb.components.ERD2WPropertyName;
import er.extensions.foundation.ERXDictionaryUtilities;

@SuppressWarnings("serial")
public class SAD2WPropertyName extends ERD2WPropertyName {

	public SAD2WPropertyName(WOContext context) {
        super(context);
    }

	public String d2wInfo() {

		StringBuilder sb = new StringBuilder();
		sb.append("<table class = \"table-hover\">");
		
		for(String currentKey: ERXDictionaryUtilities.stringKeysSortedAscending(contextDictionaryForPropertyKey())) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(currentKey);
			sb.append("</td>");
			sb.append("<td>");
			sb.append(contextDictionaryForPropertyKey().valueForKey(currentKey));
			sb.append("</td>");
			sb.append("</tr>");
		}
		
		sb.append("</table>");
		
		return sb.toString();

	}

}