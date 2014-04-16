package sa.directtoweb.components.header;

import com.webobjects.appserver.WOContext;

public class SAD2WTextHeader extends SAD2WHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4949691475292852331L;

	public SAD2WTextHeader(WOContext context) {
        super(context);
    }

	@Override
	public String headerString() {
		return stringValueForBinding(Keys.displayNameForPageConfiguration);
	}
}