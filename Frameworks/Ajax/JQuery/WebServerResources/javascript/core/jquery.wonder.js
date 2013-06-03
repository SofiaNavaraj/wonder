var Wonder = Wonder || {};

String.addQueryParameters = function(additionalParameters) {
    if(additionalParameters) {
        return this + (this.match(/\?/) ? '&' : '?') + additionalParameters;
    }
    return this;
};

/* Simple JavaScript Inheritance
 * By John Resig http://ejohn.org/
 * MIT Licensed.
 */
// Inspired by base2 and Prototype
(function(){
    var initializing = false, fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

    // The base Class implementation (does nothing)
    this.Class = function(){};

    // Create a new Class that inherits from this class
    Class.extend = function(prop) {
        var _super = this.prototype;

        // Instantiate a base class (but only create the instance,
        // don't run the init constructor)
        initializing = true;
        var prototype = new this();
        initializing = false;

        // Copy the properties over onto the new prototype
        for (var name in prop) {
            // Check if we're overwriting an existing function
            prototype[name] = typeof prop[name] == "function" &&
                typeof _super[name] == "function" && fnTest.test(prop[name]) ?
                (function(name, fn){
                    return function() {
                        var tmp = this._super;

                        // Add a new ._super() method that is the same method
                        // but on the super-class
                        this._super = _super[name];

                        // The method only need to be bound temporarily, so we
                        // remove it when we're done executing
                        var ret = fn.apply(this, arguments);
                        this._super = tmp;

                        return ret;
                    };
                })(name, prop[name]) :
                prop[name];
        }

        // The dummy class constructor
        function Class() {
            // All construction is actually done in the init method
            if ( !initializing && this.init )
                this.init.apply(this, arguments);
        }

        // Populate our constructed prototype object
        Class.prototype = prototype;

        // Enforce the constructor to be what we expect
        Class.prototype.constructor = Class;

        // And make this class extendable
        Class.extend = arguments.callee;

        return Class;
    };
})();

