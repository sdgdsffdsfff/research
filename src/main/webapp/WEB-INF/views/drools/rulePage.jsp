<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rule Page</title>
</head>
<body>
    <form id="" method="POST" action="${ctx}/drools/rule/new">
        <div>
            <ul>
                <li>规则名称：
                    <input type="text" name="ruleName" >
                </li>
                <li>订单来源：
                    <input type="text" name="expr_107" >
                </li>
                <li>订单金额：
                    <input type="checkbox" name="isLeft" >
                    <input type="text" name="expr_104" >
                    <input>
                </li>
                <li>目的国家包含：
                    <input type="text" name="expr_109" >
                    <input type="text" name="expr_109" >
                    <input type="text" name="expr_109" >
                </li>
            </ul>
        </div>
        <div>
            <button>提交</button>
        </div>
    </form>
    
    <form id="" method="POST" action="${ctx}/drools/rule/submit-order">
        <div>
            <ul>
                <li>订单金额：
                    <input type="text" name="amount" >
                </li>
                <li>订单来源：
                    <input type="text" name="source" >
                </li>
                <li>目的国家：
                    <input type="text" name="destination" >
                </li>
            </ul>
        </div>
        <div>
            <button>提交订单由规则引擎处理</button>
        </div>
    </form>
</body>
</html>