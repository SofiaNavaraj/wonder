package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;

public class AccordionTestPage extends BaseComponent {
    private int index;

	public AccordionTestPage(WOContext context) {
        super(context);
    }

	/**
	 * @return the index
	 */
	public int index() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	public String label() {
		return "Tab " + index();
	}
}