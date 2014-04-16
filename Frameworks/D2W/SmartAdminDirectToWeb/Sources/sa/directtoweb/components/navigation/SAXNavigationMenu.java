package sa.directtoweb.components.navigation;

import com.webobjects.appserver.WOContext;

import er.extensions.appserver.navigation.ERXNavigationMenu;

public class SAXNavigationMenu extends ERXNavigationMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String _childItemName;
    protected SAXNavigationState _navigationState;

	public SAXNavigationMenu(WOContext context) {
        super(context);
    }
	
	public void reset() {
		_navigationState = null;
		super.reset();
	}
	
    public SAXNavigationState navigationState() {
        if (_navigationState == null)
            _navigationState = SAXNavigationManager.manager().navigationStateForSession(session());
        return _navigationState;
    }
	
	public SAXNavigationManager manager() {
		return SAXNavigationManager.manager();
	}

	public SAXNavigationItem aNavigationItem() {
		return (SAXNavigationItem) aNavigationItem;
	}
	
	public SAXNavigationItem childItem() {
		return (SAXNavigationItem) SAXNavigationManager.manager().navigationItemForName(_childItemName);
	}

}