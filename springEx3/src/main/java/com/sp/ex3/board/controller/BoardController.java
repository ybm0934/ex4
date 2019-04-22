package com.sp.ex3.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sp.ex3.board.model.BoardService;
import com.sp.ex3.board.model.BoardVO;
import com.sp.ex3.common.PaginationInfo;
import com.sp.ex3.common.SearchVO;
import com.sp.ex3.common.Utility;

@Controller
@RequestMapping("/board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;

	@Resource(name = "fileUploadProperties")
	Properties fileuploadProperties;

	@RequestMapping("/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model, HttpServletRequest request) {
		logger.info("글 목록, 파라미터 searchVo={}", searchVo);

		// [1] PaginationInfo 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE); // 블럭당 보여질 페이지 수
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE); // 페이지당 보여질 레코드 수
		pagingInfo.setCurrentPage(searchVo.getCurrentPage()); // 현재 페이지

		// [2] SearchVO 에 값 셋팅
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex()); // curPos 페이지당 시작 인덱스 0, 5, 10, 15 ...
		logger.info("셋팅 후 searchVo={}", searchVo);

		List<BoardVO> list = boardService.selectAll(searchVo);
		logger.info("글 목록 조회 결과 list.size={}", list.size());

		// 전체 레코드 개수 조회
		int totalRecord = boardService.getTotalRecord(searchVo);
		pagingInfo.setTotalRecord(totalRecord);
		logger.info("전체 레코드 개수={}", totalRecord);

		model.addAttribute("list", list);
		model.addAttribute("pageVo", pagingInfo);

		// 아이피 조회
		Utility.getIp(request);

		return "board/list";
	}

	@RequestMapping(value = "/write.do", method = RequestMethod.GET)
	public String write_get(HttpServletRequest request, HttpSession session) {
		logger.info("글쓰기 화면");

		String ip = Utility.getIp(request);

		session.setAttribute("ip", ip);

		return "board/write";
	}

	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	public String write_post(@ModelAttribute BoardVO boardVo, MultipartHttpServletRequest request) throws IOException {
		logger.info("글쓰기 처리, 파라미터 boardVo={}", boardVo);
		logger.info("글쓰기 처리, 파라미터 request={}", request);

		// 파일 업로드 처리
		String fileName = "";
		long fileSize = 0;

		List<Map<String, Object>> resultList = null;
		try {
			resultList = boardService.fileupload(request);
			for (int i = 0; i < resultList.size(); i++) {
				Map<String, Object> fileInfoMap = resultList.get(i);
				fileName = (String) fileInfoMap.get("fileName");
				fileSize = (Long) fileInfoMap.get("fileSize");
			} // for
			logger.info("파일 업로드 성공, fileName={}, fileSize={}", fileName, fileSize);
		} catch (Exception e) {
			logger.info("파일 업로드 실패");
			e.printStackTrace();
		}

		boardVo.setFileName(fileName);
		boardVo.setFileSize(fileSize);

		int cnt = boardService.insert(boardVo);
		logger.info("글쓰기 처리 결과 cnt={}", cnt);

		return "redirect:/board/detail.do?no=" + boardVo.getNo();
	}

	@RequestMapping("/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		logger.info("상세보기 파라미터 no={}", no);

		// 조회 수 중복 방지용 쿼리(session 이용)
		String getIp = Utility.getIp(request); // 아이피 받기
		logger.info("상세보기, 아이피 읽어오기 getIp={}", getIp);

		String id = (String) session.getAttribute(getIp + no); // 이름이 'ip+글번호'인 session 값을 id에 가져온다.
		logger.info("session 불러오기 id={}", id);

		if (id == null) { // id가 null일 경우 이름과 값이 'ip+글번호'인 session을 생성해준다.
			session.setAttribute(getIp + no, getIp + no);
			session.setMaxInactiveInterval(60 * 30); // 유효시간 30분
			int cnt = boardService.readCount(no); // 조회 수 증가
			logger.info("조회수 증가, cnt={}", cnt);
		}

		BoardVO boardVo = boardService.detail(no);
		logger.info("상세보기 결과, boardVo={}", boardVo);

		// 파일 첨부된 경우 파일정보와 다운로드 수 보여주기
		String fileName = boardVo.getFileName();
		String fileInfo = "";
		String getFileName = boardVo.getFileName();
		double getFileSize = boardVo.getFileSize() / 1024 / 1024;
		long downInfo = boardVo.getDownCount();
		if (fileName != null && !fileName.isEmpty()) {
			if (fileName.length() > 45) {
				fileInfo = getFileName.substring(0, 15) + "....."
						+ getFileName.substring(getFileName.length() - 15, getFileName.length());
			} else {
				fileInfo = getFileName;
			}

			if (getFileSize < 1) {
				fileInfo += " (" + (Math.round((boardVo.getFileSize() / 1024) * 100) / 100.0) + " KB)";
			} else if (getFileSize >= 1) {
				fileInfo += " (" + (Math.round(getFileSize * 100) / 100.0) + " MB)";
			}
		}

		logger.info("파일정보 결과 fileInfo={}", fileInfo);
		logger.info("다운횟수 결과 downInfo={}", downInfo);

		model.addAttribute("vo", boardVo);
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("downInfo", downInfo);

		return "board/detail";
	}

	@RequestMapping("/confirm.do")
	public String confirm(@RequestParam(defaultValue = "0") int no, Model model) {
		logger.info("비밀번호 확인창 파라미터 no={}", no);

		return "board/confirm";
	}

	@RequestMapping("/edit1.do")
	public String edit1(@ModelAttribute BoardVO boardVo, Model model) {
		logger.info("수정 화면 파라미터, boardVo={}", boardVo);

		String view = "";
		if (boardService.checkPwd(boardVo.getNo(), boardVo.getPassword())) {
			boardVo = boardService.detail(boardVo.getNo());
			logger.info("수정화면 불러오기 결과, boardVo={}", boardVo);
			model.addAttribute("vo", boardVo);
			view = "board/edit";
		} else {
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("url", "/board/confirm.do?no=" + boardVo.getNo());
			view = "inc/message";
		}

		return view;

	}

	@RequestMapping("/edit2.do")
	public String edit2(@ModelAttribute BoardVO boardVo, Model model) {
		logger.info("수정 처리 파라미터 boardVo={}", boardVo);

		int cnt = boardService.edit(boardVo);
		logger.info("수정 처리 결과 cnt={}", cnt);

		String msg = "", url = "/board/detail.do?no=" + boardVo.getNo();
		if (cnt > 0) {
			msg = "글 수정 성공";
		} else {
			msg = "글 수정 실패";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "inc/message";

	}

	@RequestMapping("/delete1.do")
	public String delete1(@RequestParam(defaultValue = "0") int no, Model model) {
		logger.info("비밀번호 확인창 파라미터 no={}", no);

		return "board/delete";
	}

	@RequestMapping("/delete2.do")
	public String delete2(@ModelAttribute BoardVO boardVo, Model model, HttpServletRequest request) {
		logger.info("글 삭제 처리 파라미터 boardVo={}", boardVo);

		String view = "";
		if (boardService.checkPwd(boardVo.getNo(), boardVo.getPassword())) {
			boardVo = boardService.detail(boardVo.getNo());
			// 파일 삭제
			String uploadLastPath = fileuploadProperties.getProperty("file.upload.path");
			String savePath1 = request.getSession().getServletContext().getRealPath(uploadLastPath);
			String savePath = fileuploadProperties.getProperty("file.upload.path.test");
			logger.info("savePath1 ={}, savePath ={}", savePath1, savePath);
			// 업로드된 파일이 있는 경우에만 삭제
			if (boardVo.getFileName() != null && !boardVo.getFileName().isEmpty()) {
				File myfile = new File(savePath, boardVo.getFileName());
				if (myfile.exists()) {
					boolean flag = myfile.delete();
					logger.info("파일 삭제 여부 : " + flag);
				}
			}

			int cnt = boardService.delete(boardVo.getNo());
			logger.info("글 삭제 처리 결과 cnt={}", cnt);

			model.addAttribute("msg", "글 삭제 성공");
			model.addAttribute("url", "/board/list.do");

			view = "inc/message";

		} else {
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("url", "/board/delete1.do?no=" + boardVo.getNo());

			view = "inc/message";

		}

		return view;
	}

	@RequestMapping("/download.do")
	public ModelAndView download(int no, String fileName, HttpServletRequest request) {
		// db에서 다운로드 수 증가시키고, 다운로드 창을 띄우는 뷰 페이지로 넘긴다

		// 1. 파라미터
		logger.info("파라미터 : no={}, fileName={}", no, fileName);

		// 2. db작업 - update
		int cnt = boardService.downCount(no);
		logger.info("다운로드 수 증가 결과 cnt={}", cnt);

		// 3. 결과
		Map<String, Object> map = new HashMap<String, Object>();
		String uploadLastPath = fileuploadProperties.getProperty("file.upload.path");
		String savePath1 = request.getSession().getServletContext().getRealPath(uploadLastPath);
		String savePath = fileuploadProperties.getProperty("file.upload.path.test");
		logger.info("savePath1 = {}", savePath1);
		logger.info("savePath = {}", savePath);

		File myfile = new File(savePath, fileName);
		map.put("file", myfile);

		ModelAndView mav = new ModelAndView("downloadView", map);

		return mav;
	}
}
