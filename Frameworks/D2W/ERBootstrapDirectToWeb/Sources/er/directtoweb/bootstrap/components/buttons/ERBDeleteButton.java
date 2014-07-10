package er.directtoweb.bootstrap.components.buttons;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.directtoweb.ConfirmPageInterface;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WPage;
import com.webobjects.eocontrol.EODetailDataSource;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSValidation;

import er.ajax.jquery.JQAjaxUtils;
import er.directtoweb.delegates.ERDDeletionDelegate;
import er.directtoweb.delegates.ERDPageDelegate;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.eof.ERXGuardedObjectInterface;
import er.extensions.localization.ERXLocalizer;

public class ERBDeleteButton extends ERBActionButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _cancelButtonLabel;
	private String _cancelButtonClass;
	private String _deleteButtonClass;
	private String _localUpdateContainer;
	protected String _dialogMessage;
	protected String _disabledButtonClass;
	
	public interface Keys extends ERBActionButton.Keys {
		public static final String deleteButtonLabel = "deleteButtonLabel";
		public static final String classForDeleteObjButton = "classForDeleteObjButton";
		public static final String classForDisabledDeleteObjButton = "classForDisabledDeleteObjButton";
		public static final String objectPendingDeletion = "objectPendingDeletion";
		public static final String cancelButtonLabel = "cancelButtonLabel";
		public static final String classForCancelDialogButton = "classForCancelDialogButton";
		public static final String classForDeleteDialogButton = "classForDeleteDialogButton";
		public static final String confirmDeleteMessage = "confirmDeleteMessage";
	}

	public ERBDeleteButton(WOContext context) {
        super(context);
    }

	
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		super.appendToResponse(aResponse, aContext);
	//	addRequiredWebResources(aResponse, aContext);
	}
	
    protected void addRequiredWebResources(WOResponse aResponse, WOContext aContext) {
		JQAjaxUtils.addUIStylesheetResourceInHead(aContext, aResponse, null, null);
    }
	
	/**
	 * Deletes the current object. Behaviour is dependent on the d2wContext useAjaxControls flag.
	 * 
	 * if true: Display an in-line confirmation dialog and update the pages main update container.
	 * if false: Take user to the confirmation page.
	 * 		
	 */
	public WOActionResults buttonAction() {

		WOActionResults results = null;
		
		if(shouldUseAjax()) {
		
			EOEditingContext ec = ERXEC.newEditingContext(object().editingContext());
			EOEnterpriseObject localObj = ERXEOControlUtilities.localInstanceOfObject(ec, object());
			d2wContext().takeValueForKeyPath(localObj, Keys.objectPendingDeletion);

		} else {
			
			ConfirmPageInterface nextPage = (ConfirmPageInterface) D2W.factory().pageForConfigurationNamed((String)valueForBinding("confirmDeleteConfigurationName"), session());
			nextPage.setConfirmDelegate(new ERDDeletionDelegate(object(), dataSource(), context().page()));
			nextPage.setCancelDelegate(new ERDPageDelegate(context().page()));
			D2WPage d2wPage = (D2WPage) nextPage;
			
			String message = ERXLocalizer.currentLocalizer().localizedTemplateStringForKeyWithObject("ERDTrashcan.confirmDeletionMessage", d2wContext());
			nextPage.setMessage(message);
			d2wPage.setObject(object());
			results = (WOActionResults) nextPage;

		}
		
		return results;
	}
	
    /**
     * Delete action for component button
     */
	public WOActionResults deleteAction() {
		return deleteObjectWithFinalCommit(true);
	}

	/** 
     * Performs the in-line delete and purges object pending deletion from the d2wContext to hide the 
     * in-line confirmation dialog. Calls saveChanges on the parent ec if the finalCommit flag is true.
     */
	public WOActionResults deleteObjectWithFinalCommit(boolean finalCommit) {

		dataSource().deleteObject(object());
		EOEnterpriseObject obj = (EOEnterpriseObject) d2wContext().valueForKey(Keys.objectPendingDeletion);
		obj.editingContext().deleteObject(obj);
		
		try {
			
			obj.editingContext().saveChanges();
			
			if(displayGroup() != null && displayGroup().displayedObjects().count() == 0) {
				displayGroup().displayPreviousBatch();
			}
			
			if(finalCommit) {
				object().editingContext().saveChanges();
			}
			
			d2wContext().takeValueForKey(null, Keys.objectPendingDeletion);
			postDeleteNotification();
			
			
		} catch (NSValidation.ValidationException e) {
			parent().validationFailedWithException(e, e.object(), e.key());
		}
		
		return null;
		
	}
	
    /**
     * Reverts the ec, and purges the objectPendingDeletion in the d2wContext to hide the in-line 
     * confirmation dialog.
     */
	public WOActionResults cancelAction() {
	
		EOEnterpriseObject obj = (EOEnterpriseObject) d2wContext().valueForKey(Keys.objectPendingDeletion);
		obj.editingContext().revert();
		d2wContext().takeValueForKey(null, Keys.objectPendingDeletion);

		return null;
		
	}
	
    /**
     * Utility method to post the delete notification to the parent component
     */
	public void postDeleteNotification() {
		
		Object obj = parentD2WPage();
		String OBJECT_KEY = "object";
		NSMutableDictionary<String, Object> userInfo = new NSMutableDictionary<String, Object>(obj, OBJECT_KEY);
	
		System.out.println(obj);
		
		if(dataSource() instanceof EODetailDataSource) {
			EODetailDataSource dds = (EODetailDataSource) dataSource();
			userInfo.setObjectForKey(dds.masterObject(), OBJECT_KEY);
			userInfo.setObjectForKey(dds.detailKey(), "propertyKey");
		}
		
		NSNotificationCenter.defaultCenter().postNotification(BUTTON_PERFORMED_DELETE_ACTION, obj, userInfo);
		
	}
	
    /**
     * Boolean used to hide/show the in-line confirm delete dialog.
     */
	public boolean canDelete() {
		return object() != null && object() instanceof ERXGuardedObjectInterface ? ((ERXGuardedObjectInterface) object()).canDelete() : true;
	}
	
	/**
	 * Label for the Delete button.
	 * <p>
         * Defaults to "Delete"
	 */
	public String buttonLabel() {
		if (_buttonLabel == null) {
			_buttonLabel = stringValueForBinding(Keys.deleteButtonLabel, "Delete");
		}
		return _buttonLabel;
	}

	/**
	 * Label for the Cancel button.
	 * <p>
         * Defaults to "Cancel"
	 */
    public String cancelButtonLabel() {
    	if (_cancelButtonLabel == null) {
			_cancelButtonLabel = stringValueForBinding(Keys.cancelButtonLabel, "Cancel");
		}
		return _cancelButtonLabel;
    }
    
    /**
     * CSS class for the Delete button.
     */
	public String buttonClass() {
		String result = null;
		if (canDelete() && !showDialog()) {
			result = activeButtonClass();
		} else {
			result = disabledButtonClass();
		}
		return result;
	}

	
	/**
	 * CSS class for the Delete button when active.
	 * <p>
	 * Defaults to "Button ObjButton DeleteObjButton"
	 */
	public String activeButtonClass() {
		if (_buttonClass == null) {
			_buttonClass = stringValueForBinding(Keys.classForDeleteObjButton, "btn btn-danger btn-xs");
		}
		return _buttonClass;
	}
	
	/**
	 * CSS class for the delete button when disabled.
	 * <p>
	 * Defaults to "Button ObjButton DisabledObjButton DisabledDeleteObjButton"
	 */
	public String disabledButtonClass() {
		if (_disabledButtonClass == null) {
			_disabledButtonClass = stringValueForBinding(Keys.classForDisabledDeleteObjButton, "btn btn-danger btn-xs disabled");
		}
		return _disabledButtonClass;
	}
	
	/**
	 * CSS class for the in-line dialog's Cancel button.
	 * <p>
	 * Defaults to "Button DialogButton CancelDialogButton"
	 */
	public String cancelButtonClass() {
		if (_cancelButtonClass == null) {
			_cancelButtonClass = stringValueForBinding(Keys.classForCancelDialogButton, "btn btn-default");
		}
		return _cancelButtonClass;
	}
	
	/** 
	 * CSS class for the in-line dialog's Delete button.
	 * <p>
	 * Defaults to "Button DialogButton DeleteDialogButton"
	 */
	public String deleteButtonClass() {
		if (_deleteButtonClass == null) {
			_deleteButtonClass = stringValueForBinding(Keys.classForDeleteDialogButton, "btn btn-danger");
		}
		return _deleteButtonClass;
	}
	
    /**
     * Used to show/hide the confirmation dialog
     */
    public boolean showDialog() {
	    	boolean show = object() != null && ERXEOControlUtilities.eoEquals(object(), (EOEnterpriseObject)d2wContext().valueForKey(Keys.objectPendingDeletion));
	    	return show;
    }
    
    /**
     * Determines whether to use an in-line confirmation dialog with ajax behaviour or a separate
     * confirmation page.
     * 
     * Based on the value of the useAjax binding (or d2wContext key).
     */
    @Override
    public Boolean useAjax() {
    	if (_useAjax == null) {
			_useAjax = Boolean.valueOf(shouldUseAjax());
		}
		return _useAjax;
    }
	
    /**
     * Returns a unique id for this control's update container
     */
	public String localUpdateContainer() {
		if (_localUpdateContainer == null) {
			_localUpdateContainer = (String)valueForBinding("idForPropertyContainer");
			_localUpdateContainer = _localUpdateContainer + "_" + object().hashCode();
		}
		return _localUpdateContainer;
	}
	
    /**
     * String to display in the in-line confirmation dialog.
     * 
     * Obtained from the bindings or d2wContext via this key:
     * 
     * 		confirmDeleteMessage
     */
    public String dialogMessage() {
    	if (_dialogMessage == null) {
    		_dialogMessage = (String)valueForBinding(Keys.confirmDeleteMessage);
    	}
    	return _dialogMessage;
    }

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		EOEnterpriseObject eo = (EOEnterpriseObject) d2wContext().valueForKey(Keys.objectPendingDeletion);
		out.writeObject(eo);		
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		EOEnterpriseObject eo = (EOEnterpriseObject) in.readObject();
		d2wContext().takeValueForKey(eo, Keys.objectPendingDeletion);
	}	
	

}