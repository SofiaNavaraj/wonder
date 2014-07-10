package er.directtoweb.bootstrap.components.repetitions;

import com.webobjects.appserver.WOContext;
import er.extensions.components.ERXToOneRelationship;

@SuppressWarnings("serial")
public class ERBToOneRelationship extends ERXToOneRelationship {
    
	public ERBToOneRelationship(WOContext context) {
        super(context);
    }
    
    public boolean isSelect2() {
    		return uiStyle().equals("select2");
    }

}