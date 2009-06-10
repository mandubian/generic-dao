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

package org.mandubian.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.mandubian.dao.GenericDao;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * <code>GenericDaoSpringHibernateTemplate</code> is the implementation of {@link GenericDao}
 * interface for Spring HibernateTemplate facility. It shall be used as the base
 * for all your DAOs in a project involving Spring+Hibernate.
 * <p>
 * For example, if you created your own FooDao interface as described in {@link GenericDao}, 
 * now you may create your own implementation of this DAO based on Spring+Hibernate.
 * <p>
 * Ex.:<br/>
 * <code>
 * &nbsp;&nbsp;public class FooDaoSpringHibernateTemplate<br/> 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;extends GenericDaoSpringHibernateTemplate<Foo, Long><br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;implements FooDao<br/>
 * &nbsp;&nbsp;{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public FooDaoSpringHibernateTemplate() {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super(Foo.class);<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/><br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public ret fooFunc(args) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// write your code here<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * &nbsp;&nbsp;}<br/>
 * </code>
 * 
 * @author mandubian
 * @see GenericDao
 */

public class GenericDaoSpringHibernateTemplate<T, PK extends Serializable> 
					implements GenericDao<T, PK>
{
	
	/**
	 * persistence type
	 */
	private Class<T> type;
	
	/**
	 * HibernateTemplate Spring facility
	 */
	private HibernateTemplate hibernateTemplate;	
	
	/**
	 * constructor
	 * 
	 * @param type
	 */
	public GenericDaoSpringHibernateTemplate(
			Class<T> type) {
        this.type = type;
    }
	
	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#clear()
	 */
	public void clear() {
		hibernateTemplate.clear();
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#delete(java.lang.Object)
	 */
	public void delete(T persistentObject) {
		hibernateTemplate.delete(persistentObject);
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#deleteAll()
	 */
	public void deleteAll() {
		hibernateTemplate.bulkUpdate(
				"delete from "+type.getSimpleName());
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return hibernateTemplate.find("from "+type.getName());
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#findByExample(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance) {
		return hibernateTemplate.findByExample(exampleInstance);
	}


	
	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#findById(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		return (T) hibernateTemplate.get(type, id);
	}

	@SuppressWarnings("unchecked")
	public T findByIdUnique(PK id) {
		return (T) hibernateTemplate.get(type, id);
	}
	
	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#findById(java.io.Serializable, boolean)
	 */
	@SuppressWarnings("unchecked")
	public T findById(PK id, boolean lock) {
		if(lock)
			return (T) hibernateTemplate.get(type, id, LockMode.UPGRADE);
		else return (T) hibernateTemplate.get(type, id);
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#flush()
	 */
	public void flush() {
		hibernateTemplate.flush();
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#persist(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public PK persist(T newInstance) {
		return (PK)hibernateTemplate.save(newInstance);
	}

	/* (non-Javadoc)
	 * @see org.mandubian.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(T transientObject) {
		hibernateTemplate.update(transientObject);
	}
	
	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * @return the type
	 */
	public Class<T> getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Class<T> type) {
		this.type = type;
	}
}

