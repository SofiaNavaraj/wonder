package com.bumpnetworks.d2w.smartadmin.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WContext;

import er.extensions.components.ERXComponent;

public class SADComponent extends ERXComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private D2WContext _d2wContext;
	
	public SADComponent(WOContext context) {
        super(context);
    }

	public D2WContext d2wContext() {
		if(_d2wContext == null) {
			_d2wContext = (D2WContext) objectValueForBinding("d2wContext");
		}
		return _d2wContext;
	}

	public void setD2wContext(D2WContext d2wContext) {
		this._d2wContext = d2wContext;
	}
}