package com.sp.ex3.board.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sp.ex3.common.SearchVO;

public interface BoardService {

	public List<BoardVO> selectAll(SearchVO searchVo);
	public int getTotalRecord(SearchVO searchVo);
	public int insert(BoardVO boardVo);
	public BoardVO detail(int no);
	public int readCount(int no);
	public boolean checkPwd(int no, String pwd); 
	public int edit(BoardVO boardVo);
	public int delete(int no);
	public List<Map<String, Object>> fileupload(HttpServletRequest request) throws Exception;
	public int downCount(int no);
}
