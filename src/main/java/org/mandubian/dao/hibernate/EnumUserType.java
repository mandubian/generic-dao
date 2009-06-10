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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * <code>EnumUserType</code> is the Hibernate Mapping Enum facility. 
 * It allows mapping a Java Enum as a string and back as an Enum in the DB 
 * using Hibernate.
 * This code is directly inspired from a "no more available" post from Hibernate forum http://hibernate.org/272.html
 * <p>
 * When you have a Persisted POJO such as:
 * <code><br/>
 * Class PersistPojo {<br/>
 * &nbsp;&nbsp;public enum FooEnum { VALUE1, VALUE2, VALUE3 };<br/>
 * &nbsp;&nbsp;private FooEnum fooEnum;<br/>
 * }<br/>
 * </code>
 * <p>
 * Just create a new Class FooEnumUserType such as:
 * <code><br/>
 * public class FooEnumUserType extends EnumUserType&lt;PersistPojo.FooEnum&gt; {<br/> 
 * &nbsp;&nbsp;public FooEnumUserType() { <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;super(PersistPojo.FooEnum.class);<br/>
 * &nbsp;&nbsp;}<br/>
 * }
 * </code>
 * <p>
 * And finally in your hibernate mapping PersistPojo.hbm.xml, map the Enum as following:
 * <code><br/>
 * &lt;class name="PersistPojo"&gt;<br/>
 * &nbsp;&nbsp;&lt;property name="fooEnum" column="FOO_ENUM" type="FooEnumUserType"/&gt;<br/>
 * &lt;/class&gt;
 * </code>
 * 
 * @author mandubian 
 *
*/
public class EnumUserType<E extends Enum<E>> implements UserType {
	private Class<E> clazz = null;

	protected EnumUserType(Class<E> c) {
		this.clazz = c;
	}

	private static final int[] SQL_TYPES = { Types.VARCHAR };

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class<E> returnedClass() {
		return clazz;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
		String name = resultSet.getString(names[0]);
		E result = null;
		if (!resultSet.wasNull()) {
			result = Enum.valueOf(clazz, name);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement preparedStatement, Object value,
			int index) throws HibernateException, SQLException {
		if (null == value) {
			preparedStatement.setNull(index, Types.VARCHAR);
		} else {
			preparedStatement.setString(index, ((Enum<E>) value).name());
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		if (null == x || null == y)
			return false;
		return x.equals(y);
	}
}
