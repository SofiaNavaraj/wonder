package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

public class AjaxObserveFieldPage extends Main {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static NSArray<String> _colors;
	public static NSArray<String> _sizes;
	private String _selectedColor;
	private String _selectedSize;
	
	public AjaxObserveFieldPage(WOContext context) {
        super(context);
    }

	public NSArray<String> colors() {
		return _colors;
	}

	public NSArray<String> sizes() {
		return _sizes;
	}
	
	public String selectedColor() {
		return _selectedColor;
	}

	public void setSelectedColor(String selectedColor) {
		this._selectedColor = selectedColor;
	}

	public String selectedSize() {
		return _selectedSize;
	}

	public void setSelectedSize(String selectedSize) {
		this._selectedSize = selectedSize;
	}

	static {

		_colors = new NSArray<String>(new String[] {
				"Red", "White", "Blue"
		});
		
		_sizes = new NSArray<String>(new String[] {
				"Small", "Medium", "Large"
		});

	}
	
}