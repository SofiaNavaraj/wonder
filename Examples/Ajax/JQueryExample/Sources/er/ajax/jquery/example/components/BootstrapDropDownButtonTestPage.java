package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

@SuppressWarnings("serial")
public class BootstrapDropDownButtonTestPage extends BaseComponent {

	private static NSArray<String> _items;
	public String _item;
	private NSMutableArray<String> _cart;
	
	public BootstrapDropDownButtonTestPage(WOContext context) {
        super(context);
    }

	static {
		_items = new NSArray<String>(new String[] {
				"Surfboard", "Sunscreen", "Airplane Tickets"
		});
	}
	
	public NSArray<String> items() {
		return _items;
	}
	
	public WOActionResults addItemToCart() {
		if(! cart().containsObject(_item)) {
			cart().addObject(_item);
		}
		return context().page();
	}

	public NSMutableArray<String> cart() {
		if(_cart == null) {
			_cart = new NSMutableArray<String>();
		}
		return _cart;
	}

	public void setCart(NSMutableArray<String> cart) {
		this._cart = cart;
	}

	public WOActionResults resetCart() {
		cart().clear();
		return context().page();
	}
}