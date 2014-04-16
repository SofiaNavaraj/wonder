package sa.directtoweb.components;

import com.webobjects.appserver.WOContext;
import er.extensions.components.ERXToOneRelationship;

public class SAXToOneRelationship extends ERXToOneRelationship {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SAXToOneRelationship(WOContext context) {
        super(context);
    }
    
    public boolean isSelect2() {
    		return uiStyle().equals("select2");
    }

}