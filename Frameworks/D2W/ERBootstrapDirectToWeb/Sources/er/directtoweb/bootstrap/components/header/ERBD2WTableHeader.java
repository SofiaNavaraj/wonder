package er.directtoweb.bootstrap.components.header;

import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WContext;

import er.ajax.AjaxSortOrder;
import er.extensions.foundation.ERXValueUtilities;

public class ERBD2WTableHeader extends AjaxSortOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ERBD2WTableHeader(WOContext context) {
		super(context);
	}

	public D2WContext d2wContext() {
		return (D2WContext) valueForBinding("d2wContext");
	}

	// OVERRIDES

	@Override
	public String displayKey() {
		return (String) d2wContext().valueForKey("displayNameForProperty");
	}

	@Override
	public String key() {
		return (String) d2wContext().valueForKey("sortKeyForList");
	}

	@Override
	public boolean caseInsensitive() {
		return ERXValueUtilities.booleanValue(d2wContext().valueForKey(
				"sortCaseInsensitive"));
	}

	public String toggleLinkClassName() {

		String toggleLinkClassName = null;
		int state = currentState();

		switch (state) {
		case SortedAscending:
			toggleLinkClassName = "sorting_asc";
			break;
		case SortedDescending:
			toggleLinkClassName = "sorting_desc";
			break;
		default:
			toggleLinkClassName = "sorting";
			break;
		}

		return toggleLinkClassName;

	}

}