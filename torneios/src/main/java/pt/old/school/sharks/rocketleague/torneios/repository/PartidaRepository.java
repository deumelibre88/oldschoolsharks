package pt.old.school.sharks.rocketleague.torneios.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.Partida;

public interface PartidaRepository extends MongoRepository<Partida, Integer> {

	@Query("{'data': ?0 }")
	List<Partida> getByDate(Date data);
	
	@Query("{ $or : [ {'equipaAzul': { $in: ?0 } }, {'equipaLaranja': { $in: ?0 } } ] }")
	List<Partida> findByEquipas(List<Equipa> equipas, Sort sort);
	
	@Query("{ $and : [ {'data': ?0 },   { $or : [ {'equipaAzul': { $in: ?1 } }, {'equipaLaranja': { $in: ?1 } } ] } ] }")
	List<Partida> findByDataAndEquipas(Date data, List<Equipa> equipas, Sort sort);
	
	List<Partida> findByOrderByDataDesc(Pageable page);
	
	List<Partida> findByVencedorIn(List<Long> vencedores);
}
