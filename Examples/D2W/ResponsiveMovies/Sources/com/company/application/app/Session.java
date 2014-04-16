package com.company.application.app;

import sa.directtoweb.components.navigation.SAXNavigationManager;

import com.webobjects.appserver.WOComponent;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.EditPageInterface;
import com.webobjects.directtoweb.ListPageInterface;
import com.webobjects.directtoweb.QueryPageInterface;
import com.webobjects.eoaccess.EODatabaseDataSource;
import com.webobjects.eocontrol.EODataSource;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;

import er.extensions.appserver.ERXSession;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXEOControlUtilities;

public class Session extends ERXSession {
	private static final long serialVersionUID = 1L;

	private MainNavigationController _navController;
	
	public Session() {
	}
	
	public MainNavigationController navController() {
		if (_navController == null) {
			_navController = new MainNavigationController(this);
		}
		return _navController;
	}


}
