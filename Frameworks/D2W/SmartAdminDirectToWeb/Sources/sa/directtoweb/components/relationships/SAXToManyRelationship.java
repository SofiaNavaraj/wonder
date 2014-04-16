package sa.directtoweb.components.relationships;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXToManyRelationship;

public class SAXToManyRelationship extends ERXToManyRelationship {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SAXToManyRelationship(WOContext context) {
        super(context);
    }
    
    public String uiStyle() {
    		return valueForStringBinding("uiStyle", "browser");
    }
    
}