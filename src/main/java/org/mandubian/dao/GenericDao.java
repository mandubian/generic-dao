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

package org.mandubian.dao;

import java.io.Serializable;
import java.util.List;

/**
 * <code>GenericDao</code> is a Generic DAO Interface for JDK>1.5 used to implement 
 * specific DAOs accessing any persistent entity. It is based on Java Generics.
 * It is directly inspired by code from following links:
 * <ul>
 * <li>http://www.ibm.com/developerworks/java/library/j-genericdao.html</li>
 * <li>http://forum.springsource.org/showthread.php?t=25160</li>
 * </ul>
 * 
 * It provides all basic functions for Data access:
 * <ul>
 * <li>{@link #persist}</li>
 * <li>{@link #update}</li>
 * <li>{@link #delete}</li>
 * <li>{@link #deleteAll}</li>
 * <li>{@link #findById}</li>
 * <li>{@link #findAll}</li>
 * <li>{@link #findByExample}</li>
 * <li>{@link #flush}</li>
 * <li>{@link #clear}</li>
 * </ul>
 * <p>
 * For example, if you want to create your own FooDAO for the entity Foo with a Long 
 * primary key, you should begin creating your own FooDAO interface extending and 
 * specializing GenericDao interface.
 * <p>Ex:<br/>
 * <code>
 * &nbsp;&nbsp;public interface FooDao extends GenericDao<Foo, Long> {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;ret myFunc(args);<br/>
 * &nbsp;&nbsp;}<br/>
 * </code>
 * <p>
 * Then you can add specific Data Access Functions in FooDao that you shall
 * implements in your FooDaoImpl class as described in {@link GenericDaoSpringHibernateTemplate}.
 * 
 * @author mandubian
 *
 * @param <T>	the type of persisted entity
 * @param <PK>	the type of the primary key of the persisted entity
 */
public interface GenericDao <T, PK extends Serializable> {

	/**
     * persists the newInstance object into database
     * 
     * @param 	newInstance	new instance to persist
     * @return			Primary Key of the newly persisted instance
     */
    PK persist(T newInstance);

    /**
     * saves changes made to a persistent object.
     * 
     * @param transientObject	the object to update
     */
    void update(T transientObject);

    /**
     * removes an object from persistent storage in the database
     * 
     * @param persistentObject	the object to delete
     */
    void delete(T persistentObject);
    
    /**
     * deletes all persistent objects
     * 
     */
    void deleteAll();

    /**
     * finds a persistent object by its primary key
     * 
     * @param 	id		Primary Key of the object to find
     * @return			Found object or <code>null</code> if not found
     */
    T findById(PK id);

    
    /**
     * finds a persistent object by its primary key with lock
     * 
     * 
     * @param 	id		Primary Key of the object to find
     * @param 	lock	if lock is true, then the UPGRADE LockMode is used
     * @return			Found object or <code>null</code> if not found
     */
    T findById(PK id, boolean lock);

    /**
     * finds a persistent object by its unique primary key
     * 
     * 
     * @param 	id		Primary Key of the object to find
     * @return			Found object or <code>null</code> if not found
     */
    T findByIdUnique(PK id);
    
    /**
     * finds all persistent objects
     * 
     * @return			List of found objects
     */
    List<T> findAll();
    
    /**
     * finds persistent objects using an example instance
     * 
     * @param 	exampleInstance	the valued instance example
     * @return			List of found objects
     */
    List<T> findByExample(T exampleInstance);
    
    /**
     * flushes the persistence session
     * 
      */
    void flush();
    
    /**
     * clears the persistence session
     * 
     */
    void clear();

}