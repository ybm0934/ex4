package com.sp.ex3.board.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sp.ex3.common.SearchVO;

@Repository
public class BoardDAOMybatis implements BoardDAO {

	private String namespace = "config.mybatis.mapper.oracle.board.";

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<BoardVO> selectAll(SearchVO searchVo) {
		List<BoardVO> list = sqlSession.selectList(namespace + "selectAll", searchVo);
		return list;
	}

	@Override
	public int getTotalRecord(SearchVO searchVo) {
		int cnt = sqlSession.selectOne(namespace + "getTotalRecord", searchVo);
		return cnt;
	}

	@Override
	public int insert(BoardVO boardVo) {
		int cnt = sqlSession.insert(namespace + "insert", boardVo);
		return cnt;
	}

	@Override
	public BoardVO detail(int no) {
		BoardVO boardVo = sqlSession.selectOne(namespace + "detail", no);
		return boardVo;
	}

	@Override
	public int readCount(int no) {
		int cnt = sqlSession.update(namespace + "readCount", no);
		return cnt;
	}

	@Override
	public int edit(BoardVO boardVo) {
		int cnt = sqlSession.update(namespace + "edit", boardVo);
		return cnt;
	}

	@Override
	public int delete(int no) {
		int cnt = sqlSession.delete(namespace + "delete", no);
		return cnt;
	}

	@Override
	public int downCount(int no) {
		int cnt = sqlSession.update(namespace + "downCount", no);
		return cnt;
	}

}
