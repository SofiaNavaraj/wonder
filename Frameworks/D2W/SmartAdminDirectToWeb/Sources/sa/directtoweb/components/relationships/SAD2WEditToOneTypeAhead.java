package sa.directtoweb.components.relationships;

import sa.directtoweb.components.buttons.SADActionButton;

import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

import er.directtoweb.components.ERDCustomEditComponent;
import er.extensions.eof.ERXConstant;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.eof.ERXQ;
import er.extensions.eof.ERXS;
import er.extensions.foundation.ERXSimpleTemplateParser;
import er.extensions.foundation.ERXStringUtilities;
import er.extensions.foundation.ERXUtilities;

public class SAD2WEditToOneTypeAhead extends ERDCustomEditComponent {

	public interface Keys extends ERDCustomEditComponent.Keys {
		public static final String pageConfiguration = "pageConfiguration";
		public static final String createConfigurationName = "createConfigurationName";
		public static final String propertyKey = "propertyKey";
		public static final String sortKey = "sortKey";
		public static final String destinationEntityName = "destinationEntityName";
		public static final String restrictedChoiceKey = "restrictedChoiceKey";
		public static final String restrictingFetchSpecification = "restrictingFetchSpecification";
		public static final String typeAheadSearchTemplate = "typeAheadSearchTemplate";
		public static final String extraRestrictingQualifier = "extraRestrictingQualifier";
		public static final String keyWhenRelationship = "keyWhenRelationship";
		public static final String typeAheadMinimumCharaceterCount = "typeAheadMinimumCharaceterCount";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NSArray<EOEnterpriseObject> _allItems;
	private EOEnterpriseObject _currentSelection;
	private String _destinationEntityName;
	private EOQualifier _extraQualifier;
	private String _keyWhenRelationship;
	public EOEnterpriseObject item;
	private String _propertyKey;
	private String _restrictedChoiceKey;
	private EOFetchSpecification _restrictingFetchSpec;
	private String _restrictingFetchSpecification;
	private String _searchValue;
	private String _sortKey;
	private String _template;

	public SAD2WEditToOneTypeAhead(WOContext context) {
		super(context);
	}

	@Override
	public void awake() {
		NSNotificationCenter.defaultCenter().addObserver(
				this,
				new NSSelector("relatedObjectDidChange",
						ERXConstant.NotificationClassArray),
				SADActionButton.BUTTON_PERFORMED_DELETE_ACTION, null);
		super.awake();
	}

	@Override
	public void sleep() {
		NSNotificationCenter.defaultCenter().removeObserver(this,
				SADActionButton.BUTTON_PERFORMED_DELETE_ACTION, null);
		super.sleep();
	}

	@Override
	public boolean synchronizesVariablesWithBindings() {
		return false;
	}

	@SuppressWarnings("unchecked")
	public NSArray<EOEnterpriseObject> allItems() {
		if (_allItems == null) {
			_allItems = (NSArray<EOEnterpriseObject>) restrictedChoiceList();
			if (_allItems == null) {
				EOFetchSpecification fetchSpec = new EOFetchSpecification(
						destinationEntityName(), null, null);
				_allItems = ec().objectsWithFetchSpecification(fetchSpec);
			}
		}
		return _allItems;
	}

	public String keyWhenRelationship() {
		if (_keyWhenRelationship == null) {
			_keyWhenRelationship = stringValueForBinding(Keys.keyWhenRelationship);
		}
		return _keyWhenRelationship;
	}

	/**
	 * Returns the currently selected destination entity
	 */
	public EOEnterpriseObject currentSelection() {
		if (_currentSelection == null)
			// NSLog.out.appendln("***ERMD2WEditToOneTypeAhead.currentSelection: "
			// + _currentSelection + " ***");
			_currentSelection = (EOEnterpriseObject) objectPropertyValue();
		return _currentSelection;
	}

	/**
	 * Returns the array of available matching destination entities
	 */
	public NSArray<EOEnterpriseObject> currentObjects() {
		NSArray<EOEnterpriseObject> result = null;
		String value = searchValue();
		if (value != null) {
			if (searchTemplate() != null) {
				value = ERXSimpleTemplateParser.parseTemplatedStringWithObject(
						searchTemplate(), this);
			}
			EOQualifier qual = ERXQ.likeInsensitive(keyWhenRelationship(),
					value);
			result = destinationObjectsWithQualifier(qual);
		}
		return result;
	}

	public EOQualifier extraQualifier() {
		if (_extraQualifier == null) {
			_extraQualifier = (EOQualifier) valueForBinding(Keys.extraRestrictingQualifier);
		}
		return _extraQualifier;
	}

	/**
	 * Returns the display value for the available matching destination entities
	 * in the drop down list.
	 */
	public String itemDisplayString() {
		return (String) item.valueForKey(keyWhenRelationship());
	}

	/**
	 * Value displayed by the AjaxAutoFill field, if nothing is entered in the
	 * field it will return either the kvc value of 'keyWhenRelationship' on the
	 * related entity or the kvc value of 'userPresentableDescription'
	 */
	public String searchValue() {
		if (ERXStringUtilities.stringIsNullOrEmpty(_searchValue)
				&& currentSelection() != null) {
			_searchValue = currentSelection()
					.valueForKey(keyWhenRelationship()).toString();
		}
		return _searchValue;
	}

	
	
