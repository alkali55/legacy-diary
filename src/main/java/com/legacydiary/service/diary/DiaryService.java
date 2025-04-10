package com.legacydiary.service.diary;

import java.util.List;

import com.legacydiary.domain.DiaryVO;

public interface DiaryService {
	
	// 글 등록
	int register(DiaryVO diaryVo);
	
	// 글 전체 목록 조회
	List<DiaryVO> viewAll();
	
	int updateFinished(int dno, boolean finished);
}
