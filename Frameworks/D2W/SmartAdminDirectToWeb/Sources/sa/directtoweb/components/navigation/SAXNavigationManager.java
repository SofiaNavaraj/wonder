package sa.directtoweb.components.navigation;

import java.util.Enumeration;

import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation._NSUtilities;

import er.extensions.appserver.navigation.ERXNavigationManager;
import er.extensions.foundation.ERXPatcher;

public class SAXNavigationManager extends ERXNavigationManager {

	protected static SAXNavigationManager manager;
	protected SAXNavigationItem rootNavigationItem;

	public static SAXNavigationManager manager() {
		if (manager == null)
			manager = new SAXNavigationManager();
		return manager;
	}

	public SAXNavigationState navigationStateForSession(WOSession session) {
		SAXNavigationState state = (SAXNavigationState) session
				.objectForKey(navigationStateSessionKey());
		if (state == null) {
			state = new SAXNavigationState();
			session.setObjectForKey(state, navigationStateSessionKey());
		}
		return state;
	}

	public SAXNavigationItem rootNavigationItem() {
		return rootNavigationItem;
	}

	public SAXNavigationItem navigationItemForName(String name) {
		return (SAXNavigationItem) navigationItemsByName.objectForKey(name);
	}

	public SAXNavigationItem newNavigationItem(NSDictionary dict) {
		String className = (String) dict
				.objectForKey("navigationItemClassName");
		if (className != null) {
			Class c = ERXPatcher.classForName(className);
			return (SAXNavigationItem) _NSUtilities.instantiateObject(c,
					new Class[] { NSDictionary.class }, new Object[] { dict },
					true, true);
		}
		return new SAXNavigationItem(dict);
	}

	protected void setNavigationItems(NSArray items) {
		NSMutableDictionary itemsByName = new NSMutableDictionary();
		if (items != null && items.count() > 0) {
			for (Enumeration e = items.objectEnumerator(); e.hasMoreElements();) {
				SAXNavigationItem item = (SAXNavigationItem) e.nextElement();
				if (itemsByName.objectForKey(item.name()) != null) {
					log.warn("Attempting to register multiple navigation items for the same name: "
							+ item.name());
				} else {
					itemsByName.setObjectForKey(item, item.name());
					if (item.name().equals("Root"))
						rootNavigationItem = item;
				}
			}
		}
		if (rootNavigationItem == null)
			log.warn("No root navigation item set. You need one.");
		navigationItemsByName = itemsByName.immutableClone();
	}

}
