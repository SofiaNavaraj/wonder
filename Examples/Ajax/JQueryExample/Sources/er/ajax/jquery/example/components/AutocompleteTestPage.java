package er.ajax.jquery.example.components;

import java.util.Enumeration;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class AutocompleteTestPage extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public NSArray allValues;

	public String value;
	public String value2;
	public Word currentValue;
	public Word selectedValue2;

	public AutocompleteTestPage(WOContext context) {
		super(context);
        allValues = ExampleDataFactory.allWords();
	}

	/**
	 * This method gets called after every keystroke, we check the value
	 * variable and return the 10 entries in allValues that contain this value.
	 */
	@SuppressWarnings("rawtypes")
	public NSArray currentValues(String v) {
		NSMutableArray<Word> result = new NSMutableArray<Word>();
		for (Enumeration e = allValues.objectEnumerator(); e.hasMoreElements()
				&& result.count() < 10;) {
			Word c = (Word) e.nextElement();
			if (v == null || c.name.toLowerCase().indexOf(v.toLowerCase()) >= 0) {
				result.addObject(c);
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public NSArray currentValues() {
		return currentValues(value);
	}

	@SuppressWarnings("rawtypes")
	public NSArray currentValues2() {
		return currentValues(value2);
	}

	public WOActionResults submitted() {
		System.out.println("AutoCompleteExample.submitted: " + value2 + ", "
				+ selectedValue2);
		return null;
	}

	public WOActionResults resetWord() {
		selectedValue2 = null;
		value2 = null;
		return null;
	}


}
