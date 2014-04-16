package sa.directtoweb.components;

import com.webobjects.appserver.WOContext;

import er.directtoweb.components.ERDActionBar;

public class SADActionBar extends ERDActionBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8088094544786090564L;

	public SADActionBar(WOContext context) {
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