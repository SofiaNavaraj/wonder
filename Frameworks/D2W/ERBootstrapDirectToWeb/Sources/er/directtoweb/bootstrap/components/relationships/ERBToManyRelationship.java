package er.directtoweb.bootstrap.components.relationships;

import com.webobjects.appserver.WOContext;
import er.extensions.components.ERXToManyRelationship;

public class ERBToManyRelationship extends ERXToManyRelationship {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBToManyRelationship(WOContext context) {
        super(context);
    }
 
    public String uiStyle() {
		return valueForStringBinding("uiStyle", "browser");
    }

}