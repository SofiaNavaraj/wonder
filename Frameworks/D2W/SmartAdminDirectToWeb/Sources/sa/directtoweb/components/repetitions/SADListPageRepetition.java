package sa.directtoweb.components.repetitions;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.repetitions.ERDListPageRepetition;

public class SADListPageRepetition extends ERDListPageRepetition {

	public SADListPageRepetition(WOContext context) {
        super(context);
    }

	public String tableBodyRowClassName() {
		return displayGroup().displayedObjects().indexOf(d2wContext().valueForKey("object")) % 2 == 0 ? "odd" : "even";
	}
	
}