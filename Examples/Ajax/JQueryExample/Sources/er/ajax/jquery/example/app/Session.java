package er.ajax.jquery.example.app;

import er.extensions.appserver.ERXSession;

public class Session extends ERXSession {
	private static final long serialVersionUID = 1L;

	public Session() {
		new RuntimeException("Session created...").printStackTrace();
	}
	
	@Override
	public Application application() {
		return (Application) super.application();
	}
	
}
