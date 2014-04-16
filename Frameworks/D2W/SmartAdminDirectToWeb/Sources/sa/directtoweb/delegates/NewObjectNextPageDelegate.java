package sa.directtoweb.delegates;

import com.webobjects.appserver.WOComponent;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WContext;
import com.webobjects.directtoweb.EditPageInterface;
import com.webobjects.directtoweb.NextPageDelegate;
import com.webobjects.eocontrol.EOEnterpriseObject;

import er.directtoweb.pages.ERD2WPage;

public class NewObjectNextPageDelegate implements NextPageDelegate {

	private WOComponent sender;
	
	@Override
	public WOComponent nextPage(WOComponent wocomponent) {
		setSender(wocomponent);
		D2WContext context = ((ERD2WPage) sender).d2wContext();
		EOEnterpriseObject object = (EOEnterpriseObject) context.valueForKey("object");
		EditPageInterface epi = (EditPageInterface) D2W.factory().editPageForEntityNamed(object.entityName(), sender().session());
		epi.setObject(object);
		return (WOComponent) epi;
	}

	public WOComponent sender() {
		return sender;
	}

	public void setSender(WOComponent sender) {
		this.sender = sender;
	}

}
