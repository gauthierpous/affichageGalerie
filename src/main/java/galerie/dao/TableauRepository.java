package galerie.dao;

import galerie.entity.Tableau;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring

public interface TableauRepository extends JpaRepository<Tableau, Integer> {

}