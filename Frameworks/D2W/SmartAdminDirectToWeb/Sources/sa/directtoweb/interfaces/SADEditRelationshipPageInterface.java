package sa.directtoweb.interfaces;

import com.webobjects.directtoweb.EditRelationshipPageInterface;
import com.webobjects.foundation.NSArray;

public interface SADEditRelationshipPageInterface extends
		EditRelationshipPageInterface {

	/**
	 * Returns an NSArray containing the masterObject and relationshipKey
	 * 
	 * @return NSArray with the masterObject (index 0) and relationshipKey (index 1)
	 */
	public NSArray<?> masterObjectAndRelationshipKey();
	
	/**
	 * Sets the masterObject and relationshipKey
	 * 
	 * @param a NSArray with the masterObject (index 0) and relationshipKey (index 1)
	 */
	public void setMasterObjectAndRelationshipKey(NSArray<?> a);	
	
}