	@SuppressWarnings("unchecked")
	public NSArray<EOEnterpriseObject> destinationObjectsWithQualifier(
			EOQualifier qual) {
		NSArray<EOEnterpriseObject> result = null;
		NSArray<EOSortOrdering> orderings = null;
		if (!ERXStringUtilities.stringIsNullOrEmpty(sortKey())) {
			orderings = ERXS.ascs(sortKey());
		}
		if (extraQualifier() != null) {
			qual = ERXQ.and(qual, extraQualifier());
		}
		if (useFetch()
				&& ERXStringUtilities
						.stringIsNullOrEmpty(restrictedChoiceKey())) {
			if (restrictingFetchSpecificationName() != null) {
				qual = ERXQ.and(qual, restrictingFetchSpec().qualifier());
			}
			EOFetchSpecification fetchSpec = new EOFetchSpecification(
					destinationEntityName(), qual, orderings);
			fetchSpec.setIsDeep(true);
			EOEditingContext ec = ERXEC.newEditingContext();
			result = ec.objectsWithFetchSpecification(fetchSpec);
		} else {
			result = ERXQ.filtered(allItems(), qual);
		}
		return result;
	}

	public String propertyKey() {
		if (_propertyKey == null) {
			_propertyKey = stringValueForBinding(Keys.propertyKey);
		}
		return _propertyKey;
	}	
    
	/**
	 * Called when an {@link ERMDActionButton} changes the related object. Nulls
	 * {@link #_searchValue} which in turn lets it rebuild on the next display
	 */
	@SuppressWarnings("unchecked")
	public void relatedObjectDidChange(NSNotification notif) {
		NSDictionary<String, Object>userInfo = notif.userInfo();
		if (userInfo != null) {
			Object key = userInfo.valueForKey("propertyKey");
			EOEnterpriseObject obj = (EOEnterpriseObject)userInfo.valueForKey("object");
			if (propertyKey() != null && propertyKey().equals(key) && ERXEOControlUtilities.eoEquals(object(), obj)) {
				_searchValue = null;
				_currentSelection = null;
			}
		}
	}
	
	
	public String restrictedChoiceKey() {
		if (_restrictedChoiceKey == null) {
			_restrictedChoiceKey = stringValueForBinding(Keys.restrictedChoiceKey);
		}
		return _restrictedChoiceKey;
	}

	public Object restrictedChoiceList() {
		String restrictedChoiceKey = stringValueForBinding(Keys.restrictedChoiceKey);
		if (restrictedChoiceKey != null && restrictedChoiceKey.length() > 0)
			return valueForKeyPath(restrictedChoiceKey);
		String fetchSpecName = stringValueForBinding(Keys.restrictingFetchSpecification);
		if (fetchSpecName != null) {
			EORelationship relationship = ERXUtilities
					.relationshipWithObjectAndKeyPath(object(),
							(String) d2wContext().valueForKey(Keys.propertyKey));
			return EOUtilities.objectsWithFetchSpecificationAndBindings(
					object().editingContext(), relationship.destinationEntity()
							.name(), fetchSpecName, null);
		}
		return null;
	}

	public EOFetchSpecification restrictingFetchSpec() {
		if (_restrictingFetchSpec == null) {

			_restrictingFetchSpec = EOModelGroup.defaultGroup()
					.fetchSpecificationNamed(
							restrictingFetchSpecificationName(),
							destinationEntityName());
			;
		}
		return _restrictingFetchSpec;
	}

	public String restrictingFetchSpecificationName() {
		if (_restrictingFetchSpecification == null) {
			_restrictingFetchSpecification = stringValueForBinding(Keys.restrictingFetchSpecification);
		}
		return _restrictingFetchSpecification;
	}
	
	public EOEnterpriseObject selection() {
		return (EOEnterpriseObject) object().valueForKey(propertyKey());
	}
	
	public void setSelection(EOEnterpriseObject value) {
		object().takeValueForKey(EOUtilities.localInstanceOfObject(object().editingContext(), value), propertyKey());
	}

	/**
	 * Sets the searchValue
	 * 
	 * @param value
	 */
	public void setSearchValue(String value) {
		_searchValue = value;
	}

	public String searchTemplate() {
		if (_template == null) {
			_template = stringValueForBinding(Keys.typeAheadSearchTemplate);
		}
		return _template;
	}

	public String sortKey() {
		if (_sortKey == null) {
			_sortKey = stringValueForBinding(Keys.sortKey);
		}
		return _sortKey;
	}

	/**
	 * Should this component use a fetch to qualify the list of available
	 * destination entities
	 * 
	 * @return true if the 'e' is the name of an attribute
	 */
	public boolean useFetch() {
		EOEntity entity = EOUtilities
				.entityNamed(ec(), destinationEntityName());
		return (entity.attributeNamed(keyWhenRelationship()) != null);
	}

	private EOEditingContext ec() {
		return object().editingContext();
	}

	// ACCESSORS

	public String destinationEntityName() {
		if (_destinationEntityName == null) {
			_destinationEntityName = stringValueForBinding(Keys.destinationEntityName);
		}
		return _destinationEntityName;
	}

}