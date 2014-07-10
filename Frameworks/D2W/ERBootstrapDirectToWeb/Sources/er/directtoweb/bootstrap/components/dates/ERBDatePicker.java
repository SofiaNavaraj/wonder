package er.directtoweb.bootstrap.components.dates;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.ERDCustomEditComponent;
import er.extensions.formatters.ERXTimestampFormatter;

public class ERBDatePicker extends ERDCustomEditComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _formatter;

	public ERBDatePicker(WOContext context) {
        super(context);
    }

    /**
     * Format string for the date text fields
     */
	public String formatter() {
		if(_formatter == null) {
			_formatter = (String)valueForBinding("formatter");
		}
		if(_formatter == null || _formatter.length() == 0) {
			_formatter = ERXTimestampFormatter.DEFAULT_PATTERN;
		}
		return _formatter;
	}
	
}