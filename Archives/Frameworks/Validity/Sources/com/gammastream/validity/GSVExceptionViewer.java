// Generated by the WebObjects Wizard Mon Nov 13 13:18:37 America/Chicago 2000
package com.gammastream.validity;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSValidation;

public class GSVExceptionViewer extends WOComponent {

    public String currentMessage;
    public NSArray messageArray;
    private NSArray _attributeKeys = null;
    
    public GSVExceptionViewer(WOContext context){
        super(context);
    }
    
    public void reset(){
        super.reset();
        currentMessage = null;
        messageArray = null;
        _attributeKeys = null;
    }
    
    public boolean synchronizesVariablesWithBindings(){
        return false;
    }

    public boolean isStateless(){
        return true;
    }
    
    public NSValidation.ValidationException exception(){
	Object e = this.valueForBinding("exception");
        return (NSValidation.ValidationException)e;
    }
    
    public boolean showAllErrors(){
        if(this.hasBinding("showAllErrors")){
            return ((Boolean)this.valueForBinding("showAllErrors")).booleanValue();
        }
        return false;
    }
    
    public NSArray attributeKeys(){
        if( _attributeKeys == null ){
            String key = (String)this.valueForBinding("attributeKey");
            _attributeKeys = NSArray.componentsSeparatedByString(key, ":");
        }
        return _attributeKeys;
    }
    
    
    public boolean show() {
        if(exception()!=null){
            return true;
        }
        return false;
    }

    public NSDictionary messageDictionary(){
        if(this.show() && this.exception().userInfo().objectForKey(GSVEngine.ERROR_DICTIONARY_KEY) != null){
            return   (NSDictionary)this.exception().userInfo().objectForKey(GSVEngine.ERROR_DICTIONARY_KEY);
        }
        return null;
    }
    
    public NSArray messages(){
        NSDictionary md = this.messageDictionary();
        if( md != null ){
            return md.allValues();
        }
       return NSArray.EmptyArray;
    }
    
    public NSArray messagesForAttribute(){
        NSMutableArray array = new NSMutableArray();
        NSDictionary d2 = this.exception().userInfo();
        if( d2 != null ){
            NSDictionary d = (NSDictionary)d2.objectForKey(GSVEngine.ERROR_DICTIONARY_KEY);
            if( d != null ){
                if( this.attributeKeys() != null ){
                    for( int i=0;i<this.attributeKeys().count();i++){
                        array.addObjectsFromArray((NSArray)d.objectForKey(this.attributeKeys().objectAtIndex(i)));
                    }
                }
            }
        }
        return array;
    }
    
    public boolean hasMessagesForAttribute(){
        NSArray messages = this.messagesForAttribute();
        return (messages != null && messages.count() > 0);
    }
}

