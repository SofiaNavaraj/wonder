package er.directtoweb.bootstrap.components.navigation;

import com.webobjects.appserver.WOContext;
import er.extensions.appserver.navigation.ERXNavigationMenu;

public class ERBXNavigationMenu extends ERXNavigationMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBXNavigationMenu(WOContext context) {
        super(context);
    }

	public String navigationMenuClassNames() {
		return stringValueForBinding("navigationMenuClassNames",  "navbar navbar-default");
	}

}