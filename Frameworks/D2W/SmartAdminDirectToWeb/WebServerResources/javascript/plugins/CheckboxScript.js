var SAXCheckboxMatrix = {
	checkAll: function(form, name, checkbox) {
		var checked = jQuery(checkbox).val();
		jQuery(form).find('input[name="'+ name + '"]').each(function(index, element) {
			element.val(checked);
		});
	}
}