<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 상세보기</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/detail.css'/>" />
<script type="text/javascript">
	$(document).ready(function(){
		$("#edit").click(function(){
			location.href="<c:url value='/board/confirm.do?no=" + ${param.no} + "'/>";
		});
		$("#delete").click(function(){
			location.href="<c:url value='/board/delete1.do?no=" + ${param.no} + "'/>";
		});
		$("#list").click(function(){
			location.href="<c:url value='/board/list.do'/>";
		});
	});
</script>
</head>
<body>
<h1>상세보기</h1>
<div class="detail_div_body>">
	<table class="detail_table">
		<tr>
			<th colspan="2">${vo.title}</th>
		</tr>
		<tr>
			<th>
				<span style="margin-left: 10px;">${vo.memberid}</span><span class="wall">|</span>
				<span style="vertical-align: middle; margin-right: 5px;">
					<img src='<c:url value="/images/clock.png"/>' alt="clock" style="width:20px; height:20px; opacity:0.5;">
				</span>
				<fmt:formatDate value="${vo.regdate}" pattern="yyyy.MM.dd. HH:mm:ss"/>
			</th>
			<th>
				<div>
					<span style="margin-right: 10px;">조회 수 <fmt:formatNumber value="${vo.cnt}" pattern="#,###"/></span>
				</div>
			</th>
		</tr>
		<c:if test="${!empty fileInfo}">
			<tr>
				<td colspan="2" class="child_3">
					<a href='<c:url value="/board/download.do?no=${vo.no}&fileName=${vo.fileName}"/>'>
						<span style="vertical-align: middle; margin-right: 5px;">
							<img src='<c:url value='/images/disk.png'/>' alt="disk" style="width:17px; height:17px;">
						</span>
						${fileInfo}
						<span style="color: red; font-weight: bold; margin-left: 15px;">
							+ <fmt:formatNumber value="${downInfo}" pattern="#,###"/>
						</span>
					</a>
				</td>
			</tr>
		</c:if>
		<tr>
			<td colspan="2">${vo.content}</td>
		</tr>
	</table>
	<div class="detail_btn">
		<input type="button" id="edit" class="btn" value="수정">
		<input type="button" id="delete" class="btn" value="삭제">
		<input type="button" id="list" class="btn" value="목록">
	</div>
</div>
</body>
</html>
<%@ include file="../inc/bottom.jsp" %>