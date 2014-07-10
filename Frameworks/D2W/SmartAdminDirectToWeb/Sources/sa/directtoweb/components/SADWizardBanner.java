package sa.directtoweb.components;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import er.directtoweb.ERD2WContainer;
import er.extensions.components.ERXStatelessComponent;

public class SADWizardBanner extends ERXStatelessComponent {

	public static interface Keys {
		public static final String currentStep = "currentStep";
		public static final String currentTab = "currentTab";
		public static final String tabSectionsContents = "tabSectionsContents";
	}
	
	private ERD2WContainer _currentTab;
	private NSArray<ERD2WContainer> _tabSectionsContents;
	
	public ERD2WContainer tabItem;
	public int index;

	public SADWizardBanner(WOContext context) {
        super(context);
    }

	public final WOComponent self() {
		return this;
	}

	@Override
	public void reset() {
		super.reset();
		_currentTab = null;
		_tabSectionsContents = null;
	}
	
	public int currentStep() {
		return intValueForBinding(Keys.currentStep, 1);
	}
	
	public ERD2WContainer currentTab() {
		if(_currentTab == null) {
			_currentTab = (ERD2WContainer) objectValueForBinding(Keys.currentTab);
		}
		return _currentTab;
	}
	
	@SuppressWarnings("unchecked") 
	public NSArray<ERD2WContainer> tabSectionsContents() {
		if(_tabSectionsContents == null) {
			_tabSectionsContents = (NSArray<ERD2WContainer>) objectValueForBinding(Keys.tabSectionsContents);
		}
		return _tabSectionsContents;
	}

	public String badgeClassName() {
		return tabItem == currentTab() ? "badge badge-info" : 
			index > tabSectionsContents().indexOf(currentTab()) ? "badge" : "badge badge-success";
	}

	public String listItemClassName() {
		return tabItem == currentTab() ? "active" : 
			index > tabSectionsContents().indexOf(currentTab()) ? null : "complete";
	}

	public Integer stepNumber() {
		return index + 1;
	}
	
}