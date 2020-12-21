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
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.business.Pet;
import org.springframework.samples.petclinic.model.business.PetType;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Pet</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Paul-Emmanuel DOS SANTOS FACAO
 */
public interface PetRepository extends Repository<Pet, Integer> {

	/**
	 * Retrieve all {@link PetType}s from the data store.
	 * @return a Collection of {@link PetType}s.
	 */
	@Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
	@Transactional(readOnly = true)
	List<PetType> findPetTypes();

	/**
	 * Retrieve a {@link Pet} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link Pet} if found
	 */
	@Query("SELECT DISTINCT pet FROM Pet pet left join fetch pet.owner WHERE pet.id =:id")
	@Transactional(readOnly = true)
	Pet findById(@Param("id") Integer id);

	/**
	 * Retrieve all {@link Pet}d from the data store by owner id.
	 * @param id the id of owner to search for
	 * @return a Collection of {@link Pet}s
	 */
	List<Pet> findByOwnerId(Integer id);

	/**
	 * Retrieve all {@link Pet}s from the data store
	 * @return a Collection of {@link Pet}s (or an empty Collection if none
	 */
	List<Pet> findAll();

	/**
	 * Save a {@link Pet} to the data store, either inserting or updating it.
	 * @param pet the {@link Pet} to save
	 */
	Pet save(Pet pet);

}
