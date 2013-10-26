package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

import er.ajax.jquery.example.app.Application;
import er.ajax.jquery.example.app.Session;

public class BaseComponent extends ERXComponent {
	public BaseComponent(WOContext context) {
		super(context);
	}
	
	@Override
	public Application application() {
		return (Application)super.application();
	}
	
	@Override
	public Session session() {
		return (Session)super.session();
	}
}
