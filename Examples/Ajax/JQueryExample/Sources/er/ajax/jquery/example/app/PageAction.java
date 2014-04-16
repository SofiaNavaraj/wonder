package er.ajax.jquery.example.app;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;

import er.extensions.appserver.ERXDirectAction;

public class PageAction extends ERXDirectAction {

	public PageAction(WORequest request) {
		super(request);
	}

	@Override
	public WOActionResults performActionNamed(String actionName) {

		if(! context().hasSession()) {
			session();
		}
		
		return pageWithName(actionName);

	}

	
	
}
