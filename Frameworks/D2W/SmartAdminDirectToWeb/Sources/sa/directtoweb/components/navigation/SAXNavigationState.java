package sa.directtoweb.components.navigation;

import java.util.Enumeration;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCodingAdditions;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.appserver.navigation.ERXNavigationState;
import er.extensions.foundation.ERXValueUtilities;

public class SAXNavigationState extends ERXNavigationState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SAXNavigationState() {
		// TODO Auto-generated constructor stub
        super();
	}
	
    public NSArray navigationItemsForLevel(int level, NSKeyValueCodingAdditions context) {
        SAXNavigationItem levelRoot = null;
        if (level == 1) {
            levelRoot = SAXNavigationManager.manager().rootNavigationItem();
        } else if (state().count() > level - 2) {
            levelRoot = SAXNavigationManager.manager().navigationItemForName(level(level - 2));
            if (log.isDebugEnabled())
                log.debug("Root name for level: " + (level - 2) + " state: " + state() + "root: "
                          + (levelRoot != null ? levelRoot.name() : "<NULL>"));
        }
        NSArray children = null;
        if (levelRoot != null) {
            boolean hasChildrenConditions = levelRoot.childrenConditions().count() != 0;
            boolean meetsChildrenConditions = true;
            if (hasChildrenConditions) {
                for (Enumeration e = levelRoot.childrenConditions().objectEnumerator(); e.hasMoreElements();) {
                    String aCondition = (String)e.nextElement();
                    meetsChildrenConditions = ERXValueUtilities.booleanValue(context.valueForKeyPath(aCondition));
                    if (!meetsChildrenConditions)
                        break;
                }
            }
            if (meetsChildrenConditions) {// only want to do this if childrenConditions are met, or if there aren't any children conditions
                if (levelRoot.children() != null)
                    children = levelRoot.children();
                else if (levelRoot.childrenBinding() != null) {
                    Object o = context.valueForKeyPath(levelRoot.childrenBinding());
                    if (o != null && o instanceof NSArray)
                        children = (NSArray)o;
                    else if (o != null && o instanceof String) {
                        children = (NSArray)levelRoot.childrenChoices().objectForKey(o);
                        if (children == null)
                            log.warn("For nav core object: " + levelRoot + " and child binding: " + levelRoot.childrenBinding()
                                     + " couldn't find children for choice key: " + (String)o);
                    } else if (o instanceof Boolean) {
                    	String s = Boolean.toString((Boolean)o);
                    	children = (NSArray)levelRoot.childrenChoices().objectForKey(s);
                    } else {
                        log.warn("For nav core object: " + levelRoot + " and child binding: " + levelRoot.childrenBinding()
                                 + " recieved binding object: " + o);
                    }
                }
            }
        }
        if (children == null)
            children = NSArray.EmptyArray;
        if (children.count() > 0) {
            NSMutableArray childNavItems = new NSMutableArray();
            for (Enumeration e = children.objectEnumerator(); e.hasMoreElements();) {
                String childName = (String)e.nextElement();
                SAXNavigationItem item =SAXNavigationManager.manager().navigationItemForName(childName);
                if (item != null)
                    childNavItems.addObject(item);
                else
                    log.warn("Unable to find navigation item for name: " + childName);
            }
            children = childNavItems;
        }
        return children;
    }	

}
