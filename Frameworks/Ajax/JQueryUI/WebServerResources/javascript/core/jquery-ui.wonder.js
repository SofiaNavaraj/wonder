(function($) {
	
    Wonder.Slider = Wonder.AjaxElement.extend({

        init: function(element) {
            var element = $(element);
            this._super(element);
            this.options['change'] = this.update;
            if(! this.options['value']) {
                this.options['values'] = [
                    this.options['minValue'] || this.options['min'] || 0,
                    this.options['maxValue'] || this.options['max'] || 100
                ];
            }
            var slider = element.slider(this.options);
        },

        update: function(event, ui) {

            var element = $(ui.handle).parent();
            var options = element.slider("option");

            var actionUrl = options['url'];
            actionUrl = actionUrl.replace(/\/wo\//, '/ajax/');

            var data = {};
            var formFieldName = options['elementID'];

            if(options['value']) {
                data[formFieldName] = options['value'];
            } else if(options['values']) {

                var a = options['values'][0];
                var b = options['values'][1];
                if(b < a) {
                    data[formFieldName] = b + ":" + a;
                } else {
                    data[formFieldName] = a + ":" + b;
                }

            }

            data['_partialSenderID'] = formFieldName;

            $.ajax({
                url: actionUrl,
                data: data,
                context: options
            }).done(function(data, textStatus, jqXHR) {
                var options = this;
                if(options['trigger']) {

                    var triggerName = options['triggerName'];
                    $.event.trigger({
                        type: triggerName,
                        message: data,
                        time: new Date()
                    });
                }
            });


/*
            var form = element.parent("form");
            var updateContainer = Wonder.Page.getComponent(options['updateContainer']);
            var actionUrl = form[0].action;
            actionUrl = actionUrl.replace(/\/wo\//, '/ajax/');
            var data = {};
            var formFieldName = options['elementID'];

            if(options['value']) {
                data[formFieldName] = options['value'];
            } else if(options['values']) {
                var a = options['values'][0];
                var b = options['values'][1];
                if(b < a) {
                    data[formFieldName] = b + ":" + a;
                } else {
                    data[formFieldName] = a + ":" + b;
                }
            }

            data['_partialSenderID'] = formFieldName;
            updateContainer.update(actionUrl, element, null, data);
 */

        }

    });
    
    Wonder.DatePicker = Wonder.AjaxElement.extend({
		
		init: function(element) {
			var element = $(element);
			this._super(element);
			element.datepicker(this.options);
		}    

    });
    
    Wonder.Accordion = Wonder.AjaxElement.extend({

    	init: function(element) {
    		var element = $(element);
    		this._super(element);
    		element.accordion(this.options);
    	}

    });
    

})(jQuery);