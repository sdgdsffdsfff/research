/**
 * 
 */
package com.camel.research.aop;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author cameldeng
 * @since 2013年7月15日
 */

public class JavaAssistResearch extends BytecodeProcessor {

	public Object weaveProcessor(){
		super.weaveProcessor();
        /*
         * 在生成的class文件中增加import com.utils.AOPDynamicConfigurator.javassistaop.Spring3PlaceHolderProcessor
         */
        pool.importPackage("com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap");
        try
        {
            CtClass cc = pool.get("com.enterfly.framework.service.datasource.BaseDataSourceService");
            String params[] = {
                "java.util.Properties", "org.w3c.dom.Element"
            };
            CtClass paramTypes[] = pool.get(params);
            /*
             * 获取指定参数的方法，由于resolvePlaceholder方法存在同名重载，所有需要指定参数类型
             */
            CtMethod cm = cc.getDeclaredMethod("initDatabaseInfo", paramTypes);
            /*
             * 在原代码尾部插入以下代码段。
             */
            StringBuffer statement = new StringBuffer();
            statement.append("{")
            	.append("db.setProperty(URL, ConfigParamMap.getValue(\"oracle.url\"));")
            	.append("db.setProperty(USER, ConfigParamMap.getValue(\"oracle.user\"));")
            	.append("db.setProperty(PASSWORD, ConfigParamMap.getValue(\"oracle.password\"));")
            	.append("db.setProperty(SCHEMA, ConfigParamMap.getValue(\"oracle.schema\"));")
            	.append("System.out.println(\"db url=\"+db.getProperty(URL));")
            	.append("}");

            System.out.println("db user= from ConfigParamMap get db user");
            cm.insertAfter(statement.toString());
            cc.writeFile();
            cc.toClass();
        }
        catch(NotFoundException e)
        {
            e.printStackTrace();
        }
        catch(CannotCompileException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
