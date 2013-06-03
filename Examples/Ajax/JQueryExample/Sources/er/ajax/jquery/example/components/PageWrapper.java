package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;

public class PageWrapper extends Main {

	private int counter = 0;
	private boolean wasFound = false;
	
	public PageWrapper(WOContext context) {
        super(context);
    }
	
	public void reset() {
		super.reset();
		counter = 0;
		wasFound = false;
	}
	
	@Override
	public boolean isStateless() {
		return true;
	}
    
	public int index() {
		return valueForIntegerBinding("index", -1);
	}
	
	public String navigationClassName() {
		String navigationClassName = null;
		if(! wasFound && index() > -1) {
			if(counter == index()) {
				navigationClassName = "selected";
				wasFound = true;
			} else {
				counter++;
			}
		}
		return navigationClassName;
	}
    
}