//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RAS506 {
    
    
    #region Component Designer generated code 
    public partial class HECRASGeometry_ {
        
        public static global::java.lang.Class _class {
            get {
                return global::RAS506.@__HECRASGeometry.staticClass;
            }
        }
    }
    #endregion
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaProxyAttribute(typeof(global::RAS506.HECRASGeometry), typeof(global::RAS506.HECRASGeometry_))]
    [global::net.sf.jni4net.attributes.ClrWrapperAttribute(typeof(global::RAS506.HECRASGeometry), typeof(global::RAS506.HECRASGeometry_))]
    internal sealed partial class @__HECRASGeometry : global::java.lang.Object, global::RAS506.HECRASGeometry {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_nRiver0;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_RiverName1;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_RiverIndex2;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_nReach3;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_ReachName4;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_ReachIndex5;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_ReachInvert_nPoints6;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_ReachInvert_Points7;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_nNode8;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_NodeRS9;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_NodeIndex10;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_NodeType11;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_NodeCType12;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_NodeCutLine_nPoints13;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_NodeCutLine_Points14;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_Save15;
        
        private @__HECRASGeometry(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::RAS506.@__HECRASGeometry.staticClass = @__class;
            global::RAS506.@__HECRASGeometry.j4n_nRiver0 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "nRiver", "()I");
            global::RAS506.@__HECRASGeometry.j4n_RiverName1 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "RiverName", "(Lnet/sf/jni4net/Ref;)Ljava/lang/String;");
            global::RAS506.@__HECRASGeometry.j4n_RiverIndex2 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "RiverIndex", "(Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_nReach3 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "nReach", "(Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_ReachName4 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "ReachName", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;");
            global::RAS506.@__HECRASGeometry.j4n_ReachIndex5 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "ReachIndex", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_ReachInvert_nPoints6 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "ReachInvert_nPoints", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_ReachInvert_Points7 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "ReachInvert_Points", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;" +
                    ")V");
            global::RAS506.@__HECRASGeometry.j4n_nNode8 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "nNode", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_NodeRS9 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "NodeRS", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;");
            global::RAS506.@__HECRASGeometry.j4n_NodeIndex10 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "NodeIndex", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_NodeType11 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "NodeType", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_NodeCType12 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "NodeCType", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;");
            global::RAS506.@__HECRASGeometry.j4n_NodeCutLine_nPoints13 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "NodeCutLine_nPoints", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I");
            global::RAS506.@__HECRASGeometry.j4n_NodeCutLine_Points14 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "NodeCutLine_Points", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;" +
                    "Lnet/sf/jni4net/Ref;)V");
            global::RAS506.@__HECRASGeometry.j4n_Save15 = @__env.GetMethodID(global::RAS506.@__HECRASGeometry.staticClass, "Save", "()V");
        }
        
        public int nRiver() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_nRiver0)));
            }
        }
        
        public string RiverName(int riv) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2CString(@__env, @__env.CallObjectMethodPtr(this, global::RAS506.@__HECRASGeometry.j4n_RiverName1, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv)));
            }
        }
        
        public int RiverIndex(string RiverName) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_RiverIndex2, global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<string>(@__env, RiverName))));
            }
        }
        
        public int nReach(int riv) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_nReach3, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv))));
            }
        }
        
        public string ReachName(int riv, int rch) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2CString(@__env, @__env.CallObjectMethodPtr(this, global::RAS506.@__HECRASGeometry.j4n_ReachName4, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch)));
            }
        }
        
        public int ReachIndex(int riv, string ReachName) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_ReachIndex5, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<string>(@__env, ReachName))));
            }
        }
        
        public int ReachInvert_nPoints(int riv, int rch) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_ReachInvert_nPoints6, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch))));
            }
        }
        
        public void ReachInvert_Points(int riv, int rch, global::System.Array PointX, global::System.Array PointY) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 18)){
            @__env.CallVoidMethod(this, global::RAS506.@__HECRASGeometry.j4n_ReachInvert_Points7, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<global::System.Array>(@__env, PointX), global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<global::System.Array>(@__env, PointY));
            }
        }
        
        public int nNode(int riv, int rch) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 14)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_nNode8, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch))));
            }
        }
        
        public string NodeRS(int riv, int rch, int n) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 16)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2CString(@__env, @__env.CallObjectMethodPtr(this, global::RAS506.@__HECRASGeometry.j4n_NodeRS9, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(n)));
            }
        }
        
        public int NodeIndex(int riv, int rch, string Rs) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 16)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_NodeIndex10, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<string>(@__env, Rs))));
            }
        }
        
        public int NodeType(int riv, int rch, int n) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 16)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_NodeType11, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(n))));
            }
        }
        
        public string NodeCType(int riv, int rch, int n) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 16)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2CString(@__env, @__env.CallObjectMethodPtr(this, global::RAS506.@__HECRASGeometry.j4n_NodeCType12, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(n)));
            }
        }
        
        public int NodeCutLine_nPoints(int riv, int rch, int n) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 16)){
            return ((int)(@__env.CallIntMethod(this, global::RAS506.@__HECRASGeometry.j4n_NodeCutLine_nPoints13, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(n))));
            }
        }
        
        public void NodeCutLine_Points(int riv, int rch, int n, global::System.Array PointX, global::System.Array PointY) {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 20)){
            @__env.CallVoidMethod(this, global::RAS506.@__HECRASGeometry.j4n_NodeCutLine_Points14, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(riv), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(rch), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(n), global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<global::System.Array>(@__env, PointX), global::net.sf.jni4net.utils.Convertor.ParStrongC2Jp<global::System.Array>(@__env, PointY));
            }
        }
        
        public void Save() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            @__env.CallVoidMethod(this, global::RAS506.@__HECRASGeometry.j4n_Save15);
            }
        }
        
        private static global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> @__Init(global::net.sf.jni4net.jni.JNIEnv @__env, global::java.lang.Class @__class) {
            global::System.Type @__type = typeof(__HECRASGeometry);
            global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod> methods = new global::System.Collections.Generic.List<global::net.sf.jni4net.jni.JNINativeMethod>();
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "nRiver", "nRiver0", "()I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "RiverName", "RiverName1", "(Lnet/sf/jni4net/Ref;)Ljava/lang/String;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "RiverIndex", "RiverIndex2", "(Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "nReach", "nReach3", "(Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "ReachName", "ReachName4", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "ReachIndex", "ReachIndex5", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "ReachInvert_nPoints", "ReachInvert_nPoints6", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "ReachInvert_Points", "ReachInvert_Points7", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;" +
                        ")V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "nNode", "nNode8", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "NodeRS", "NodeRS9", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "NodeIndex", "NodeIndex10", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "NodeType", "NodeType11", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "NodeCType", "NodeCType12", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "NodeCutLine_nPoints", "NodeCutLine_nPoints13", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "NodeCutLine_Points", "NodeCutLine_Points14", "(Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;" +
                        "Lnet/sf/jni4net/Ref;)V"));
            methods.Add(global::net.sf.jni4net.jni.JNINativeMethod.Create(@__type, "Save", "Save15", "()V"));
            return methods;
        }
        
        private static int nRiver0(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()I
            // ()I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).nRiver()));
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle RiverName1(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv) {
            // (Lnet/sf/jni4net/Ref;)Ljava/lang/String;
            // (I)LSystem/String;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.StrongC2JString(@__env, ((global::RAS506._HECRASGeometry)(@__real)).RiverName(ref __ref_riv));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int RiverIndex2(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle RiverName) {
            // (Lnet/sf/jni4net/Ref;)I
            // (LSystem/String;)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            string @__ref_RiverName = net.sf.jni4net.Ref.GetValue<string>(@__env, RiverName);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).RiverIndex(ref __ref_RiverName)));
            net.sf.jni4net.Ref.SetValue<string>(@__env, RiverName, @__ref_RiverName);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int nReach3(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv) {
            // (Lnet/sf/jni4net/Ref;)I
            // (I)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).nReach(ref __ref_riv)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle ReachName4(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;
            // (II)LSystem/String;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.StrongC2JString(@__env, ((global::RAS506._HECRASGeometry)(@__real)).ReachName(ref __ref_riv, ref __ref_rch));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int ReachIndex5(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle ReachName) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I
            // (ILSystem/String;)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            string @__ref_ReachName = net.sf.jni4net.Ref.GetValue<string>(@__env, ReachName);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).ReachIndex(ref __ref_riv, ref __ref_ReachName)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<string>(@__env, ReachName, @__ref_ReachName);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int ReachInvert_nPoints6(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I
            // (II)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).ReachInvert_nPoints(ref __ref_riv, ref __ref_rch)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static void ReachInvert_Points7(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle PointX, global::net.sf.jni4net.utils.JniLocalHandle PointY) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)V
            // (IILSystem/Array;LSystem/Array;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            global::System.Array @__ref_PointX = net.sf.jni4net.Ref.GetValue<global::System.Array>(@__env, PointX);
            global::System.Array @__ref_PointY = net.sf.jni4net.Ref.GetValue<global::System.Array>(@__env, PointY);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            ((global::RAS506._HECRASGeometry)(@__real)).ReachInvert_Points(ref __ref_riv, ref __ref_rch, ref __ref_PointX, ref __ref_PointY);
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<global::System.Array>(@__env, PointX, @__ref_PointX);
            net.sf.jni4net.Ref.SetValue<global::System.Array>(@__env, PointY, @__ref_PointY);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static int nNode8(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I
            // (II)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).nNode(ref __ref_riv, ref __ref_rch)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle NodeRS9(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle n) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;
            // (III)LSystem/String;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            int @__ref_n = net.sf.jni4net.Ref.GetValue<int>(@__env, n);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.StrongC2JString(@__env, ((global::RAS506._HECRASGeometry)(@__real)).NodeRS(ref __ref_riv, ref __ref_rch, ref __ref_n));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<int>(@__env, n, @__ref_n);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int NodeIndex10(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle Rs) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I
            // (IILSystem/String;)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            string @__ref_Rs = net.sf.jni4net.Ref.GetValue<string>(@__env, Rs);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).NodeIndex(ref __ref_riv, ref __ref_rch, ref __ref_Rs)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<string>(@__env, Rs, @__ref_Rs);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int NodeType11(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle n) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I
            // (III)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            int @__ref_n = net.sf.jni4net.Ref.GetValue<int>(@__env, n);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).NodeType(ref __ref_riv, ref __ref_rch, ref __ref_n)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<int>(@__env, n, @__ref_n);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static global::net.sf.jni4net.utils.JniHandle NodeCType12(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle n) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)Ljava/lang/String;
            // (III)LSystem/String;
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            global::net.sf.jni4net.utils.JniHandle @__return = default(global::net.sf.jni4net.utils.JniHandle);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            int @__ref_n = net.sf.jni4net.Ref.GetValue<int>(@__env, n);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = global::net.sf.jni4net.utils.Convertor.StrongC2JString(@__env, ((global::RAS506._HECRASGeometry)(@__real)).NodeCType(ref __ref_riv, ref __ref_rch, ref __ref_n));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<int>(@__env, n, @__ref_n);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static int NodeCutLine_nPoints13(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle n) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)I
            // (III)I
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            int @__return = default(int);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            int @__ref_n = net.sf.jni4net.Ref.GetValue<int>(@__env, n);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            @__return = ((int)(((global::RAS506._HECRASGeometry)(@__real)).NodeCutLine_nPoints(ref __ref_riv, ref __ref_rch, ref __ref_n)));
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<int>(@__env, n, @__ref_n);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
            return @__return;
        }
        
        private static void NodeCutLine_Points14(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj, global::net.sf.jni4net.utils.JniLocalHandle riv, global::net.sf.jni4net.utils.JniLocalHandle rch, global::net.sf.jni4net.utils.JniLocalHandle n, global::net.sf.jni4net.utils.JniLocalHandle PointX, global::net.sf.jni4net.utils.JniLocalHandle PointY) {
            // (Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;Lnet/sf/jni4net/Ref;)V
            // (IIILSystem/Array;LSystem/Array;)V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            int @__ref_riv = net.sf.jni4net.Ref.GetValue<int>(@__env, riv);
            int @__ref_rch = net.sf.jni4net.Ref.GetValue<int>(@__env, rch);
            int @__ref_n = net.sf.jni4net.Ref.GetValue<int>(@__env, n);
            global::System.Array @__ref_PointX = net.sf.jni4net.Ref.GetValue<global::System.Array>(@__env, PointX);
            global::System.Array @__ref_PointY = net.sf.jni4net.Ref.GetValue<global::System.Array>(@__env, PointY);
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            ((global::RAS506._HECRASGeometry)(@__real)).NodeCutLine_Points(ref __ref_riv, ref __ref_rch, ref __ref_n, ref __ref_PointX, ref __ref_PointY);
            net.sf.jni4net.Ref.SetValue<int>(@__env, riv, @__ref_riv);
            net.sf.jni4net.Ref.SetValue<int>(@__env, rch, @__ref_rch);
            net.sf.jni4net.Ref.SetValue<int>(@__env, n, @__ref_n);
            net.sf.jni4net.Ref.SetValue<global::System.Array>(@__env, PointX, @__ref_PointX);
            net.sf.jni4net.Ref.SetValue<global::System.Array>(@__env, PointY, @__ref_PointY);
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        private static void Save15(global::System.IntPtr @__envp, global::net.sf.jni4net.utils.JniLocalHandle @__obj) {
            // ()V
            // ()V
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.Wrap(@__envp);
            try {
            global::RAS506.HECRASGeometry @__real = global::net.sf.jni4net.utils.Convertor.FullJ2C<global::RAS506.HECRASGeometry>(@__env, @__obj);
            ((global::RAS506._HECRASGeometry)(@__real)).Save();
            }catch (global::System.Exception __ex){@__env.ThrowExisting(__ex);}
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::RAS506.@__HECRASGeometry(@__env);
            }
        }
    }
    #endregion
}
