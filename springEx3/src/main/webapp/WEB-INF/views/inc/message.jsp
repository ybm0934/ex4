<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
<c:if test="${!empty msg}">
	<script type="text/javascript">
		alert("${msg}");
		location.href = "<c:url value='${url}'/>";
	</script>
</c:if>
<c:if test="${empty msg}">
	<script type="text/javascript">
		location.href = "<c:url value='${url}'/>";
	</script>
</c:if>
 --%>

<script type="text/javascript">
	console.log('msg=${msg}');
	console.log('url=${url}');
	
	<c:if test="${!empty msg }">
		alert("${msg}");
	</c:if>
	location.href="<c:url value='${url}'/>";
</script>