(function($) {

    Wonder.settings = {
        verbose: 0
    };

    Wonder.PeriodicalRegistry = {};
    Wonder.guid = 1;
    Wonder.expando = "data-" + (new Date).getTime();
    Wonder.handlers = {};
    Wonder.components = [];

    Wonder.log = function(msg, lvl) {
        lvl = lvl || 1;
        if(lvl >= Wonder.settings.verbose) {
            try {
                console.log(msg);
            } catch(err) {}
        }
    };

    Wonder.Application = {

        initialize: function(ctx) {
            $(ctx).find("[data-wonder-id]").each(function(index, element) {
                var component = eval(Wonder[$(element).attr('data-wonder-id')]);
                new component(element);
            });
        },

        registerComponent: function(element, obj) {

        },

        registerEvent: function(element, type, fn) {

            if(! Wonder.handlers) {
                Wonder.handlers = {};
            }

            var wonderId = element.attr('data-wonder-id');

            if(! Wonder.handlers[wonderId]) {
                Wonder.handlers[wonderId] = [];
                var selector = '[data-wonder-id="' + wonderId + '"]';
                $(selector).on(type, fn);
            }

            if(! fn.guid) {
                fn.guid = Wonder.guid;
                Wonder.guid++;
            }

            Wonder.handlers[wonderId].push(fn);
        }

    };

    Wonder.AjaxElement = Class.extend({
        init: function(element) {
            Wonder.log("Initializing " + element.attr('data-wonder-id'));
        }
    });

    Wonder.AjaxComponent = Wonder.AjaxElement.extend({

        delegate: null,

        init: function(element) {
            this._super(element);
            Wonder.Application.registerComponent(element, this);
        },

        mightUpdate: function(target, caller) {
            if(this.delegate && Wonder.delegates[this.delegate] && Wonder.delegates[this.delegate].mightUpdate) {
                return Wonder.delegates[this.delegate].mightUpdate(target, caller);
            }
        },

        willUpdate: function(target, caller) {
            if(this.delegate && Wonder.delegates[this.delegate] && Wonder.delegates[this.delegate].willUpdate) {
                return Wonder.delegates[this.delegate].willUpdate(target, caller);
            }
        },

        processUpdate: function(target, caller, remoteData, callback) {
            var self = this;
            $.when(self.willUpdate(target, caller)).done(function() {
                self._updateTarget(target, remoteData);
                $.when(self.didUpdate(target, caller)).done(function() {
                    $.when(self.handleFinish(target)).done(function() {
                        if(callback) {
                            callback.call();
                        }
                    })
                });
            });
        },

        _updateTarget: function(target, remoteData) {
            target.html(remoteData);
        },

        didUpdate: function(target, caller) {
            if(this.delegate && Wonder.delegates[this.delegate] && Wonder.delegates[this.delegate].didUpdate) {
                return Wonder.delegates[this.delegate].didUpdate(target, caller);
            }
            return true;
        },

        updateFailed: function(target, caller, callback) {
            if(this.delegate && Wonder.delegates[this.delegate] && Wonder.delegates[this.delegate].updateFailed) {
                Wonder.delegates[this.delegate].updateFailed(target, caller);
            }
        },

        handleFinish: function(target) {
            return Wonder.Application.initialize(target);
        }

    });

    var AjaxOptions = {
        defaultOptions: function(additionalOptions) {
            var options = {method: 'get', async: true, evalScripts: true};
            return $.extend(options, additionalOptions);
        }
    };

    Wonder.AjaxUpdateContainer = Wonder.AjaxComponent.extend({

        init: function(element) {
            var element = $(element);
            this._super(element);
            var options = $.parseJSON(element.attr('data-wonder-options'));
            if(options.hasOwnProperty('minTimeout')) {
                this.registerPeriodic(
                    element,
                    options.hasOwnProperty('canStop') ? options.canStop : null,
                    options.hasOwnProperty('stopped') ? options.stopped : null,
                    options
                );
            } else {
                this.registerContainer(element.attr('id'), options);
            }
        },

        registerPeriodic: function(element, canStop, stopped, options) {
            var self = this;
            var url = options.updateUrl;
            self.delegate = eval(options.delegate);
            if(self.delegate) {
                if(Wonder.delegates[this.delegate]) {
                    options.beforeSend = function(element, element) {
                        self.mightUpdate(element, element);
                    };
                }
            }
            if(! canStop) {
                if(! Wonder.PeriodicalRegistry[element.attr('id')]) {
                    Wonder.PeriodicalRegistry[element.attr('id')] = $.PeriodicalUpdater(url, options, function(remoteData, success, xhr, handle) {
                        self.delegate = eval(options.delegate);
                        if(success) {
                            handle.pause();
                            self.processUpdate(element, element, remoteData, handle.pause);
                        } else {
                            self.updateFailed(element, element, null);
                        }
                    });
                }
            }
        },

        cleanUp: function() {
            var element = $(this);
            // TODO bug with nested periodical components.  For some reason the unmatched function runs
            // TODO (cont'd) before the matched function in livequery or something to that effect.
        },

        registerContainer: function(id, options) {
            if(!options) {
                options = {};
            } else if(options.delegate) {
                this.delegate = eval(options.delegate);
            }
            eval(id + "Update = function() { AjaxUpdateContainer.update(id, options) }");
        },

        update: function(id, options) {
            var updateElement = $("#" + id);
            if(updateElement == null) {
                alert('There is no element on this page with the id "' + id + '".');
            }
            var actionUrl = updateElement.attr('data-updateUrl');
            if(options && options['_r']) {
                actionUrl = actionUrl.addQueryParameters('_r='+ id);
            }
            else {
                actionUrl = actionUrl.addQueryParameters('_u=' + id);
            }
            actionUrl = actionUrl.addQueryParameters(new Date().getTime());
            $.get(actionUrl, $.extend(AjaxOptions.defaultOptions(options), {

            }));
        }

    });

    Wonder.AUC = Wonder.AjaxUpdateContainer;

    Wonder.AjaxUpdateLink = Wonder.AjaxElement.extend({

        init: function(element) {
            var element = $(element);
            this._super(element);
            Wonder.Application.registerEvent(element, "click", this.update);
        },

        update: function() {

            var self = $(this);
            var options = $.parseJSON(self.attr('data-wonder-options'));
            var target = $("#" + options.updateContainer);

            if(target == null) {
                alert('There is no element on this page with the id "' + options.updateContainer + '".');
            } else {

                var elementID = options.elementID;
                var targetOptions = $.parseJSON(target.attr('data-wonder-options'));

                var actionUrl = targetOptions.updateUrl;
                var id = target.attr('id');

                if(elementID) {
                    actionUrl = actionUrl.replace(/[^\/]+$/, elementID);
                }

                // Should you be stored in a cache??
                var updateComponent = new Wonder.AUC(target);

                $.ajax({
                    url: actionUrl,
                    data: options,
                    success: function(data) {
                        updateComponent.processUpdate(target, self, data, options.callback);
                    }
                });

            }

        }

    });

    Wonder.AUL = Wonder.AjaxUpdateLink;

    Wonder.delegates = {};
    Wonder.delegates.debug = function() {}
    Wonder.delegates.debug.prototype.mightUpdate = function(target, caller) {
        Wonder.log("Might update...", 1);
    };
    Wonder.delegates.debug.prototype.willUpdate = function(target, caller) {
        Wonder.log("Might update...", 1);
    };
    Wonder.delegates.debug.prototype.didUpdate = function(target, caller) {
        Wonder.log("Did update...", 1);
    };
    Wonder.delegates.debug.prototype.updateFailed = function(target, caller) {
        Wonder.log("There was an error...", 1);
    };

    Wonder.delegates.fade = function() {};
    Wonder.delegates.fade.prototype = new Wonder.delegates.debug();
    Wonder.delegates.fade.prototype.willUpdate = function(target, caller) {
        return $(container).fadeOut();
    };

    Wonder.delegates.fade.didUpdate = function(target, caller) {
        return $(container).fadeIn();
    };

    Wonder.delegates.slide = function() {};
    Wonder.delegates.slide = new Wonder.delegates.debug();

    Wonder.delegates.slide.willUpdate = function(target, caller) {
        return $(target).slideUp();
    };
    Wonder.delegates.slide.didUpdate = function(target, caller) {
        return $(target).slideDown();
    };

    Wonder.AUC.delegates = $.extend(Wonder.delegates, {
    });

    (function() {
        $(window).load(function() {
            Wonder.Application.initialize(document);
        });
    })();

})(jQuery);


