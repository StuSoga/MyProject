package com.zero.service;

import com.zero.dao.MemberDao;
import com.zero.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zero on 16/3/1.
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService{

	@Autowired
	private MemberDao memberDao;

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> index() {

		ArrayList<Map<String, Object>> result= new ArrayList<>();
		HashMap data;

		List<Member> members = this.memberDao.getAll();

		for (Member member:members){
			data=new HashMap();
			data.put("id",member.getId());
			data.put("name",member.getName());
			result.add(data);
		}

		return result;
	}
}
