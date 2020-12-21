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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.dto.common.NamedDTO;

import java.time.LocalDate;
import java.util.*;

/**
 * Simple Data Transfert Object representing a pet.
 *
 * @author Paul-Emmanuel DOS SANTOS FACAO
 */
@Getter
@Setter
public class PetDTO extends NamedDTO {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	private PetTypeDTO type;

	private OwnerDTO owner;

	private Set<VisitDTO> visits = new LinkedHashSet<>();

	protected Set<VisitDTO> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}

	public void setVisitsInternal(Collection<VisitDTO> visits) {
		this.visits = new LinkedHashSet<>(visits);
	}

	public List<VisitDTO> getVisits() {
		List<VisitDTO> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	public void addVisit(VisitDTO visit) {
		getVisitsInternal().add(visit);
		visit.setPetId(this.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PetDTO))
			return false;
		if (!super.equals(o))
			return false;

		PetDTO pet = (PetDTO) o;

		if (!getBirthDate().equals(pet.getBirthDate()))
			return false;
		if (!getType().equals(pet.getType()))
			return false;
		if (getOwner() != null ? !getOwner().equals(pet.getOwner()) : pet.getOwner() != null)
			return false;
		return getVisits() != null ? getVisits().equals(pet.getVisits()) : pet.getVisits() == null;
	}

}
