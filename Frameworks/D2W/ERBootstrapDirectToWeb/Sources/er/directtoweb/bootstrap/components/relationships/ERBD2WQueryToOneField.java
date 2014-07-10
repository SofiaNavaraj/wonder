package er.directtoweb.bootstrap.components.relationships;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.relationships.ERD2WQueryToOneField;
import er.extensions.foundation.ERXStringUtilities;
import er.extensions.localization.ERXLocalizer;

public class ERBD2WQueryToOneField extends ERD2WQueryToOneField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBD2WQueryToOneField(WOContext context) {
        super(context);
    }

	public String where() {
		return ERXStringUtilities.capitalize((String) ERXLocalizer.currentLocalizer().valueForKeyPath("ERD2WQueryToOneField.where"));
	}

}