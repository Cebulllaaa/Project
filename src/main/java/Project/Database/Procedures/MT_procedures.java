package Project.Database.Procedures;

import org.springframework.data.repository.CrudRepository;

import Project.Database.Tables.Game_attribute;
import Project.Database.Tables.Move_attribute;

public interface MT_procedures extends CrudRepository<Move_attribute, Integer> {

}
