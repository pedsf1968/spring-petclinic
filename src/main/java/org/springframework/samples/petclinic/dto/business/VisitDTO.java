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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.dto.common.BaseDTO;
import org.springframework.samples.petclinic.model.business.Visit;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * Simple Data Transfert Object representing a visit.
 *
 * @author Paul-Emmanuel DOS SANTOS FACAO
 */
@Getter
@Setter
public class VisitDTO extends BaseDTO {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotEmpty
	private String description;

	private Integer petId;

	/**
	 * Creates a new instance of Visit for the current date
	 */
	public VisitDTO() {
		this.date = LocalDate.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof VisitDTO))
			return false;

		VisitDTO visit = (VisitDTO) o;

		if (!getDate().equals(visit.getDate()))
			return false;
		if (!getDescription().equals(visit.getDescription()))
			return false;
		return getPetId().equals(visit.getPetId());
	}

}
