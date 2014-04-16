(function($) {
	
    Wonder.Slider = Wonder.AjaxElement.extend({

        init: function(element) {

            var element = $(element);
            this._super(element);

            this.options['change'] = this.update;

            if(this.options['range'] == 'true') {
                this.options['range'] = true;
            }

            if(this.options['step']) {
                this.options['step'] = parseFloat(this.options['step']);
            }

            if(this.options['min']) {
                this.options['min'] = parseFloat(this.options['min']);
            }

            if(this.options['max']) {
                this.options['max'] = parseFloat(this.options['max']);
            }

            if(! this.options['value']) {
                this.options['values'] = [
                    this.options['min'] || this.options['minValue'] || 0,
                    this.options['max'] || this.options['maxValue']|| 100
                ];
            }

            var slider = element.slider(this.options);
            Wonder.Page.addComponent(element, slider);

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
    
    Wonder.Autocomplete = Wonder.AjaxElement.extend({
    	
    		init: function(element) {
 
    			var element = $(element);
    			this._super(element);
			var id = element.attr("id")

    			element.autocomplete(this.options);
 
    		}
    
    });
    
    Wonder.Dialog = Wonder.AjaxElement.extend({
    dialog: null,
    	init: function(element) {
		var self = this;
    		var element = $(element);
    		this._super(element);
	        var dialogID = "#" + this.options['dialogID'];
            var href = element.attr("href");
            if(! href.match(/^#/)) {
                this.options['open'] = function( event, ui ) {
                    $.get(href, function(data) {
                        $(dialogID).html(data);
                    });
                }
            }
            
        	var showEffect = this.options['showEffect'];
        	this.options['show'] = {
        		effect: 'drop',
        		direction: 'up',
        		duration: 200
        	},
			this.options['hide'] = {
				effect: 'fade',
        		duration: 200
            }
            

            this.dialog = $(dialogID).dialog(this.options);
            element.click(function(event) {
                event.preventDefault();
                self.dialog.dialog("open");
            });

            Wonder.Page.addComponent(element, this);

    	},
    	
	destroy: function() {
		this.dialog.dialog("destroy");
	}
    
    });
    

})(jQuery);

	/*
	 * CONVERT DIALOG TITLE TO HTML
	 * REF: http://stackoverflow.com/questions/14488774/using-html-in-a-dialogs-title-in-jquery-ui-1-10
	 */
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			if (!this.options.title) {
				title.html("&#160;");
			} else {
				title.html(this.options.title);
			}
		}
	}));
