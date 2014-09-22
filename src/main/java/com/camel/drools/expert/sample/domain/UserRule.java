package com.camel.drools.expert.sample.domain;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.camel.drools.expert.sample.service.KBaseContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

/**
 * <pre>获取用户从界面选择的规则和条件，并对应到dsl的key值</pre>
 * 
 * @author dengqb
 * @date 2014年8月29日
 */
public class UserRule {
    private Map<String,Object> ruleConditionMap = new HashMap<String,Object>();
    
    /**
     * 用户设定的规则及条件，比如：订单金额amount == 50.00
     */
    private DataObject ruleCondition;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 用户编码，全系统唯一
     */
    private String userCode;
    
    /**
     * 规则表达式的命名分隔符
     */
    private final static String spliter = "_";
    
    private ObjectMapper om;
    
    public UserRule(){
        ruleCondition = DataFactory.INSTANCE.create("http://drools.research.com/xsd/RuleCondition", "RuleCondition");
    }
    /**
     * 从 httpServletRequest获取paramaterMap，转换成内部ruleConditionMap
     * @param paramaterMap httpServletRequest中的paramaterMap
     */
    public DataObject loadRuleCondition(Map<String,String[]> paramaterMap) throws Exception{
        Set<String> keySet = paramaterMap.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()){
            String key = it.next();
            String[] values = paramaterMap.get(key);
            if (key.equals("ruleName")){
                this.ruleName = values[0];
            }else{
                if (values.length > 1){
                    List<String> valueLst = Arrays.asList(values);
                    ruleConditionMap.put(key, valueLst);
                    //ruleCondition.set(getSDOName(key), valueLst);
                }else{
                    ruleConditionMap.put(key, values[0]);
                    //ruleCondition.set(getSDOName(key), values[0]);
                }
            }
        }
        
        return ruleCondition;
    }
    
    /**
     * 通过用户的规则表达式名称，找到SDO对象的属性名称
     * @param expr 用户的规则表达式名称，比如expr_101,expr_102
     * @return
     */
    public String getSDOName(String expr){
        Properties props = KBaseContext.getExprToSDOMapping();
        String sdoName = props.getProperty(expr);
        return sdoName;
    }
    
    /**
     * 判断用户规则集中是否有指定类型，比如是否有order，buyer规则
     * @param type 类型
     * @return true|false
     */
    public boolean hasTypeof (String type){
        Iterator<String> it = ruleConditionMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            if (key.split(spliter)[1].startsWith(type)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断指定用户规则是否属于指定类型
     * @param expression 用户规则
     * @param type 类型
     * @return true|false
     */
    public boolean isTypof(String expression,String type){
        if (expression.split(spliter)[1].startsWith(type)){
            return true;
        }
        return false;
    }
    
    /**
     * 把用户的规则和条件转换为json字符串，便于存储
     * @return 用户规则和条件json字符串
     */
    public String convertUserRuleTOJson(){
        if (om == null){
            om = new ObjectMapper();
        }
        String rcStr = null;
        try {
            rcStr = om.writeValueAsString(ruleConditionMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return rcStr;
    }
    
    /**
     * 从json格式字符串加载用户规则和条件，并赋值SDO对象
     * @param ruleConditionJson json字符串
     * @return SDO对象
     */
    public DataObject loadRuleConditionFromJson(String ruleConditionJson){
        if (om == null){
            om = new ObjectMapper();
        }
        if ( StringUtils.isBlank(ruleConditionJson)){
            return null;
        }
        try {
            ruleConditionMap = om.readValue(ruleConditionJson, Map.class);
            
            Set<String> keySet = ruleConditionMap.keySet();
            Iterator<String> it = keySet.iterator();
            while (it.hasNext()){
                String key = it.next();
                Object values = ruleConditionMap.get(key);
                ruleCondition.set(getSDOName(key), values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ruleCondition;
    }
    
    public Map<String,Object> getRuleConditionMap() {
        return ruleConditionMap;
    }

    public void setRuleConditionMap(Map<String,Object> ruleConditionMap) {
        this.ruleConditionMap = ruleConditionMap;
    }
    public String getRuleName() {
        return ruleName;
    }
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    
    public DataObject getRuleCondition() {
        return ruleCondition;
    }
    public void setRuleCondition(DataObject ruleCondition) {
        this.ruleCondition = ruleCondition;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
