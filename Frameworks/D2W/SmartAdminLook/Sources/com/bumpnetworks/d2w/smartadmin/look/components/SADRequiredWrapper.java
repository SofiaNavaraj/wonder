package com.bumpnetworks.d2w.smartadmin.look.components;

import com.webobjects.appserver.WOContext;
import com.bumpnetworks.d2w.smartadmin.components.SADComponent;

import er.extensions.foundation.ERXStringUtilities;

public class SADRequiredWrapper extends SADComponent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _wrapperClass;
	private String _wrapperId;
	private String _watchContainerID;
	private String _formName;
	private Boolean _showForm;
	private Boolean _showHelp;
	
	public SADRequiredWrapper(WOContext context) {
        super(context);
    }
	
	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@Override
	public void sleep() {
		super.sleep();
		_wrapperClass = null;
		_wrapperId = null;
		_watchContainerID = null;
		_formName = null;
		_showForm = null;
		_showHelp = null;
	}
	
	public String wrapperClass() {
		if(_wrapperClass == null) {
			_wrapperClass = stringValueForBinding("class");
		}
		return _wrapperClass;
	}

	public void setWrapperClass(String wrapperClass) {
		this._wrapperClass = wrapperClass;
	}

	public String wrapperId() {
		if(_wrapperId == null) {
			_wrapperId = stringValueForBinding("id");
		}
		return _wrapperId;
	}

	public void setWrapperId(String wrapperId) {
		this._wrapperId = wrapperId;
	}

	public String watchContainerID() {
		if (_watchContainerID == null) {
			_watchContainerID = stringValueForBinding("watchedContainerID");
		}
		return _watchContainerID;
	}

	public void setWatchContainerID(String watchContainerID) {
		this._watchContainerID = watchContainerID;
	}

	public String formName() {
		if (_formName == null) {
			_formName = ERXStringUtilities.capitalize(d2wContext().task()) + "Form";	
		}
		return _formName;
	}

	public void setFormName(String formName) {
		this._formName = formName;
	}

	public Boolean showForm() {
		Integer temp = (Integer)d2wContext().valueForKey("hasForm");
		boolean hideForm = booleanValueForBinding("hideForm");
		boolean result = (!hideForm && temp != null && temp.intValue() > 0);
		_showForm = Boolean.valueOf(result);
		return _showForm;
	}

	public void setShowForm(Boolean showForm) {
		this._showForm = showForm;
	}

	public Boolean showHelp() {
		return _showHelp;
	}

	public void setShowHelp(Boolean showHelp) {
		if (_showHelp == null) {
			_showHelp = Boolean.valueOf(d2wContext().valueForKey("parentConfigurationName") != null);
		}
		this._showHelp = showHelp;
	}
	
	
	
}