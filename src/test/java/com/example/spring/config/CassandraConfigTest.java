package com.example.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update;
import com.example.app.junit.JunitSpringAnnotation;
import com.example.spring.logic.cassandra.model.Person;

/**
 * @author gimbyeongsu
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@JunitSpringAnnotation
public class CassandraConfigTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfigTest.class);

	@Autowired
	private CassandraTemplate cassandraTemplate;

	@Test
	public void test0_init() throws Exception {
		cassandraTemplate
				.execute("CREATE TABLE person(id text, event_time timestamp, name text, age int, primary key (id, event_time)) with CLUSTERING ORDER BY (event_time DESC)");
		cassandraTemplate.execute("CREATE INDEX ix_person_name ON person (name)");
	}

	@Test
	public void test1_cql_insert() throws Exception {
		String cql = "INSERT INTO person(id,  event_time, name,age) VALUES(?, ?, ?, ?)";

		List<Object> person1 = new ArrayList<>();
		person1.add("10000");
		DateTime dt1 = new DateTime(2016, 6, 20, 1, 1, 1);
		person1.add(dt1.toDate());
		person1.add("홍길동");
		person1.add(40);

		List<Object> person2 = new ArrayList<>();
		person2.add("10001");
		DateTime dt2 = new DateTime(2016, 6, 20, 1, 1, 1);
		person2.add(dt2.toDate());
		person2.add("이순신");
		person2.add(65);

		List<List<?>> people = new ArrayList<>();
		people.add(person1);
		people.add(person2);

		cassandraTemplate.ingest(cql, people);
	}

	@Test
	public void test2_update() throws Exception {
		Update update = QueryBuilder.update("person");
		update.setConsistencyLevel(ConsistencyLevel.ONE);
		update.with(QueryBuilder.set("age", 35));
		DateTime dt1 = new DateTime(2016, 6, 20, 1, 1, 1);
		update.where(QueryBuilder.eq("id", "10000")).and(QueryBuilder.eq("event_time", dt1.toDate()));

		cassandraTemplate.execute(update);
	}

	@Test
	public void test3_cql_select() throws Exception {
		String cqlOne = "select * from person where id = '10000'";
		Person person = cassandraTemplate.selectOne(cqlOne, Person.class);
		LOGGER.debug("{}", person);
	}

	@Test
	public void test4_delete() throws Exception {
		Delete delete = QueryBuilder.delete().from("person");
		delete.where(QueryBuilder.eq("id", "10000"));

		cassandraTemplate.execute(delete);
	}

	@Test
	public void test5_dropTable() throws Exception {
		cassandraTemplate.truncate("person");
		cassandraTemplate.execute("DROP TABLE person");
	}
}
