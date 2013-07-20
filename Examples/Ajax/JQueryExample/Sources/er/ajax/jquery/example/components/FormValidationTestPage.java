package er.ajax.jquery.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

public class FormValidationTestPage extends BaseComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String email;
	public String theName;
	public String phone;
	public String favoriteColor;
	
	public FormValidationTestPage(WOContext context) {
        super(context);
    }

	public WOActionResults submit() {
		return null;
	}

}