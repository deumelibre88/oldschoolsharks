package pt.old.school.sharks.rocketleague.torneios.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.old.school.sharks.rocketleague.torneios.model.Jogador;

public interface JogadorRepository extends MongoRepository<Jogador, Integer> {

	@Query("{'_id': ?0 }")
	Jogador getById(String ids);
	
	@Query("{'_id': { $in: ?0 }}")
	List<Jogador> getByIds(List<String> ids);
}
