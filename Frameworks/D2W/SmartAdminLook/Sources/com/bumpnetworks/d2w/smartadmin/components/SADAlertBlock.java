package com.bumpnetworks.d2w.smartadmin.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSDictionary;

public class SADAlertBlock extends SADComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SADAlertBlock(WOContext context) {
        super(context);
    }

	@Override
	public void reset() {
		super.reset();
	}
	
	@Override
	public boolean isStateless() {
		return true;
	}
	
	public boolean showMessageBlock() {
		return (messages() != null && messages().allKeys().count() > 0) || (message() != null && message().length() > 0);
	}

	public String alertClass() {
		return stringValueForBinding("alertClass");
	}

	public String icon() {
		return stringValueForBinding("icon");
	}

	public String message() {
		return stringValueForBinding("message");
	}

	public NSDictionary<?, ?> messages() {
		return valueForNSDictionaryBindings("messages", null);
	}

}