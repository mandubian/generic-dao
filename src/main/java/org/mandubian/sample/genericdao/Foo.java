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

import java.io.Serializable;
import java.util.Date;

/**
 * <code>Foo</code> : a simple Persistent Entity
 *   
 * @author mandubian
 *
 */
public class Foo implements Serializable{
	private static final long serialVersionUID = 1596894501446969468L;
	
	private long			fooId;
	private String			fooStr;
	private Date			lastUpdated;
	public 	enum			FooEnum {
		VALUE_1,
		VALUE_2,
		VALUE_3
	};
	
	private FooEnum			fooEnum;
	
	public Foo(String fooStr, FooEnum fooEnum) {
		this.fooStr = fooStr;
		this.fooEnum = fooEnum;
	}
	
	public Foo() {
	}

	/**
	 * @return the fooId
	 */
	public long getFooId() {
		return fooId;
	}

	/**
	 * @param fooId the fooId to set
	 */
	public void setFooId(long fooId) {
		this.fooId = fooId;
	}

	/**
	 * @return the fooStr
	 */
	public String getFooStr() {
		return fooStr;
	}

	/**
	 * @param fooStr the fooStr to set
	 */
	public void setFooStr(String fooStr) {
		this.fooStr = fooStr;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public FooEnum getFooEnum() {
		return fooEnum;
	}

	public void setFooEnum(FooEnum fooEnum) {
		this.fooEnum = fooEnum;
	}
}
