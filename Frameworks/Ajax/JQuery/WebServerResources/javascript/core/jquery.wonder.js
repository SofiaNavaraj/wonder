var Wonder = Wonder || {};

/* Simple JavaScript Inheritance
 * By John Resig http://ejohn.org/
 * MIT Licensed.
 */
// Inspired by base2 and Prototype
(function() {
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

    String.prototype.addQueryParameters = function(additionalParameters) {
        if(additionalParameters) {
            return this + (this.match(/\?/) ? '&' : '?') + additionalParameters;
        }
        return this;
    };

    Wonder.settings = {
        verbose: 0
    };

    Wonder.PeriodicalRegistry = {};
    Wonder.nextGuid = 1;
    Wonder.components = [];

    Wonder.log = function(msg, lvl) {
        lvl = lvl || 1;
        if(lvl >= Wonder.settings.verbose) {
            try {
                console.log(msg);
            } catch(err) {}
        }
    };

    Wonder.Page = {

        initialize: function(ctx) {
            $(ctx).find("[data-wonder-id]").each(function(index, element) {
                var component = eval(Wonder[$(element).attr('data-wonder-id')]);
                new component(element);
            });
        },

        addComponent: function(element, obj) {
            element.data()['component'] = obj;
        },

        getComponent: function(element) {
            return element.data()['component'];
        },

        addEvent: function(element, type, fn) {
            var data = element.data();
            if(! data.handlers) data.handlers = {};
            if(! data.handlers[type]) data.handlers[type] = [];
            if(! fn.guid) fn.guid = Wonder.nextGuid++;
            data.handlers[type].push(fn);
            if(! data.dispatcher) {
                data.disabled = false;
                data.dispatcher = function(event) {
                    if(data.disabled) return;
                    var handlers = data.handlers[event.type];
                    if(handlers) {
                        for(var n = 0; n < handlers.length; n++) {
                            handlers[n].call(element, event);
                        }
                    }
                }
            }
            if(data.handlers[type].length == 1) {
                element.bind(type, data.dispatcher);
            }
        }

    };

    Wonder.AjaxElement = Class.extend({

        options: null,
        init: function(element) {
            Wonder.log("Initializing " + element.attr('data-wonder-id'));
            this.options = $.parseJSON(element.attr('data-wonder-options'));
        },

        sayHi: function() {
            alert('hi');
        }

    });

    Wonder.AjaxComponent = Wonder.AjaxElement.extend({

        delegate: null,
        element: null,
        url: null,

        init: function(element) {
            this._super(element);
            if(this.options.delegate) {
                this.delegate = Wonder.delegates[eval(this.options.delegate)];
            }
        },

        mightUpdate: function(target, caller) {
            if(this.delegate) {
                return this.delegate.mightUpdate(target, caller);
            }
        },

        willUpdate: function(target, caller) {
            if(this.delegate) {
                return this.delegate.willUpdate(target, caller);
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
            if(this.delegate) {
                return this.delegate.didUpdate(target, caller);
            }
            return true;
        },

        updateFailed: function(target, caller, callback) {
            if(this.delegate) {
                this.delegate.updateFailed(target, caller);
            }
        },

        handleFinish: function(target) {
            return Wonder.Page.initialize(target);
        }


    });

    var AjaxOptions = {
        defaultOptions: function(additionalOptions) {
            var options = {type: 'GET', async: true, evalScripts: true};
            return $.extend(options, additionalOptions);
        }
    };

    Wonder.AjaxUpdateContainer = Wonder.AjaxComponent.extend({

        init: function(element) {

            this.element = $(element);
            this._super(this.element);
            this.url = this.options['updateUrl'];

            if(this.options['minTimeout']) {
                this.registerPeriodic(
                    this.options['canStop'] ? this.options['canStop'] : null,
                    this.options['stopped'] ? this.options['stopped'] : null
                );
            } else {
                Wonder.Page.addComponent(this.element, this);
            }
        },

        registerPeriodic: function(canStop, stopped) {

            if(this.delegate) {
                this.options['beforeSend'] = $.proxy(function(xhr) {
                    this.mightUpdate(this.element, this.element);
                }, this);

                this.options['error'] = $.proxy(function(xhr, textStatus) {
                    this.updateFailed(this.element, this.element);
                }, this);
            }

            if(! canStop) {
                if(! Wonder.PeriodicalRegistry[this.element.attr('id')]) {
                    Wonder.PeriodicalRegistry[this.element.attr('id')] = $.PeriodicalUpdater(this.url, this.options, $.proxy(function(remoteData, success, xhr, handle) {
                        if(success) {
                            handle.pause();
                            this.processUpdate(this.element, this.element, remoteData, handle.pause);
                        }
                    }, this));
                }
            }

        },

        update: function(url, caller, options, data) {

            var updateUrl = url || this.url;
            var delegate = options.delegate || this.delegate;
            var aCaller = caller || this.element;

            options['context'] = this;
            options['data'] = data;

            options['beforeSend'] = function(xhr, settings) {
                if(delegate) {
                    delegate.mightUpdate(this.element, aCaller);
                }
            };

            options['success'] = function(data, textStatus, xhr) {
                this.processUpdate(this.element, aCaller, data, options.callback);
            };

            options['error'] = function(xhr, textStatus, errorThrown) {
                if(delegate) {
                    delegate.updateFailed(this.element, aCaller);
                }
            };

            $.ajax(updateUrl, options);

        },

        _update: function(id, options) {
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
            element.bind("click", { options: this.options }, this.update);
        },

        update: function(event) {

            var caller = $(this);
            var options = event.data.options;

            var updateContainer = Wonder.Page.getComponent($("#" + options['updateContainer']));

            if(updateContainer == null) {
                alert('There is no element on this page with the id "' + options['updateContainer'] + '".');
            } else {

                var elementID = options['elementID'];
                var actionUrl = updateContainer.url;

                if(elementID) {
                    actionUrl = actionUrl.replace(/[^\/]+$/, elementID);
                }

                updateContainer.update(actionUrl, caller, options);

            }

        }

    });

    Wonder.AUL = Wonder.AjaxUpdateLink;

    Wonder.AjaxSubmitButton = Wonder.AjaxElement.extend({

        PartialFormSenderIDKey: '_partialSenderID',
        AjaxSubmitButtonNameKey: 'AJAX_SUBMIT_BUTTON_NAME',

        init: function(element){
            var element = $(element);
            this._super(element);
            element.bind("click", { options: this.options }, this.update);
        },

        defaultOptions: function(additionalOptions) {
            var options = AjaxOptions.defaultOptions(additionalOptions);
            options['type'] = 'POST';
            options['cache'] = false;
            return options;
        },

        update: function(event) {

            var caller = $(this);
            var options = event.data.options;
            var targetId = options['updateContainer'];
            var target = Wonder.Page.getComponent($("#" + targetId));

            if(target == null) {
                alert("There is no element on this page with the id " + targetId);
            } else {

                var form = options['formName'] ? document[options.formName] : caller[0].form;
                var actionUrl = Wonder.ASB.prototype.generateActionUrl(targetId, form, options);
                var settings = Wonder.ASB.prototype.defaultOptions(options);
                var data = Wonder.ASB.prototype.processOptions(form, {
                    '_asbn': options['_asbn']
                });

                target.update(actionUrl, caller, settings, data['parameters']);

            }
        },

        processOptions: function(form, options) {

            var processedOptions = null;

            if(options != null) {
                processedOptions = $.extend({}, options);
                var ajaxSubmitButtonName = processedOptions['_asbn'];
                if(ajaxSubmitButtonName != null) {
                    processedOptions['_asbn'] = null;
                    var parameters = processedOptions['parameters'];
                    if(parameters === undefined || parameters == null) {
                        var formSerializer = processedOptions['_fs'];
                        var serializedForm = $(form).serialize();
                        processedOptions['parameters'] = serializedForm + '&' + Wonder.ASB.prototype.AjaxSubmitButtonNameKey +
                            '=' + ajaxSubmitButtonName;
                    }
                    else {
                        processedOptions['parameters'] = parameters + '&' + Wonder.ASB.prototype.AjaxSubmitButtonNameKey +
                            '=' + ajaxSubmitButtonName;
                    }
                }
            }

//            processedOptions = Wonder.ASB.prototype.defaultOptions(processedOptions);
            return processedOptions;

        },

        generateActionUrl: function(id, form, options) {

            var actionUrl = form.action;

            actionUrl = actionUrl.replace(/\/wo\//, '/ajax/');

            if(id != null) {
                if(options && options['_r']) {
                    actionUrl = actionUrl.addQueryParameters('_r=' + id);
                }
                else {
                    actionUrl = actionUrl.addQueryParameters('_u=' + id);
                }
            }

            actionUrl = actionUrl.addQueryParameters(new Date().getTime());
            return actionUrl;

        }

    });

    Wonder.ASB = Wonder.AjaxSubmitButton;

    Wonder.AjaxObserveField = Wonder.AjaxElement.extend({

        init: function(element) {
            var element = $(element);
            this._super(element);
            var options = $.parseJSON(self.attr('data-wonder-options'));
            var observeFieldId = options['observeFieldId'];
            var elementToObserve = $("#" + observeFieldId);
            if(! elementToObserve) {
                alert("Unable to observe element with id: " + observeFieldId + ".  The element does not exist.");
            } else {
                Wonder.Page.addEvent(elementToObserve, "change", this.update.partial(element));
            }
        },

        update: function() {

        }


    });

    Wonder.AOF = Wonder.AjaxObserveField;

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

    Wonder.delegates.fade = new Wonder.delegates.debug();
    Wonder.delegates.fade.willUpdate = function(target, caller) {
        return $(target).fadeOut();
    };

    Wonder.delegates.fade.didUpdate = function(target, caller) {
        return $(target).fadeIn();
    };

    Wonder.delegates.slide = new Wonder.delegates.debug();

    Wonder.delegates.slide.willUpdate = function(target, caller) {
        return $(target).slideUp();
    };

    Wonder.delegates.slide.didUpdate = function(target, caller) {
        return $(target).slideDown();
    };

    $(window).load(function() {
        Wonder.Page.initialize(document);
    });

})(jQuery);


