package er.directtoweb.bootstrap.components;

import com.webobjects.appserver.WOContext;

import er.directtoweb.bootstrap.components.header.ERBD2WHeader;

public class ERBD2WTextHeader extends ERBD2WHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBD2WTextHeader(WOContext context) {
        super(context);
    }

	@Override
	public String headerString() {
		return stringValueForBinding(Keys.displayNameForPageConfiguration);
	}
	
}