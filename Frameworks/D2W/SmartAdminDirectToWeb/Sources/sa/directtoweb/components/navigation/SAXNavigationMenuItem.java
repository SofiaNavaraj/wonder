package sa.directtoweb.components.navigation;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import er.extensions.appserver.navigation.ERXNavigationItem;
import er.extensions.appserver.navigation.ERXNavigationMenuItem;

public class SAXNavigationMenuItem extends ERXNavigationMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String childIdentifier;
	protected SAXNavigationItem _navigationItem;
    protected SAXNavigationState _navigationState;

	public SAXNavigationMenuItem(WOContext context) {
        super(context);
    }
	
	public void reset() {
		_navigationItem = null;
		_navigationState = null;
		super.reset();
	}

    public SAXNavigationItem navigationItem() {
        if (_navigationItem==null) {
            _navigationItem = (SAXNavigationItem)valueForBinding("navigationItem");
            if(_navigationItem == null) {
                String name = (String)valueForBinding("navigationItemName");
                if(name != null) {
                    _navigationItem = SAXNavigationManager.manager().navigationItemForName(name);
                } else {
                    log.warn("Navigation unset: " + name);
                    _navigationItem = SAXNavigationManager.manager().newNavigationItem(new NSDictionary(name, "name"));
                }
            }
        }
        return _navigationItem;
    }
	
    public SAXNavigationState navigationState() {
        if (_navigationState == null)
            _navigationState = SAXNavigationManager.manager().navigationStateForSession(session());
        return _navigationState;
    }	
    
	public SAXNavigationItem childNavigationItem() {
		return SAXNavigationManager.manager().navigationItemForName(childIdentifier);
	}

	public boolean isFirstLevel() {
		return level() == 1;
	}
	
    public boolean isSelected() {
        if (_isSelected == null) {
            NSArray navigationState = navigationState().state();
            _isSelected=!isDisabled() && navigationState != null && navigationState.containsObject(navigationItem().name()) ? Boolean.TRUE : Boolean.FALSE;

        }
        return _isSelected.booleanValue();
    }

	
	public String listItemClassNames() {
		StringBuilder sb = new StringBuilder();
		if(isSelected() && navigationState().state().lastObject().equals(navigationItem().name())) {
			sb.append("active");
		} else if(isSelected()) {
			sb.append("open");
		}
		return sb.toString();
	}

	public int nextLevel() {
		return level() + 1;
	}
}