package com.camel.research.aop;
/**
 * javassist对class源码处理的超类，所有集成子类可以override weaveProcessor方法
 * @author camel.deng
 * @since 2013-1-20
 */
import javassist.ClassClassPath;
import javassist.ClassPool;

public class BytecodeProcessor
{
    protected static ClassPool pool;

    /**
     * 代码织入方法，这里只是初始化ClassPool，及设定pool的搜索路径。
     * 如果使用jboss、tomcat等server的时候，由于使用了各种不同的classloader，所以需要指定当前类的路径。
     */
    public Object weaveProcessor()
    {
        pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(getClass()));
        return null;
    }
}
