/**
 * 
 */
package org.mandubian.sample.genericdao;

import org.mandubian.dao.hibernate.EnumUserType;

/**
 * @author mandubian
 *
 */
public class FooEnumUserType extends EnumUserType<Foo.FooEnum> {

	public FooEnumUserType() {
		super(Foo.FooEnum.class);
	}

}
