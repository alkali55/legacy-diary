package com.legacydiary.service.diary;

import java.util.List;

import org.springframework.stereotype.Service;

import com.legacydiary.domain.DiaryVO;
import com.legacydiary.domain.SearchDTO;
import com.legacydiary.mapper.DiaryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
	
	private final DiaryMapper diarymapper;
	
	@Override
	public int register(DiaryVO diaryVo) {
		return diarymapper.insert(diaryVo);
	}

	@Override
	public List<DiaryVO> viewAll() {
		
		return diarymapper.selectAllList();
	}

	@Override
	public int updateFinished(int dno, boolean finished) {
		return diarymapper.updateFinished(dno, finished);
	}

	@Override
	public void modify(DiaryVO diaryVO) {
		
		diarymapper.updateDiary(diaryVO);
	}

	@Override
	public List<DiaryVO> viewAll(String memberId) {
		
		
		return diarymapper.selectAllListById(memberId);
	}

	@Override
	public List<DiaryVO> searchDiary(SearchDTO searchDTO) {
		return diarymapper.selectSearchList(searchDTO);
	}

}
