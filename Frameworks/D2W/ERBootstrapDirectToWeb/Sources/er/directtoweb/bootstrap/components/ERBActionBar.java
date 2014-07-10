package er.directtoweb.bootstrap.components;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.ERDActionBar;

public class ERBActionBar extends ERDActionBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBActionBar(WOContext context) {
        super(context);
    }

	public String branchIcon() {
		return (String)branch().valueForKey("branchIcon");
	}

	public String branchLinkClass() {
		return branch().allKeys().containsObject("branchLinkClass") ?
				(String) branch().valueForKey("branchLinkClass") : 
					(String) d2wContext().valueForKey("branchLinkClass");
	}
	
}