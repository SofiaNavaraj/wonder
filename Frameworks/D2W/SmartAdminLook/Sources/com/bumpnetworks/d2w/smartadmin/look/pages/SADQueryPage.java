package com.bumpnetworks.d2w.smartadmin.look.pages;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.directtoweb.ERD2WUtilities;
import com.webobjects.directtoweb.ListPageInterface;
import com.webobjects.foundation.NSValidation;

import er.directtoweb.delegates.ERDQueryValidationDelegate;
import er.directtoweb.pages.templates.ERD2WQueryPageTemplate;
import er.extensions.foundation.ERXValueUtilities;
import er.extensions.localization.ERXLocalizer;

public class SADQueryPage extends ERD2WQueryPageTemplate {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface Keys extends ERD2WQueryPageTemplate.Keys {
		public static final String parentPageConfiguration = "parentPageConfiguration";
		public static final String useAjaxControlsWhenEmbedded = "useAjaxControlsWhenEmbedded";
		public static final String allowInlineEditing = "allowInlineEditing";
		public static final String shouldShowCancelButton = "shouldShowCancelButton";
	}	
	
	public SADQueryPage(WOContext context) {
        super(context);
    }

	/** 
	 * Perform the return action. Overridden to handle perform the correct behaviour if the page is in-line 
	 * (i.e: useAjaxControlsWhenEmbedded returns true)
	 */
	@Override
	public WOComponent returnAction() {
		WOComponent page = super.returnAction();
		boolean useAjaxWhenEmbedded = ERXValueUtilities.booleanValue(d2wContext().valueForKey(Keys.useAjaxControlsWhenEmbedded));
		if (useAjaxWhenEmbedded) {
			if (parent() != null) {
				D2WPage parent = (D2WPage)ERD2WUtilities.enclosingPageOfClass(this, D2WPage.class);
				if (parent != null) 
					parent.takeValueForKeyPath(null, "d2wContext.inlineTask");
			}
		}
		return page;
	}	
	
	/**
	 * Perform the query action. Overridden to handle in-line results display
	 * (i.e: if allowInlineEditing is true, then we will return this page)
	 */
	@Override
	public WOComponent queryAction() {
		WOComponent nextPage = null;

		// If we have a validation delegate, validate the query values before actually performing the query.
		ERDQueryValidationDelegate queryValidationDelegate = queryValidationDelegate();
		if (queryValidationDelegate != null) {
			clearValidationFailed();
			setErrorMessage(null);
			try {
				queryValidationDelegate.validateQuery(this);
			} catch (NSValidation.ValidationException ex) {
				setErrorMessage(ERXLocalizer.currentLocalizer().localizedTemplateStringForKeyWithObject("CouldNotQuery", ex));
				validationFailedWithException(ex, null, "queryExceptionKey");
			}
			if (hasErrors()) {
				return context().page();
			}
		}

		if (ERXValueUtilities.booleanValue(d2wContext().valueForKey("showListInSamePage"))) {
			setShowResults(true);
		} else {
			nextPage = nextPageFromDelegate();
			boolean allowInlineEditing = ERXValueUtilities.booleanValue(d2wContext().valueForKey("allowInlineEditing"));
			if (nextPage == null && !allowInlineEditing) {
				String listConfigurationName = (String) d2wContext().valueForKey("listConfigurationName");
				ListPageInterface listpageinterface;
				if (listConfigurationName != null) {
					listpageinterface = (ListPageInterface) D2W.factory().pageForConfigurationNamed(listConfigurationName, session());
				} else {
					listpageinterface = D2W.factory().listPageForEntityNamed(entity().name(), session());
				}
				listpageinterface.setDataSource(queryDataSource());
				listpageinterface.setNextPage(context().page());
				nextPage = (WOComponent) listpageinterface;
			}
		}
		return nextPage;
	}
	
}