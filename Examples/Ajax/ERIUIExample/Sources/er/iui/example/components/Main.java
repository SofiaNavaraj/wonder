// Generated by the WOLips Templateengine Plug-in at Mar 7, 2008 10:03:32 PM
package er.iui.example.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

public class Main extends ERXComponent {
  private static final long serialVersionUID = 1L;

  public Main(WOContext context) {
    super(context);
  }

  public WOActionResults something() {
//    try {
//      Thread.sleep(2000);
//    }
//    catch (Throwable t) {
//    }
    System.out.println("Main.something: Going to Main2");
    return pageWithName(Main2.class);
  }
}