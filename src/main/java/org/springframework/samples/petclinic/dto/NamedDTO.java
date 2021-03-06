/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.dto;

/**
 * Simple Data Transfert Object with a name property to <code>BaseDTO</code>. Used as a
 * base class for DTOs needing these properties.
 *
 * @author Paul-Emmanuel DOS SANTOS FACAO
 */
public class NamedDTO extends BaseDTO {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof NamedDTO))
			return false;

		NamedDTO namedDTO = (NamedDTO) o;

		return getName().equals(namedDTO.getName());
	}

}
