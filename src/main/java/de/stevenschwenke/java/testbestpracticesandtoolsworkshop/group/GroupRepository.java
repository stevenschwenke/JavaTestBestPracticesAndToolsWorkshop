package de.stevenschwenke.java.testbestpracticesandtoolsworkshop.group;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Long> {

    List<Group> findAllByOrderByNameAsc();
}
