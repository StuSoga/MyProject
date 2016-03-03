package com.zero.dao.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * Created by zero on 16/3/2.
 */
public class MySQLLocalDialect extends MySQLInnoDBDialect {

	public MySQLLocalDialect() {
		super();

		registerFunction("datediff", new StandardSQLFunction("datediff", Hibernate.INTEGER));
		registerFunction("timediff", new StandardSQLFunction("timediff", Hibernate.TIME));
		registerFunction("date_format", new StandardSQLFunction("date_format", Hibernate.STRING));

		registerFunction("curdate", new NoArgSQLFunction("curdate", Hibernate.DATE));
		registerFunction("curtime", new NoArgSQLFunction("curtime", Hibernate.TIME));
		registerFunction("current_date", new NoArgSQLFunction("current_date", Hibernate.DATE, false));
		registerFunction("current_time", new NoArgSQLFunction("current_time", Hibernate.TIME, false));
		registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", Hibernate.TIMESTAMP, false));
		registerFunction("date", new StandardSQLFunction("date", Hibernate.DATE));
		registerFunction("day", new StandardSQLFunction("day", Hibernate.INTEGER));
		registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", Hibernate.INTEGER));
		registerFunction("dayname", new StandardSQLFunction("dayname", Hibernate.STRING));
		registerFunction("dayofweek", new StandardSQLFunction("dayofweek", Hibernate.INTEGER));
		registerFunction("dayofyear", new StandardSQLFunction("dayofyear", Hibernate.INTEGER));
		registerFunction("from_days", new StandardSQLFunction("from_days", Hibernate.DATE));
		registerFunction("from_unixtime", new StandardSQLFunction("from_unixtime", Hibernate.TIMESTAMP));
		registerFunction("hour", new StandardSQLFunction("hour", Hibernate.INTEGER));
		registerFunction("last_day", new StandardSQLFunction("last_day", Hibernate.DATE));
		registerFunction("localtime", new NoArgSQLFunction("localtime", Hibernate.TIMESTAMP));
		registerFunction("localtimestamp", new NoArgSQLFunction("localtimestamp", Hibernate.TIMESTAMP));
		registerFunction("microseconds", new StandardSQLFunction("microseconds", Hibernate.INTEGER));
		registerFunction("minute", new StandardSQLFunction("minute", Hibernate.INTEGER));
		registerFunction("month", new StandardSQLFunction("month", Hibernate.INTEGER));
		registerFunction("monthname", new StandardSQLFunction("monthname", Hibernate.STRING));
		registerFunction("now", new NoArgSQLFunction("now", Hibernate.TIMESTAMP));
		registerFunction("quarter", new StandardSQLFunction("quarter", Hibernate.INTEGER));
		registerFunction("second", new StandardSQLFunction("second", Hibernate.INTEGER));
		registerFunction("sec_to_time", new StandardSQLFunction("sec_to_time", Hibernate.TIME));
		registerFunction("sysdate", new NoArgSQLFunction("sysdate", Hibernate.TIMESTAMP));
		registerFunction("time", new StandardSQLFunction("time", Hibernate.TIME));
		registerFunction("timestamp", new StandardSQLFunction("timestamp", Hibernate.TIMESTAMP));
		registerFunction("time_to_sec", new StandardSQLFunction("time_to_sec", Hibernate.INTEGER));
		registerFunction("to_days", new StandardSQLFunction("to_days", Hibernate.LONG));
		registerFunction("unix_timestamp", new StandardSQLFunction("unix_timestamp", Hibernate.LONG));
		registerFunction("utc_date", new NoArgSQLFunction("utc_date", Hibernate.STRING));
		registerFunction("utc_time", new NoArgSQLFunction("utc_time", Hibernate.STRING));
		registerFunction("utc_timestamp", new NoArgSQLFunction("utc_timestamp", Hibernate.STRING));
		registerFunction("week", new StandardSQLFunction("week", Hibernate.INTEGER));
		registerFunction("weekday", new StandardSQLFunction("weekday", Hibernate.INTEGER));
		registerFunction("weekofyear", new StandardSQLFunction("weekofyear", Hibernate.INTEGER));
		registerFunction("year", new StandardSQLFunction("year", Hibernate.INTEGER));
		registerFunction("yearweek", new StandardSQLFunction("yearweek", Hibernate.INTEGER));

		registerFunction("ifnull", new StandardSQLFunction("ifnull"));
	}
}
