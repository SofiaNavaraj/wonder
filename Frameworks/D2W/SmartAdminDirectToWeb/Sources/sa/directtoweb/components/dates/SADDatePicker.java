package sa.directtoweb.components.dates;

import com.webobjects.appserver.WOContext;
import er.directtoweb.components.ERDCustomEditComponent;
import er.extensions.formatters.ERXTimestampFormatter;

public class SADDatePicker extends ERDCustomEditComponent {

	private String _formatter;
	
	public SADDatePicker(WOContext context) {
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