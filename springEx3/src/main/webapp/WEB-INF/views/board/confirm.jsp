<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 확인</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/confirm.css'/>" />
<script type="text/javascript">
	$(document).ready(function(){
		$("#cfm_pwd").focus();
		
		$("#cfm_ok").click(function(){
			$("form[name=confirm]").submit();
		});
		
		$("#cfm_no").click(function(){
			location.href="<c:url value='/board/detail.do?no=" + ${param.no} + "'/>";
		});
	});
	
	//새로 고침 방지
	function noEvent() {
        if (event.keyCode == 116 || (event.ctrlKey && (event.keyCode == 78 || event.keyCode == 82))) {
        	event.keyCode = 2;
            return false;
        }
    }
	document.onkeydown = noEvent;
	
</script>
</head>
<body oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
<form name="confirm" method="post" action="<c:url value='/board/edit1.do'/>">
<input type="hidden" name="no" value="${param.no}" readonly="readonly"> 
	<div class="cfm_pwd_div">
		<p>비밀번호를 입력해주십시오.</p>
		<input type="password" name="password" class="cfm_pwd" id="cfm_pwd">
		<div class="cfm_btn_div">
			<input type="button" name="cfm_ok" class="cfm_btn" id="cfm_ok" value="확인">
			<input type="button" name="cfm_no" class="cfm_btn" id="cfm_no" value="취소">
		</div>
	</div>
</form>
</body>
</html>
<%@ include file="../inc/bottom.jsp" %>