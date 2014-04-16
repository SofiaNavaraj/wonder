(function($) {

	// Impacts the responce rate of some of the responsive elements (lower value affects CPU but improves speed)
	$.throttle_delay = 350;
	
	// The rate at which the menu expands revealing child elements on click
	$.menu_speed = 235;
	
	// Note: You will also need to change this variable in the "variable.less" file.
	$.navbar_height = 49;
	
	$.root_ = $('body');
	$.left_panel = $('#left-panel');
	$.main = null;
	
    // desktop or mobile
    $.device = null;

	/*
	 * DETECT MOBILE DEVICES
	 * Description: Detects mobile device - if any of the listed device is detected
	 * a class is inserted to $.root_ and the variable $.device is decleard. 
	 */	
	
	/* so far this is covering most hand held devices */
	var ismobile = (/iphone|ipad|ipod|android|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()));

	if (!ismobile) {
		// Desktop
		$.root_.addClass("desktop-detected");
		$.device = "desktop";
	} else {
		// Mobile
		$.root_.addClass("mobile-detected");
		$.device = "mobile";
		
		// Removes the tap delay in idevices
		// dependency: js/plugin/fastclick/fastclick.js 
		// FastClick.attach(document.body);
	}
		
	
	Wonder.SAUtils = {

		check_if_mobile_width : function() {
			if ($(window).width() < 979) {
				$.root_.addClass('mobile-view-activated')
			} else if ($.root_.hasClass('mobile-view-activated')) {
				$.root_.removeClass('mobile-view-activated');
			}
		},

		nav_page_height : function() {

			var contentHeight = $('#main').height();
			var windowHeight = $(window).height() - $.navbar_height;

			var root = $('body');
			var left_panel = $('#left-panel');
			
			if (contentHeight > windowHeight) {// if content height exceedes actual window height and menuHeight
				left_panel.css('min-height', contentHeight + 'px');
				root.css('min-height', contentHeight + $.navbar_height + 'px');
			} else {
				left_panel.css('min-height', windowHeight + 'px');
				root.css('min-height', windowHeight + 'px');
			}
		
		}

	};
	
    Wonder.SAMainContent = Wonder.AjaxElement.extend({

		init: function(element) {

			var self = this;
			var element = $(element);
			self._super(element);

			Wonder.SAUtils.nav_page_height();								

			$(document.getElementsByTagName('body')[0]).resize(function() {
				Wonder.SAUtils.nav_page_height();								
				Wonder.SAUtils.check_if_mobile_width();
			});

		}    

    });
    
    Wonder.SANavigation = Wonder.AjaxElement.extend({
    
		init: function(element) {
			
			var element = $(element);
			this._super(element);
			var options = $.extend(this.options, {
				accordion : true,
				speed : $.menu_speed,
				closedSign : '<em class="fa fa-expand-o"></em>',
				openedSign : '<em class="fa fa-collapse-o"></em>'
			});
			element.children("ul").jarvismenu(options);
						
		}    
    
    });
    
    Wonder.SAMinifyButton = Wonder.AjaxElement.extend({
    
    	init: function(element) {

    		var self = this;
			var element = $(element);
			self._super(element);
			element.click(function(e) {
				$('body').toggleClass("minified");
				element.effect("highlight", {}, 500);
				e.preventDefault();
			});    		

    	}
    
    });
    
    Wonder.SAHideMenuButton = Wonder.AjaxElement.extend({
    	
    	init: function(element) {
    		var self = this;
    		var element = $(element);
    		self._super(element);
    		element.find(">span  >a").click(function(e) {
				$('body').toggleClass("hidden-menu");
				e.preventDefault();
    		});
    	
    	}
    	
    });
    
})(jQuery);

/*
 * RESIZER WITH THROTTLE
 * Source: http://benalman.com/code/projects/jquery-resize/examples/resize/
 */

(function($, window, undefined) {

	var elems = $([]), jq_resize = $.resize = $.extend($.resize, {}), timeout_id, str_setTimeout = 'setTimeout', str_resize = 'resize', str_data = str_resize + '-special-event', str_delay = 'delay', str_throttle = 'throttleWindow';

	jq_resize[str_delay] = $.throttle_delay;

	jq_resize[str_throttle] = true;

	$.event.special[str_resize] = {

		setup : function() {
			if (!jq_resize[str_throttle] && this[str_setTimeout]) {
				return false;
			}

			var elem = $(this);
			elems = elems.add(elem);
			$.data(this, str_data, {
				w : elem.width(),
				h : elem.height()
			});
			if (elems.length === 1) {
				loopy();
			}
		},
		teardown : function() {
			if (!jq_resize[str_throttle] && this[str_setTimeout]) {
				return false;
			}

			var elem = $(this);
			elems = elems.not(elem);
			elem.removeData(str_data);
			if (!elems.length) {
				clearTimeout(timeout_id);
			}
		},

		add : function(handleObj) {
			if (!jq_resize[str_throttle] && this[str_setTimeout]) {
				return false;
			}
			var old_handler;

			function new_handler(e, w, h) {
				var elem = $(this), data = $.data(this, str_data);
				data.w = w !== undefined ? w : elem.width();
				data.h = h !== undefined ? h : elem.height();

				old_handler.apply(this, arguments);
			};
			if ($.isFunction(handleObj)) {
				old_handler = handleObj;
				return new_handler;
			} else {
				old_handler = handleObj.handler;
				handleObj.handler = new_handler;
			}
		}
	};

	function loopy() {
		timeout_id = window[str_setTimeout](function() {
			elems.each(function() {
				var elem = $(this), width = elem.width(), height = elem.height(), data = $.data(this, str_data);
				if (width !== data.w || height !== data.h) {
					elem.trigger(str_resize, [data.w = width, data.h = height]);
				}

			});
			loopy();

		}, jq_resize[str_delay]);

	};

})(jQuery, this);

