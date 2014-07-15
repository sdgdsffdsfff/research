<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fpx" uri="/fpx-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>websocket demo page</title>
</head>
<body>
    <div id="connect-container">
        <div>
            <button id="connect">Connect</button>
            <button id="disconnect" disabled="disabled">Disconnect</button>
        </div>
        <div>
            <textarea id="message" style="width: 350px">Here is a message!</textarea>
        </div>
        <div>
            <button id="echo" disabled="disabled">Echo message</button>
        </div>
    </div>
    <div id="console-container">
        <div id="console"></div>
    </div>
</body>
<fpx:script type="text/javascript" src="/js/stomp/stomp.js,/js/jquery/jquery-1.10.1.js" />
<script type="text/javascript">
$(function(){
    var ws = null;
    var url = null;
    var transports = [];

    url = "ws://"+ window.location.host + "/research/ws";
    
    function setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('echo').disabled = !connected;
    }
    
    function connect() {
        if (!url) {
            alert('Select whether to use W3C WebSocket or SockJS');
            return;
        }
    
        ws = (url.indexOf('sockjs') != -1) ? new SockJS(url, undefined, {
            protocols_whitelist : transports
        }) : new WebSocket(url);
        
        ws.onopen = function() {
            setConnected(true);
            log('Info: connection opened.');
        };
        ws.onmessage = function(event) {
            log('Received: ' + event.data);
        };
        ws.onclose = function(event) {
            setConnected(false);
            log('Info: connection closed.');
            log(event);
        };
    }

    function disconnect() {
        if (ws != null) {
            ws.close();
            ws = null;
        }
        setConnected(false);
    }

    function echo() {
        if (ws != null) {
            var message = document.getElementById('message').value;
            log('Sent: ' + message);
            ws.send(message);
        } else {
            alert('connection not established, please connect.');
        }
    }

    function updateUrl(urlPath) {
        if (urlPath.indexOf('sockjs') != -1) {
            url = urlPath;
            document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
        }
        else {
          if (window.location.protocol == 'http:') {
              url = 'ws://' + window.location.host + urlPath;
          } else {
              url = 'wss://' + window.location.host + urlPath;
          }
          document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
        }
    }

    function updateTransport(transport) {
      transports = (transport == 'all') ?  [] : [transport];
    }
    
    function log(message) {
        var console = document.getElementById('console');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(message));
        console.appendChild(p);
        while (console.childNodes.length > 25) {
            console.removeChild(console.firstChild);
        }
        console.scrollTop = console.scrollHeight;
    }

    var $connectContainer = $('#connect-container');
    var $consoleContainer = $('#console-container');

    $connectContainer.on('click','#connect',function(){
    	connect();
    });

    $connectContainer.on('click','#disconnect',function(){
    	disconnect();
    });

    $connectContainer.on('click','#echo',function(){
    	echo();
    });
    
});
</script>
</html>