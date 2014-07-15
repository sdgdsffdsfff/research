<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fpx" uri="/fpx-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>websocket demo page</title>
</head>
<body>

</body>
<fpx:script type="text/javascript" src="/js/stomp/stomp.js" />
<script type="text/javascript">
var socket = new WebSocket("/stomp/portfolio");
var stompClient = Stomp.over(socket);
stompClient.connect({},function(frame){
	alert("wah");
});
</script>
</html>