package sa.directtoweb.components.navigation;

import com.webobjects.foundation.NSDictionary;

import er.extensions.appserver.navigation.ERXNavigationItem;

public class SAXNavigationItem extends ERXNavigationItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _iconClassNames;
	
	public SAXNavigationItem(NSDictionary values) {
		super(values);
		_iconClassNames = (String) values.valueForKey("iconClassNames");
	}

	public String iconClassNames() {
		return _iconClassNames;
	}

	public void setIconClassNames(String iconClassName) {
		this._iconClassNames = iconClassName;
	}

}
