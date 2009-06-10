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

package org.mandubian.sample.genericdao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.mandubian.dao.hibernate.GenericDaoSpringHibernateTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * <code>FooDaoSpringHibernateTemplate</code> is the implementation of FooDao
 * using GenericDaoSpringHibernateTemplate
 * 
 * @author mandubian
 * 
 * @see GenericDaoSpringHibernateTemplate
 *
 */
public class FooDaoSpringHibernateTemplate 
		extends GenericDaoSpringHibernateTemplate<Foo, Long> 
		implements FooDao {

	public Foo findByFooStr(final String fooStr) {
		return (Foo)getHibernateTemplate().execute(
				new HibernateCallback(){
					public Object doInHibernate(Session session) 
					throws HibernateException, SQLException 
					{
						Query query = session.createQuery(
								"from FOO as foo where foo.fooStr = ?");
						query.setString(0, fooStr);
						return query.uniqueResult();
					}		
				});	
	}

	public FooDaoSpringHibernateTemplate() {
		super(Foo.class);
	}

}
