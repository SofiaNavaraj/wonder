package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class AjaxTabTestPage extends BaseComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tab _selectedTab;
	private NSArray<Tab> _tabs;
	
	public AjaxTabTestPage(WOContext context) {
        super(context);
    }
	
	public Tab selectedTab() {
		if(_selectedTab == null) {
			_selectedTab = tabs().get(0);
		}
		return _selectedTab;
	}

	public void setSelectedTab(Tab selectedTab) {
		this._selectedTab = selectedTab;
	}

	public NSArray<Tab> tabs() {
		if(_tabs == null) {
			NSMutableArray<Tab> tabArray = new NSMutableArray<Tab>();
			for(char letter = 'A'; letter < 'D'; letter++) {
				String aLetter = Character.toString(letter);
				Tab aTab = new Tab(aLetter, "Tab" + aLetter);
				tabArray.add(aTab);
			}
			_tabs = tabArray;
		}
		return _tabs;
	}

	public void setTabs(NSArray<Tab> tabs) {
		this._tabs = tabs;
	}

	public class Tab {
		
		private String _displayName;
		private String _componentName;
		
		public Tab() {
			
		}
		
		public Tab(String aDisplayName, String aComponentName) {
			_displayName = aDisplayName;
			_componentName = aComponentName;
		}
		
		public String displayName() {
			return _displayName;
		}
		public void setDisplayName(String displayName) {
			this._displayName = displayName;
		}
		public String componentName() {
			return _componentName;
		}
		public void setComponentName(String componentName) {
			this._componentName = componentName;
		}
		
		
	}


}