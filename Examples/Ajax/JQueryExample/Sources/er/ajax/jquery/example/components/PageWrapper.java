package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.ajax.jquery.JQAjaxUtils;

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

		JQAjaxUtils.addStylesheetResourceInHead(context, response, "JQueryBootstrap", "css/bootstrap.min.css");
		JQAjaxUtils.addStylesheetResourceInHead(context, response, "app", "css/flat-ui.css");
		
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
				navigationClassName = "active";
				wasFound = true;
			} else {
				counter++;
			}
		}
		return navigationClassName;
	}
    
}