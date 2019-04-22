<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/list.css'/>" />
<script type="text/javascript">
	$(document).ready(function(){
		$("#regit").click(function(){
			location.href="<c:url value='/board/write.do'/>";
		});
	});
	
	function pageFunc(curPage){
		document.frmPage.currentPage.value=curPage;
		frmPage.submit();
	}
</script>
</head>
<body>
<form name="frmPage" method="post" action="<c:url value='/board/list.do'/>">
	<input type="hidden" name="currentPage">
</form>
	<h1>게시판</h1>
	<div class="list_div_body">
		<table class="board" summary="게시판에 관한 표로써 번호, 제목, 작성자, 작성일, 조회수에 대한 정보를 제공합니다.">
			<colgroup>
				<col style="width:10%;"/> <!-- 번호 -->
				<col style="width:*;"/> <!-- 제목 -->
				<col style="width:15%;"/> <!-- 작성자 -->
				<col style="width:10%;"/> <!-- 등록일 -->
				<col style="width:10%;"/> <!-- 조회수 -->
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일</th>
					<th scope="col">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty list}">
					<tr>
						<td colspan="5" style="text-align: center">글이 존재하지 않습니다.</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach var="vo" items="${list}">
						<tr style="text-align:center">
							<td>${vo.no}</td>
							<td style="text-align: left">
								<c:if test="${!empty vo.fileName}">
									<span style="vertical-align: middle; margin-right: 8px;">
										<img src='<c:url value='/images/disk.png'/>' alt="disk" style="width:17px; height:17px;">
									</span>
								</c:if>
								<a href="<c:url value='/board/detail.do?no=${vo.no}'/>">
									<c:if test="${fn:length(vo.title)>35}">
										${fn:substring(vo.title, 0, 35)}.....
									</c:if>
									<c:if test="${fn:length(vo.title)<=35}">
										${vo.title}
									</c:if>
								</a>
								<c:if test="${vo.newImgTerm < 24}">
									&nbsp;&nbsp;&nbsp;
									<img alt="new 이미지" src="<c:url value='/images/new.png'/>">
								</c:if>
							</td>
							<td>
								<c:if test="${fn:length(vo.memberid) > 10}">
									${fn:substring(vo.memberid, 0, 10)}...
								</c:if>
								<c:if test="${fn:length(vo.memberid) <= 10}">
									${vo.memberid}
								</c:if>
							</td>
							<td>
								<c:if test="${vo.newImgTerm < 24}">
									<fmt:formatDate value="${vo.regdate}" pattern="HH:mm:ss"/>
								</c:if>
								<c:if test="${vo.newImgTerm >= 24}">
									<fmt:formatDate value="${vo.regdate}" pattern="yyyy.MM.dd."/>
								</c:if>
							</td>
							<td><fmt:formatNumber value="${vo.cnt}" pattern="#,###"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="page">
		<c:if test="${pageVo.firstPage > 1}">
			<a href="#" onclick="pageFunc(${pageVo.firstPage-1})">
				<span class="pageNum3">&lt;PREV</span>
			</a>
		</c:if>
		
		<c:forEach var="i" begin="${pageVo.firstPage}" end="${pageVo.lastPage}">
			<c:if test="${i == pageVo.currentPage}">
				<span class="pageNum1">${i}</span>
			</c:if>
			<c:if test="${i != pageVo.currentPage}">
				<a href="#" onclick="pageFunc(${i})">
					<span class="pageNum2">${i}</span>
				</a>
			</c:if>
			
		</c:forEach>
		<c:if test="${pageVo.lastPage < pageVo.totalPage}">
			<a href="#" onclick="pageFunc(${pageVo.lastPage+1})">
				<span class="pageNum3">NEXT&gt;</span>
			</a>
		</c:if>
	</div>
	<div class="list_div_btn">
		<input type="button" id="regit" value="글쓰기" style="cursor:pointer;">
	</div>
</body>
</html>
<%@ include file="../inc/bottom.jsp" %>