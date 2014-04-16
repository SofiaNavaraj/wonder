package er.ajax.jquery.example.app;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WORequest;

import er.ajax.jquery.example.components.HomePage;
import er.extensions.appserver.ERXDirectAction;

public class DirectAction extends ERXDirectAction {

	public DirectAction(WORequest request) {
		super(request);
	}

	@Override
	public WOActionResults defaultAction() {
		session();
		return pageWithName(HomePage.class);
	}
	
	public Application application() {
		return (Application) WOApplication.application();
	}
	
	@Override
	public Session session() {
		return (Session)super.session();
	}
}
