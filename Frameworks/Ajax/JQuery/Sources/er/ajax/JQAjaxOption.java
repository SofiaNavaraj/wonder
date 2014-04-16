package er.ajax;

import com.webobjects.appserver.WOAssociation;
import com.webobjects.appserver.WOComponent;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;



public class JQAjaxOption extends AjaxOption {
		
	  public static final AjaxOption.Type FUNCTION_3 = new AjaxOption.Type(10);// Function with three args

	
	public JQAjaxOption(String name) {
		super(name);
	}

	  /*
	   * Creates an AjaxOption with:
	   * <ul>
	   * 	<li>Name: name</li>
	   * 	<li>Binding Name: name</li>
	   * 	<li>Default Value: none</li>
	   * 	<li>Type: type</li>
	   * </ul> 
	   */
	  public JQAjaxOption(String name, AjaxOption.Type type) {
		super(name, type);
	  }
	
	  
	  
	/**
	 * @param obj the Object to return an AjaxValue for
	 * @return an AjaxValue encapsulating obj with the same type as this AjaxOption
	 */
	public JQAjaxValue valueForObject(Object obj) {
		return new JQAjaxValue(type(), obj);
	}
	
	  /*
	   * Creates an AjaxOption with:
	   * <ul>
	   * 	<li>Name: name</li>
	   * 	<li>Binding Name: name</li>
	   * 	<li>Default Value: defaultValue</li>
	   * 	<li>Type: type</li>
	   * </ul> 
	   */
	  public JQAjaxOption(String name, Object defaultValue, AjaxOption.Type type) {
		super(name, name, defaultValue, type);
	  }	
	
	  /**
	   * @param ajaxOptions list of AjaxOption to evaluate on component
	   * @param component WOComponent to get binding value from
	   * @param associations dictionary of associations to get WOAssocation providing value from
	   *
	   * @return dictionary produced by evaluating the array of AjaxOption on a WOComponent and adding the resulting name and JavaScript formatted values
	   */
	  public static NSMutableDictionary<String, String> createAjaxOptionsDictionary(NSArray<AjaxOption> ajaxOptions, WOComponent component, NSDictionary<String, ? extends WOAssociation> associations) {
	    NSMutableDictionary<String, String> optionsDictionary = new NSMutableDictionary<String, String>();
	    for (AjaxOption ajaxOption : ajaxOptions) {
	      ajaxOption.addToDictionary(component, associations, optionsDictionary);
	    }
	    return optionsDictionary;
	  }	
	
}
