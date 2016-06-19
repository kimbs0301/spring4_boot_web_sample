package com.example.etc.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.InvalidURIException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * <pre>
 * https://github.com/xetorthio/jedis
 * </pre>
 * 
 * @author gimbyeongsu
 */
public class JedisPoolTest {
	private static HostAndPort hnp = new HostAndPort("127.0.0.1", 6379);

	@Test
	public void checkConnections() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000);
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		assertEquals("bar", jedis.get("foo"));
		jedis.close();
		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test
	public void checkCloseableConnections() throws Exception {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000);
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		assertEquals("bar", jedis.get("foo"));
		jedis.close();
		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test
	public void checkConnectionWithDefaultPort() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort());
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		assertEquals("bar", jedis.get("foo"));
		jedis.close();
		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test
	public void checkJedisIsReusedWhenReturned() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort());
		Jedis jedis = pool.getResource();
		jedis.set("foo", "0");
		jedis.close();

		jedis = pool.getResource();
		jedis.incr("foo");
		jedis.close();
		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test(expected = JedisException.class)
	public void checkPoolOverflow() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(1);
		config.setBlockWhenExhausted(false);
		JedisPool pool = new JedisPool(config, hnp.getHost(), hnp.getPort());
		Jedis jedis = pool.getResource();
		jedis.set("foo", "0");

		Jedis newJedis = pool.getResource();
		newJedis.incr("foo");
		pool.close();
	}

	@Test
	public void securePool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setTestOnBorrow(true);
		JedisPool pool = new JedisPool(config, hnp.getHost(), hnp.getPort(), 2000, null);
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		jedis.close();
		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test
	public void nonDefaultDatabase() {
		JedisPool pool0 = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000, null);
		Jedis jedis0 = pool0.getResource();
		jedis0.set("foo", "bar");
		assertEquals("bar", jedis0.get("foo"));
		jedis0.close();
		pool0.close();
		assertTrue(pool0.isClosed());

		JedisPool pool1 = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000, null, 1);
		Jedis jedis1 = pool1.getResource();
		assertNull(jedis1.get("foo"));
		jedis1.close();
		pool1.close();
		assertTrue(pool1.isClosed());
	}

	@Test(expected = InvalidURIException.class)
	public void shouldThrowInvalidURIExceptionForInvalidURI() throws URISyntaxException {
		JedisPool pool = new JedisPool(new URI("localhost:6380"));
		pool.close();
	}

	@Test
	public void selectDatabaseOnActivation() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000, null);

		Jedis jedis0 = pool.getResource();
		assertEquals(0, jedis0.getDB().intValue());

		jedis0.select(1);
		assertEquals(1, jedis0.getDB().intValue());

		jedis0.close();

		Jedis jedis1 = pool.getResource();
		assertTrue("Jedis instance was not reused", jedis1 == jedis0);
		assertEquals(0, jedis1.getDB().intValue());

		jedis1.close();
		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test
	public void customClientName() {
		JedisPool pool0 = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000, null, 0,
				"my_shiny_client_name");

		Jedis jedis = pool0.getResource();

		assertEquals("my_shiny_client_name", jedis.clientGetname());

		jedis.close();
		pool0.close();
		assertTrue(pool0.isClosed());
	}

	@Test
	public void returnResourceDestroysResourceOnException() {

		class CrashingJedis extends Jedis {
			@Override
			public void resetState() {
				throw new RuntimeException();
			}
		}

		final AtomicInteger destroyed = new AtomicInteger(0);

		class CrashingJedisPooledObjectFactory implements PooledObjectFactory<Jedis> {

			@Override
			public PooledObject<Jedis> makeObject() throws Exception {
				return new DefaultPooledObject<Jedis>(new CrashingJedis());
			}

			@Override
			public void destroyObject(PooledObject<Jedis> p) throws Exception {
				destroyed.incrementAndGet();
			}

			@Override
			public boolean validateObject(PooledObject<Jedis> p) {
				return true;
			}

			@Override
			public void activateObject(PooledObject<Jedis> p) throws Exception {
			}

			@Override
			public void passivateObject(PooledObject<Jedis> p) throws Exception {
			}
		}

		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(1);
		JedisPool pool = new JedisPool(config, hnp.getHost(), hnp.getPort(), 2000, null);
		pool.initPool(config, new CrashingJedisPooledObjectFactory());
		Jedis crashingJedis = pool.getResource();

		try {
			crashingJedis.close();
		} catch (Exception ignored) {
		}

		assertEquals(destroyed.get(), 1);
		pool.close();
	}

	@Test
	public void returnResourceShouldResetState() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(1);
		config.setBlockWhenExhausted(false);
		JedisPool pool = new JedisPool(config, hnp.getHost(), hnp.getPort(), 2000, null);

		Jedis jedis = pool.getResource();
		try {
			jedis.set("hello", "jedis");
			Transaction t = jedis.multi();
			t.set("hello", "world");
		} finally {
			jedis.close();
		}

		Jedis jedis2 = pool.getResource();
		try {
			assertTrue(jedis == jedis2);
			assertEquals("jedis", jedis2.get("hello"));
		} finally {
			jedis2.close();
		}

		pool.close();
		assertTrue(pool.isClosed());
	}

	@Test
	public void checkResourceIsCloseable() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(1);
		config.setBlockWhenExhausted(false);
		JedisPool pool = new JedisPool(config, hnp.getHost(), hnp.getPort(), 2000, null);

		Jedis jedis = pool.getResource();
		try {
			jedis.set("hello", "jedis");
		} finally {
			jedis.close();
		}

		Jedis jedis2 = pool.getResource();
		try {
			assertEquals(jedis, jedis2);
		} finally {
			jedis2.close();
		}
		pool.close();
	}

	@Test
	public void getNumActiveIsNegativeWhenPoolIsClosed() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000, null, 0,
				"my_shiny_client_name");

		pool.close();
		assertTrue(pool.getNumActive() < 0);
	}

	@Test
	public void getNumActiveReturnsTheCorrectNumber() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000);
		Jedis jedis = pool.getResource();
		jedis.set("foo", "bar");
		assertEquals("bar", jedis.get("foo"));

		assertEquals(1, pool.getNumActive());

		Jedis jedis2 = pool.getResource();
		jedis.set("foo", "bar");

		assertEquals(2, pool.getNumActive());

		jedis.close();
		assertEquals(1, pool.getNumActive());

		jedis2.close();

		assertEquals(0, pool.getNumActive());

		pool.close();
	}

	@Test
	public void testAddObject() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.getHost(), hnp.getPort(), 2000);
		pool.addObjects(1);
		assertEquals(pool.getNumIdle(), 1);
		pool.close();
	}
}