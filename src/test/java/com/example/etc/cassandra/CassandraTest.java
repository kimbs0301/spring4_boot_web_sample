package com.example.etc.cassandra;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.example.spring.logic.cassandra.model.Person;

/**
 * @author gimbyeongsu
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CassandraTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CassandraTest.class);

	@Test
	public void test0_createKeyspace() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect();

		session.execute("CREATE KEYSPACE test WITH REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': '1' }");

		session.close();
		cluster.close();
	}

	@Test
	public void test1_createTable() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect("test");

		session.execute("CREATE TABLE person (id text, event_time timestamp, name text, age int, PRIMARY KEY (id, event_time)) WITH CLUSTERING ORDER BY (event_time DESC)");
		session.execute("CREATE INDEX ix_person_name ON person (name)");

		session.close();
		cluster.close();
	}
	
	@Test
	public void test2_alterTable() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect("test");
		
		session.execute("ALTER TABLE person ALTER age TYPE int");
		
		session.close();
		cluster.close();
	}

	@Test
	public void test3_insert() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect("test");
		CassandraTemplate cassandraTemplate = new CassandraTemplate(session);

		cassandraTemplate.insert(new Person("1234567890", new Date(), "David", 40));

		session.close();
		cluster.close();
	}

	@Test
	public void test4_selectList() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect("test");
		CassandraTemplate cassandraTemplate = new CassandraTemplate(session);

		String cql = "SELECT * FROM person WHERE id IN ('1234567890')";
		List<Person> results = cassandraTemplate.select(cql, Person.class);
		for (Person person : results) {
			LOGGER.info("{}", person);
		}

		session.close();
		cluster.close();
	}

	@Test
	public void test5_truncateTable() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect("test");
		CassandraTemplate cassandraTemplate = new CassandraTemplate(session);

		cassandraTemplate.truncate("person");

		session.close();
		cluster.close();
	}

	@Test
	public void test6_dropKeyspace() throws Exception {
		Cluster cluster = Cluster.builder().withClusterName("Test Cluster")
				.addContactPoints(InetAddress.getByName("127.0.0.1")).build();
		Session session = cluster.connect("test");

		session.execute("DROP KEYSPACE test");

		session.close();
		cluster.close();
	}
}