package org.springframework.samples.petclinic.service.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.dto.common.PrivilegeDTO;
import org.springframework.samples.petclinic.dto.common.RoleDTO;
import org.springframework.samples.petclinic.dto.common.UserDTO;
import org.springframework.samples.petclinic.model.common.Privilege;
import org.springframework.samples.petclinic.model.common.Role;
import org.springframework.samples.petclinic.model.common.User;
import org.springframework.samples.petclinic.repository.RoleRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
class RoleServiceIntegrationTest {

	private final static Integer USER_ID = 2;

	private final static Integer ROLE_ID = 4;

	private final static Integer PRIVILEGE_ID = 3;

	private final static String ROLE_NAME = "ROLE_TEST";

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PrivilegeService privilegeService;

	private RoleService roleService;

	private Role role;

	private RoleDTO roleDTO;

	private Privilege privilege;

	private PrivilegeDTO privilegeDTO;

	@BeforeEach
	void beforeEach() {
		roleService = new RoleService(roleRepository);

		UserDTO userDTO = userService.findById(USER_ID);
		User user = userService.dtoToEntity(userDTO);
		privilegeDTO = privilegeService.findById(PRIVILEGE_ID);
		privilege = privilegeService.dtoToEntity(privilegeDTO);

		role = new Role();
		role.setId(ROLE_ID);
		role.setName(ROLE_NAME);
		role.setUsers(Collections.singleton(user));
		role.setPrivileges(Collections.singleton(privilege));
		user.setRoles(Collections.singleton(role));

		roleDTO = new RoleDTO();
		roleDTO.setId(ROLE_ID);
		roleDTO.setName(ROLE_NAME);
		roleDTO.setUsers(Collections.singleton(userDTO));
		roleDTO.setPrivileges(Collections.singleton(privilegeDTO));
		userDTO.setRoles(Collections.singleton(roleDTO));
	}

	@Test
	@Tag("findById")
	@DisplayName("Verify that we get RoleDTO by his ID")
	void findById() {
		List<RoleDTO> allDTO = roleService.findAll();
		RoleDTO expected = allDTO.get(2);

		RoleDTO found = roleService.findById(expected.getId());

		assertThat(found).isEqualToIgnoringGivenFields(expected, "users", "privileges");
		assertThat(found.getUsers()).usingElementComparatorIgnoringFields("roles")
				.contains(expected.getUsers().toArray(new UserDTO[0]));
		assertThat(found.getPrivileges()).usingElementComparatorIgnoringFields("roles")
				.contains(expected.getPrivileges().toArray(new PrivilegeDTO[0]));
	}

	@Test
	@Tag("findAll")
	@DisplayName("Verify that the RoleDTO list contain all previous elements and the new saved one")
	void findAll() {
		List<RoleDTO> expected = roleService.findAll();
		roleDTO.setUsers(new HashSet<>());

		assertThat(expected).doesNotContain(roleDTO);

		RoleDTO saved = roleService.save(roleDTO);
		expected.add(saved);
		List<RoleDTO> found = roleService.findAll();

		assertThat(found).usingElementComparatorOnFields("id", "name").containsOnlyOnceElementsOf(expected);

		roleService.delete(saved);
	}

	@Test
	@Tag("findByName")
	@DisplayName("Verify that we get RoleDTO by his Name")
	void findByName() {
		RoleDTO expected = roleService.findById(1);

		RoleDTO found = roleService.findByName(expected.getName());

		assertThat(found).isEqualToIgnoringGivenFields(expected, "users", "privileges");
		assertThat(found.getUsers()).usingElementComparatorIgnoringFields("roles")
				.contains(expected.getUsers().toArray(new UserDTO[0]));
		assertThat(found.getPrivileges()).usingElementComparatorIgnoringFields("roles")
				.contains(expected.getPrivileges().toArray(new PrivilegeDTO[0]));

	}

	@Test
	@Tag("save")
	@DisplayName("Verify that all RoleDTO list contain the new saved one")
	void save() {
		Collection<RoleDTO> expected = roleService.findAll();
		assertThat(expected).isNotEmpty().doesNotContain(roleDTO);

		roleDTO.setUsers(new HashSet<>());
		RoleDTO saved = roleService.save(roleDTO);

		assertThat(saved).isEqualToIgnoringGivenFields(roleDTO, "id", "users", "privileges");
		assertThat(saved.getUsers()).usingElementComparatorIgnoringFields("roles")
				.contains(roleDTO.getUsers().toArray(new UserDTO[0]));
		assertThat(saved.getPrivileges()).usingElementComparatorIgnoringFields("roles")
				.contains(roleDTO.getPrivileges().toArray(new PrivilegeDTO[0]));

		roleService.delete(saved);
	}

}
