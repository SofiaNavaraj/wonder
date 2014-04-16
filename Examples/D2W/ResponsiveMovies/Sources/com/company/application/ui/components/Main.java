package com.company.application.ui.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;

import er.extensions.appserver.ERXResponseRewriter;
import er.extensions.components.ERXStatelessComponent;

public class Main extends ERXStatelessComponent {

	private static final long serialVersionUID = 1L;
	private String _username;
	private String _password;
	private String _errorMessage;
	private String _errorMessageType;
	
	public Main(WOContext context) {
		super(context);
	}
	
	@Override
	public void appendToResponse(WOResponse response, WOContext context) {
		super.appendToResponse(response, context);
		_addRequiredWebResources(response, context);
	}

	protected void _addRequiredWebResources(WOResponse response, WOContext context) {

		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/bootstrap.min.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/font-awesome.min.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/smartadmin-production.css");
		ERXResponseRewriter.addStylesheetResourceInHead(response, context, "SmartAdminUI", "css/smartadmin-skins.css");
		
	}

	public void setUsername(String username) {
		_username = username;
	}

	public String username() {
		return _username;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public String password() {
		return _password;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public String errorMessage() {
		return _errorMessage;
	}

	public String errorMessageType() {
		return _errorMessageType;
	}

	public void setErrorMessageType(String errorMessageType) {
		this._errorMessageType = errorMessageType;
	}
	
}
