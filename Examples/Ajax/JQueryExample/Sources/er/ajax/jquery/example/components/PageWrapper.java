package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

public class PageWrapper extends Main {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counter = 0;
	private boolean wasFound = false;
	
	public PageWrapper(WOContext context) {
        super(context);
    }
	
	public void appendToResponse(WOResponse response, WOContext context) {
		super.appendToResponse(response, context);		
	}
	
	public void sleep() {
		super.sleep();
		counter = 0;
		wasFound = false;
	}
	
	@Override
	public boolean isStateless() {
		return false;
	}
    
	@Override
	public boolean synchronizesVariablesWithBindings() {
		// TODO Auto-generated method stub
		return false;
	}

	public int index() {
		return valueForIntegerBinding("index", -1);
	}
	
	public String navigationClassName() {
		String navigationClassName = null;
		if(! wasFound && index() > -1) {
			if(counter == index()) {
				navigationClassName = "active";
				wasFound = true;
			} else {
				counter++;
			}
		}
		return navigationClassName;
	}
    
}