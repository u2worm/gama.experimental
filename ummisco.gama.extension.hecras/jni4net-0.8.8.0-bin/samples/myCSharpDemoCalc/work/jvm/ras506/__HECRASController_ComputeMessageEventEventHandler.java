// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package ras506;

@net.sf.jni4net.attributes.ClrType
public abstract class __HECRASController_ComputeMessageEventEventHandler extends system.MulticastDelegate {
    
    //<generated-proxy>
    private static system.Type staticType;
    
    protected __HECRASController_ComputeMessageEventEventHandler(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    protected __HECRASController_ComputeMessageEventEventHandler() {
            super(((net.sf.jni4net.inj.INJEnv)(null)), 0);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/String;)V")
    public abstract void Invoke(java.lang.String eventMessage);
    
    public static system.Type typeof() {
        return ras506.__HECRASController_ComputeMessageEventEventHandler.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        ras506.__HECRASController_ComputeMessageEventEventHandler.staticType = staticType;
    }
    //</generated-proxy>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class ____HECRASController_ComputeMessageEventEventHandler extends ras506.__HECRASController_ComputeMessageEventEventHandler {
    
    protected ____HECRASController_ComputeMessageEventEventHandler(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("(LSystem/String;)V")
    public native void Invoke(java.lang.String eventMessage);
}
//</generated-proxy>
