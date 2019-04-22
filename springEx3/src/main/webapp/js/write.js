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

//파일 업로드 체크
function fileCheck( file ) {
	
	//확장자 체크
	pathpoint = file.value.lastIndexOf('.');
	filepoint = file.value.substring(pathpoint + 1, file.length);
	filetype = filepoint.toLowerCase(); //소문자로 변환하여 리턴
	if (filetype == 'exe') alert('첨부할 수 없는 파일입니다.');
	
	//사이즈 체크
    var maxSize  = 10 * 1024 * 1024    //10MB
    var fileSize = 0;
    
    //브라우저 확인
	var browser = navigator.appName;
	
	//익스플로러일 경우
	if (browser == "Microsoft Internet Explorer") {
		var oas = new ActiveXObject("Scripting.FileSystemObject");
		fileSize = oas.getFile( file.value ).size;
	}
	//익스플로러가 아닐경우
	else {
		fileSize = file.files[0].size;
	}
	
	//파일 사이즈 변환
	if(((fileSize / 1024 / 1024).toFixed(2)) >= 0.1){
		sfileSize = (fileSize / 1024 / 1024).toFixed(2) + "MB";
	}else {
		sfileSize = (fileSize / 1024).toFixed(2) + "KB";
	}

	document.getElementById("sizeSpn").innerHTML = sfileSize;
	document.getElementById("fileSize").value = fileSize / 1024 / 1024;
	document.getElementById("fileExt").value = filetype;

	if(fileSize > maxSize) {
        alert("파일사이즈 : " + sfileSize + "\n첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
        return;
    }
    
}