package Project.Database.Procedures;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import Project.Database.Tables.Game_attribute;

public interface GT_procedures extends CrudRepository<Game_attribute, Integer>{
	Game_attribute findById(int x);
	
}
