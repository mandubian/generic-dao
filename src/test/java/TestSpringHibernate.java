/*
 * Copyright © 2009 mandubian. All Rights Reserved.

 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 * 3. The name of the author may not be used to endorse or promote products 
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY MANDUBIAN "AS IS" AND ANY EXPRESS OR IMPLIED 
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mandubian.sample.genericdao.Foo;
import org.mandubian.sample.genericdao.FooDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * <code>TestSpringHibernate</code> : a simple Generic Dao Test Class using 
 * Spring/JUnit4 annotations 
 * 
 * @author mandubian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/dao-test.xml", "classpath*:/dao-spring-hibernate.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager="testTxManager", defaultRollback=false)
public final class TestSpringHibernate 
	extends AbstractTransactionalJUnit4SpringContextTests 
{

    @Autowired    
    protected FooDao fooDao;
    
    @Test
    public void testFooProv() throws Exception {
       	Foo foo = new Foo("coucou", Foo.FooEnum.VALUE_1);
    	
    	fooDao.persist(foo);    	
    	
    	Foo fooCheck = (Foo) this.simpleJdbcTemplate.queryForObject(
    		    "select foo_id, foo_str, last_updated, foo_enum from Foo where foo_id = ?",
    		    new ParameterizedRowMapper<Foo>() {
    		          public Foo mapRow(ResultSet resultSet, int row) 
    		          	throws SQLException 
    		          {
    		            Foo foo = new Foo();
    		            foo.setFooId(resultSet.getLong("foo_id"));
    		            foo.setFooStr(resultSet.getString("foo_str"));
    		            foo.setLastUpdated(resultSet.getTimestamp("last_updated"));
    		            foo.setFooEnum(Foo.FooEnum.valueOf(resultSet.getString("foo_enum")));
    		            return foo;
    		          }
    		    },
    		    foo.getFooId()
    		    );
    	
    	Assert.assertEquals(foo.getFooId(), fooCheck.getFooId());
    	Assert.assertEquals(foo.getFooStr(), fooCheck.getFooStr());
    	Assert.assertNotNull(foo.getLastUpdated());
    	Assert.assertNotNull(fooCheck.getLastUpdated());
    	Assert.assertEquals(foo.getLastUpdated(), fooCheck.getLastUpdated());
    	Assert.assertEquals(foo.getFooEnum(), fooCheck.getFooEnum());

    }
}