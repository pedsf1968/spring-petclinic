package org.springframework.samples.petclinic.service.business;

import java.util.List;

/**
 * Interface for all services
 *
 * @author Paul-Emmanuel DOS SANTOS FACAO
 */
public interface BaseService<E, D> {

	/**
	 * Convert Data Transfert Object to Entity Model
	 * @param dto DTO
	 * @return Entity Model
	 */
	E dtoToEntity(D dto);

	/**
	 * Convert Entity Model to Data Transfert Object
	 * @param entity Entity Model
	 * @return DTO object
	 */
	D entityToDTO(E entity);

	/**
	 * Convert Entities Models Collection to Data Transfert Object Collection
	 * @param entities Collection of Entity Model
	 * @return Collection of DTO
	 */
	List<D> entitiesToDTOS(List<E> entities);

	/**
	 * Convert Entities Models Collection to Data Transfert Object Collection
	 * @param dtos Collection of DTO
	 * @return Collection of Entity Model
	 */
	List<E> dtosToEntities(List<D> dtos);

	/**
	 * Get DTO object from repository by his ID
	 * @param id identify object to be found
	 * @return DTO object
	 */
	D findById(int id);

	/**
	 * Get all DTO objects from repository
	 * @return all DTO objects
	 */
	List<D> findAll();

	/**
	 * Save DTO object to repository
	 * @return saved DTO object
	 */
	D save(D dto);

}
