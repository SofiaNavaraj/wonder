package er.directtoweb.bootstrap.components;

import com.webobjects.appserver.WOContext;

import er.directtoweb.components.ERD2WPropertyName;
import er.extensions.foundation.ERXDictionaryUtilities;

public class ERBD2WPropertyName extends ERD2WPropertyName {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ERBD2WPropertyName(WOContext context) {
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