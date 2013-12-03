(function($) {
	
    Wonder.SelectPicker = Wonder.AjaxElement.extend({

		init: function(element) {
			var element = $(element);
			this._super(element);
			element.selectpicker(this.options);
		}    

    });

})(jQuery);