package com.legacydiary.persistance;

public interface MemberDAO {
	
		// 아이디 중복검사
		 int selectDuplicateId(String tmpMemberId);
		
	
}
