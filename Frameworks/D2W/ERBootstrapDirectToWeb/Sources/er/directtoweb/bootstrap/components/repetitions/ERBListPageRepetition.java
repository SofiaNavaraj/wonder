package er.directtoweb.bootstrap.components.repetitions;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.repetitions.ERDListPageRepetition;

public class ERBListPageRepetition extends ERDListPageRepetition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBListPageRepetition(WOContext context) {
        super(context);
    }
    
	public String tableBodyRowClassName() {
		return displayGroup().displayedObjects().indexOf(d2wContext().valueForKey("object")) % 2 == 0 ? "odd" : "even";
	}    
	
}