package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

public class SelectPickerPage extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String _item;
	public String _selectedItem;
	
	public SelectPickerPage(WOContext context) {
        super(context);
    }
	
	public NSArray<String> list() {
		return new NSArray<String>(new String[] {"Banana", "Guava", "Lilikoi", "Mango", "Pineapple" });
	}
	
	public WOActionResults selectFruit() {
		return context().page();
	}    
    
}