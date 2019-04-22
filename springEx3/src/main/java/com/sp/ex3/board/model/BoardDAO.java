package com.sp.ex3.board.model;

import java.util.List;

import com.sp.ex3.common.SearchVO;

public interface BoardDAO {
	
	public List<BoardVO> selectAll(SearchVO searchVo);
	public int getTotalRecord(SearchVO searchVo);
	public int insert(BoardVO boardVo);
	public BoardVO detail(int no);
	public int readCount(int no);
	public int edit(BoardVO boardVo);
	public int delete(int no);
	public int downCount(int no);
	
}
