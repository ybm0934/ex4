package com.sp.ex3.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.sp.ex3.board.controller.BoardController;

public class Utility {
	public static final int BLOCK_SIZE = 10;
	public static final int RECORD_COUNT_PER_PAGE = 15; // 게시판에서 한 페이지에 보여줄 레코드 개수
	public static final int RECORD_COUNT_TEN = 10; // 우편번호 찾기에서 한 페이지에 보여줄 레코드 개수

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	public static String getUserId(String userCode, HttpSession session) {
		String result = "";
		if ("1".equals(userCode)) {
			result = (String) session.getAttribute("memberId");
		} else if ("2".equals(userCode)) {
			result = (String) session.getAttribute("sMemberId");
		}

		return result;
	}

	// 아이피 가져오기
	public static String getIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		logger.info(">>>> X-FORWARDED-FOR : " + ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info(">>>> Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
			logger.info(">>>> WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.info(">>>> HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		logger.info(">>>> Result : IP Address : " + ip);

		return ip;

	}

	// 중복 파일명 수정하기 방법1
	public static String getUniqueFileName(String dirPath, String fileName) {
		// test.txt => test_1.txt => test_2.txt

		// [1] 순수 파일명만 가져오기 test
		String fName = fileName.substring(0, fileName.lastIndexOf(".")); // test

		// [2] 순수 확장자만 가져오기(.포함) .txt
		String ext = fileName.substring(fileName.lastIndexOf(".")); // .확장자만
		System.out.println("fName : " + fName + ", ext : " + ext);

		// 같은 이름의 파일 존재여부, 우선 있다고 가정.
		boolean bExist = true;
		int count = 0;
		while (bExist) {
			// D:\\site\\uploaded\\test.txt
			if (new File(dirPath, fileName).exists()) {
				count++;
				fileName = fName + "_" + count + ext; // test_1.txt
				System.out.println("fName:" + fName + ", ext:" + ext + ", newFileName :" + fileName);
			} else {
				bExist = false;
			}
		} // while

		return fileName;

	}

	// 중복 파일명 수정하기 방법2-1
	public static String getTimeStamp() {
		String result = "";

		// 문자열로 변환하기 위한 패턴(년-월-일 시:분:초:밀리초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date today = new Date();
		result = sdf.format(today);
		System.out.println("getTimeStamp():" + result);

		return result;

	}

	// 중복 파일명 수정하기 방법2-2
	public static String getUniqueFileName2(String fileName) {
		// 파일명이 중복될 경우 파일이름 변경하기
		// 파일명에 현재시간을 붙여서 변경된 파일이름 구하기
		// a.txt => a_20150519123315235.txt

		// 순수파일명만 구하기
		int idx = fileName.lastIndexOf(".");
		String fileNm = fileName.substring(0, idx); // a

		// 확장자 구하기
		String ext = fileName.substring(idx); // .txt

		// 변경된 파일 이름
		String result = fileNm + "_" + getTimeStamp() + ext;
		logger.info("변경된 파일이름 : {}", result);

		return result;

	}

	// Cookie 생성
	public static void makeCookie(String cName, String cValue, int eTime, HttpServletResponse response) {
		Cookie ck = new Cookie(cName, cValue); // 이름, 값 지정
		ck.setMaxAge(eTime); // 유효시간 초단위 (60 * 30 = 30분)
		ck.setPath("/");
		response.addCookie(ck);
	}

	// Cookie 삭제
	public static void delCookie(String cName, HttpServletResponse response) {
		Cookie ck = new Cookie(cName, null); // 쿠키 이름에 대한 값을 null로 지정
		ck.setMaxAge(0); // 유효시간을 0으로 설정
		response.addCookie(ck); // 응답 헤더에 추가해서 없어지도록 함
	}
}