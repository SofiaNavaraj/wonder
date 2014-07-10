package com.bumpnetworks.d2w.smartadmin.look.pages;

import org.apache.commons.lang.ObjectUtils;

import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WContext;
import com.webobjects.eoaccess.EOEntity;

import er.directtoweb.pages.templates.ERD2WWizardCreationPageTemplate;
import er.extensions.localization.ERXLocalizer;

public class SADWizardCreationPage extends ERD2WWizardCreationPageTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface Keys extends ERD2WWizardCreationPageTemplate.Keys {
		public static final String useAjaxControlsWhenEmbedded = "useAjaxControlsWhenEmbedded";
		public static final String parentPageConfiguration = "parentPageConfiguration";
		public static final String idForParentMainContainer = "idForParentMainContainer";
		public static final String idForMainContainer = "idForMainContainer";
	}
	
	public SADWizardCreationPage(WOContext context) {
        super(context);
    }

	@Override
	public void awake() {
		super.awake();
		clearValidationFailed();
	}

	// What follows is a hack.
	// I am not proud of it, but there it is.
	// This is necessary because the wizard component will blow chunks
	// if you don't clear its tabSectionContents if the d2wContext's entity
	// changes when embedded, in-line, and updated with ajax requests.
	// davidleber
	
	private EOEntity _cachedEntity;
	
	@Override
	public D2WContext d2wContext() {
		D2WContext result = super.d2wContext();
		if (_cachedEntity == null) {
			_cachedEntity = result.entity();
		} else if (ObjectUtils.notEqual(_cachedEntity, result.entity())) {
			clearTabSectionsContents();
			_cachedEntity = result.entity();
		}
		return super.d2wContext();
	}

	
	
}