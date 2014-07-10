package er.directtoweb.bootstrap.components.buttons;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.directtoweb.SelectPageInterface;

public class ERBSelectButton extends ERBActionButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public interface Keys extends ERBActionButton.Keys {
		public static final String selectButtonLabel =  "selectButtonLabel";
		public static final String classForSelectObjButton = "classForSelectObjButton";
		public static final String idForParentMainContainer = "idForParentMainContainer";
	}
	
	public ERBSelectButton(WOContext context) {
        super(context);
    }

    /**
     * Label for select button
     * <p>
     * Defaults to "Select"
     */
	public String buttonLabel() {
		if (_buttonLabel == null) {
			_buttonLabel = stringValueForBinding(Keys.selectButtonLabel, "Select");
		}
		return _buttonLabel;
	}

	/**
	 * CSS class for the select button
	 * <p>
	 * Defaults to "Button ObjButton SelectObjButton"
	 */
	public String buttonClass() {
		if (_buttonClass == null) {
			_buttonClass = stringValueForBinding(Keys.classForSelectObjButton, "btn btn-default btn-xs");
		}
		return _buttonClass;
	}	
	
	/**
	 * Action performed by the select button
	 */
    public WOComponent selectObjectAction() {
        SelectPageInterface parent = parentSelectPage();
        if(parent != null) {
            parent.setSelectedObject(object());
            return nextPageInPage((D2WPage)parent);
        }
        throw new IllegalStateException("This page is not an instance of SelectPageInterface. I can't select here.");
    }
	
    public String updateContainer() {
    	if (_updateContainer == null) {
			_updateContainer = stringValueForBinding(Keys.idForParentMainContainer);
		}
		return _updateContainer;
    }	
	
	
}