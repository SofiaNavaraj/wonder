package sa.directtoweb.assignments;

import com.webobjects.directtoweb.D2WContext;
import com.webobjects.eocontrol.EOKeyValueUnarchiver;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import er.directtoweb.assignments.ERDAssignment;
import er.extensions.foundation.ERXDictionaryUtilities;
import er.extensions.foundation.ERXStringUtilities;

public class SADAssignment extends ERDAssignment {

	public SADAssignment(EOKeyValueUnarchiver u) { 
    	super(u);
    }
	
	public SADAssignment(String key, Object value) {
		super(key, value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** holds the array of keys this assignment depends upon */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected static final NSDictionary keys = ERXDictionaryUtilities.dictionaryWithObjectsAndKeys( new Object [] {
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration"}), "idForUpdateContainer",
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration"}), "idForRepetitionContainer",
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration"}), "idForMainContainer",
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration"}), "idForPageTabContainer",
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration", "propertyKey"}), "idForPropertyContainer",
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration", "parentPageConfiguration"}), "idForParentPageConfiguration",
		new NSArray(new Object[] {"task", "entity.name", "pageConfiguration", "parentPageConfiguration"}), "idForParentMainContainer"
	});
	
    /**
     * Static constructor required by the EOKeyValueUnarchiver
     * interface. If this isn't implemented then the default
     * behavior is to construct the first super class that does
     * implement this method. Very lame.
     * @param eokeyvalueunarchiver to be unarchived
     * @return decoded assignment of this class
     */
    public static Object decodeWithKeyValueUnarchiver(EOKeyValueUnarchiver eokeyvalueunarchiver)  {
        return new SADAssignment(eokeyvalueunarchiver);
    }    
    
    /**
     * Implementation of the {@link er.directtoweb.assignments.ERDComputingAssignmentInterface}. This array
     * of keys is used when constructing the
     * significant keys for the passed in keyPath.
     * @param keyPath to compute significant keys for.
     * @return array of context keys this assignment depends upon.
     */
    @SuppressWarnings("rawtypes")
	public NSArray dependentKeys(String keyPath) {
		return (NSArray)keys.valueForKey(keyPath);
	}

	public String idForUpdateContainer(D2WContext c) {
		return "JQAUC_" + idForPageConfiguration(c);
	}	
	
	public String idForPageConfiguration(D2WContext c) {
		String result = c.task() + "_NoEntity";
		if (c.dynamicPage() != null) {
			result = c.dynamicPage();
		} else if (c.entity() != null) {
			result = c.task() + "_" + c.entity().name();
		}
		return result;
	}

	public String idForParentPageConfiguration(D2WContext c) {
		String parentConfig = (String)c.valueForKey("parentPageConfiguration");
		if (parentConfig == null) {
			parentConfig = "NO_PARENT_CONFIGURATION_" + idForPageConfiguration(c);
		}
		return parentConfig;
	}
	
	// IDs
	
	public String idForMainContainer(D2WContext c) {
		return "MUC_" + idForPageConfiguration(c);
	}
	
	public String idForParentMainContainer(D2WContext c) {
		return "MUC_" + idForParentPageConfiguration(c);
	}
	
	public String idForRepetitionContainer(D2WContext c) {
		return "RUC_" + idForPageConfiguration(c);
	}
	
	public String idForPageTabContainer(D2WContext c) {
		return "PTUC_" + idForPageConfiguration(c);
	}
	
	public String idForPropertyContainer(D2WContext c) {
		return "PCUC_" + idForPageConfiguration(c) + "_" + cssClassForPropertyKey(c.propertyKey());
	}	
		
	/**
	 * Cleans the propertyKey, removing "." and capitalizing key path components
	 * @param key
	 */
	public String cssClassForPropertyKey(String key) {
		if (key != null) {
			String temp = "";
			if (key.indexOf(".") != -1) {
				NSArray<String> components = NSArray.componentsSeparatedByString(key, ".");
				for (String string : components) {
					string = ERXStringUtilities.capitalize(string);
					temp = temp + string;
				}
			} else {
				temp = ERXStringUtilities.capitalize(key);
			}
			key = temp;
		}
		return key;
	}
	
	
}
