package sa.directtoweb.components.numbers;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.numbers.ERD2WQueryNumberRange;

public class SAD2WQueryNumberRange extends ERD2WQueryNumberRange {

	/**
	 * Do I need to update serialVersionUID?
	 * See section 5.6 <cite>Type Changes Affecting Serialization</cite> on page 51 of the 
	 * <a href="http://java.sun.com/j2se/1.4/pdf/serial-spec.pdf">Java Object Serialization Spec</a>
	 */
	private static final long serialVersionUID = 1L;

	public SAD2WQueryNumberRange(WOContext context) {
        super(context);
    }
}