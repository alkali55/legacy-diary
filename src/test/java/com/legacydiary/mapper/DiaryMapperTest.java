package com.legacydiary.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.legacydiary.domain.DiaryVO;
import com.legacydiary.persistance.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
@Slf4j
public class DiaryMapperTest {
	
	@Autowired
	private DiaryMapper diaryMapper;
	
	@Autowired
	private MemberDAO memberDao;
	
//	@Test
//	public void selectNowTest() {
//		
//		log.info(diaryMapper.selectNow());
//		
//	}
	
	@Test
	public void selectDiaryDueTomorrowTest() {
		// 내일 마감인 글 조회
		List<DiaryVO> list = diaryMapper.selectDiaryDueTomorrow();
		
		log.info("list : {}", list);
		
		// key : writer
		// value : 내일 마감인 글들
		Map<String, List<DiaryVO>> memberDiaryMap = new HashMap<String, List<DiaryVO>>();
		
		for (DiaryVO vo : list) {
			
			if(!memberDiaryMap.containsKey(vo.getWriter())) {
				memberDiaryMap.put(vo.getWriter(), new ArrayList<DiaryVO>());
			}
			
			memberDiaryMap.get(vo.getWriter()).add(vo);
		}
		
		for (Map.Entry<String, List<DiaryVO>> entry : memberDiaryMap.entrySet()) {
			
			String memberId = entry.getKey();
			log.info("writer : {} ", memberId);
			log.info("list : {} ", entry.getValue());
			
			String email = memberDao.selectEmailByMemberId(memberId);
			log.info("email : {}", email);
			
			// 메일 본문
			StringBuilder sb = new StringBuilder();
			
			sb.append("안녕하세요. 내일까지 해야할 일이 있습니다.");
			
			for(DiaryVO vo : entry.getValue()) {
				sb.append("---").append(vo.getTitle());
			}
			
			sb.append(memberId + "님, 꼭 완료하세요!!!");
			
			log.info("내용 : {} ", sb.toString());
		}
	}
}
