package sa.directtoweb.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WODisplayGroup;
import com.webobjects.foundation.NSArray;

import er.directtoweb.components.ERDCustomComponent;

public class SAD2WBatchSizeControl extends ERDCustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static NSArray<Integer> _batchSizes;
	
	public SAD2WBatchSizeControl(WOContext context) {
        super(context);
    }

	static {
		_batchSizes = new NSArray<Integer>(new Integer[] {
				10, 20, 50, 100
		});
	}
	
	public NSArray<Integer> batchSizes() {
		return _batchSizes;
	}
	
	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}
	
	public WODisplayGroup displayGroup() {
		return (WODisplayGroup)valueForBinding("displayGroup");
	}
	
	
}