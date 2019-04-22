<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정하기</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/write.css'/>" />
<script src="//cdn.ckeditor.com/4.11.1/basic/ckeditor.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//CKEditor에 내용 집어 넣기
		CKEDITOR.instances.content.setData("${vo.content}");
		
		$("#regit").click(function(){
			var memberid = $("#memberid").val();
			var password = $("#password").val();
			var title = $("#title").val();
			var content = CKEDITOR.instances.content.getData();
			
			if(title.length < 1){
				alert("제목을 입력하세요.");
				$("#title").focus();
				return false;
			}else if(title.length > 70){
				alert("제목은 70글자 이내만 가능합니다.");
				$("#title").focus();
				return false;
			}else if(memberid.length < 1){
				alert("닉네임을 입력하세요.");
				$("#memberid").focus();
				return false;
			}else if(memberid.length > 10){
				alert("닉네임은 10글자 이하만 가능합니다.");
				$("#memberid").focus();
				return false;
			}else if(password.length < 1){
				alert("비밀번호를 입력하세요.");
				$("#password").focus();
				return false;
			}else if(password.length > 10){
				alert("비밀번호는 10글자 이하만 가능합니다.");
				$("#password").focus();
				return false;
			}else if(content == ''){
				alert("내용을 입력하세요.");
				CKEDITOR.instances.content.focus();
				return false;
			}else{
				$("form[name=write]").submit();
			}
			
		});
	});
	
	//새로 고침 방지
	function noEvent() {
        if (event.keyCode == 116 || (event.ctrlKey && (event.keyCode == 78 || event.keyCode == 82))) {
            if(confirm("새로고침 하시면 작성한 정보가 지워집니다. 계속하시겠습니까?")){
            	return true;
            }else{
                event.keyCode = 2;
                return false;                	
            }
        }
    }
	document.onkeydown = noEvent;
	
	function goDetail(){
		if(confirm("작성한 정보가 지워집니다. 계속하시겠습니까?")){
			location.href="<c:url value='/board/detail.do?no=" + ${vo.no} + "'/>";
		}else{
			return false;
		}
	}
</script>
</head>
<body>
<h1>글 수정하기</h1>
<form name="write" method="post" action="<c:url value='/board/edit2.do'/>">
	<table class="write_table" summary="글쓰기 양식을 위한 표로써 제목, 닉네임, 비밀번호, 내용을 제공한다.">
		<tr>
			<th>제목</th>
			<td>
				<div class="write_title">
					<input type="text" id="title" class="text" name="title" placeholder="제목을 입력하세요" value="${vo.title}"/>
				</div>
			</td>
		</tr>
		<tr>
			<th>닉네임</th>
			<td>
				<div class="write_memberid">
					<input type="text" id="memberid" class="text" name="memberid" placeholder="닉네임을 입력하세요." value="${vo.memberid}">
				</div>
			</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<div class="write_password">
					<input type="password" id="password" class="text" name="password" placeholder="비밀번호를 입력하세요.">
				</div>
			</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>
				<div class="write_content">
					<textarea id="content" class="text" name="content" placeholder="내용을 입력하세요."></textarea>
					<script>
						CKEDITOR.replace('content',{
							height:'500px'
						});
					</script>
				</div>
			</td>
		</tr>
	</table>
	<div class="write_btn">
		<input type="button" class="return" id="return" value="취소" onclick="goDetail()"/>
		<input type="button" class="regit" id="regit" value="글 수정"/>
	</div>
	<input type="hidden" name="no" value="${vo.no}" readonly="readonly"/>
	<input type="hidden" id="ip_address" name="ip_address" value="${ip}" readonly="readonly">
</form>
</body>
</html>
<%@ include file="../inc/bottom.jsp" %>