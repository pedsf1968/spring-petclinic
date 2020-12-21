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
package org.springframework.samples.petclinic.dto.business;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.common.CommonAttribute;
import org.springframework.samples.petclinic.dto.common.PersonDTO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * Simple Data Transfert Object representing a owner.
 *
 * @author Paul-Emmanuel DOS SANTOS FACAO
 */
@Getter
@Setter
public class OwnerDTO extends PersonDTO {

	@NotEmpty
	private String address;

	@NotEmpty
	private String city;

	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	private Set<PetDTO> pets;

	protected Set<PetDTO> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<>();
		}
		return this.pets;
	}

	public void setPetsInternal(Set<PetDTO> pets) {
		this.pets = pets;
	}

	public List<PetDTO> getPets() {
		List<PetDTO> sortedPets = new ArrayList<>(getPetsInternal());
		PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPets);
	}

	public void addPet(PetDTO pet) {

		if (pet.isNew() || this.getPets() == null || !this.getPets().contains(pet)) {
			getPetsInternal().add(pet);
		}
		pet.setOwner(this);
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return true if pet name is already in use
	 */
	public PetDTO getPet(String name) {
		return getPet(name, false);
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return true if pet name is already in use
	 */
	public PetDTO getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (PetDTO pet : getPetsInternal()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append(CommonAttribute.OWNER_ID, this.getId())
				.append(CommonAttribute.NEW, this.isNew()).append(CommonAttribute.OWNER_LAST_NAME, this.getLastName())
				.append(CommonAttribute.OWNER_FIRST_NAME, this.getFirstName())
				.append(CommonAttribute.OWNER_ADDRESS, this.address).append(CommonAttribute.OWNER_CITY, this.city)
				.append(CommonAttribute.OWNER_PHONE, this.telephone).toString();
	}

}
