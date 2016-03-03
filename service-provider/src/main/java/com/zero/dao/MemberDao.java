package com.zero.dao;

import com.zero.dao.hibernate.HibernateDao;
import com.zero.entity.Member;
import org.springframework.stereotype.Repository;

/**
 * Created by zero on 16/3/1.
 */
@Repository
public class MemberDao extends HibernateDao<Member,Long> {
}
