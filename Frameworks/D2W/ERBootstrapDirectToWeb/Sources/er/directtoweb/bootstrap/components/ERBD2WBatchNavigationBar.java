package er.directtoweb.bootstrap.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WContext;

import er.ajax.AjaxFlickrBatchNavigation;

public class ERBD2WBatchNavigationBar extends AjaxFlickrBatchNavigation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ERBD2WBatchNavigationBar(WOContext context) {
        super(context);
    }

	public D2WContext d2wContext() {
		return (D2WContext) valueForBinding("d2wContext");
	}

}