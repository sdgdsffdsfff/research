package com.camel.research.pmd;
/**
 * Calling overridable methods during construction poses a risk of invoking methods on an incompletely constructed object and can be difficult to discern. 
 * It may leave the sub-class unable to construct its superclass or forced to replicate the construction process completely within itself, 
 * losing the ability to call super(). 
 * If the default constructor contains a call to an overridable method, 
 * the subclass may be completely uninstantiable. 
 * Note that this includes method calls throughout the control flow graph - i.e., 
 * if a constructor Foo() calls a private method bar() that calls a public method buz(), 
 * this denotes a problem. 
 * 
 * 子类调用super constructor,由于子类override了toString方法，super constructor调用了toString方法，
 * 但是子类尚未完成实例化，所以会抛出NullPointerException
 * @author dengqb
 * @date 2013年10月10日
 */
public class ConstructorCallsOverridableMethodSubclass extends ConstructorCallsOverridableMethod {

    private String name;
    public ConstructorCallsOverridableMethodSubclass(){
      super(); //Automatic call leads to NullPointerException
      name = "JuniorClass";
    }
    public String toString(){
      return name.toUpperCase();
    }
}
