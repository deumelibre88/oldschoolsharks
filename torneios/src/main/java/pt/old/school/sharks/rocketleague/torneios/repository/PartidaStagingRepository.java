package pt.old.school.sharks.rocketleague.torneios.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.PartidaStaging;

public interface PartidaStagingRepository extends MongoRepository<PartidaStaging, Integer> {

	@Query("{'data': ?0 }")
	List<PartidaStaging> getByDate(Date data);
	
	@Query("{'aprovado': false }")
	List<PartidaStaging> getPorAprovar();
//	
//	@Query("{ $or : [ {'equipaAzul': { $in: ?0 } }, {'equipaLaranja': { $in: ?0 } } ] }")
//	List<PartidaStaging> findByEquipas(List<Equipa> equipas, Sort sort);
//	
//	@Query("{ $and : [ {'data': ?0 },   { $or : [ {'equipaAzul': { $in: ?1 } }, {'equipaLaranja': { $in: ?1 } } ] } ] }")
//	List<PartidaStaging> findByDataAndEquipas(Date data, List<Equipa> equipas, Sort sort);
//	
//	List<PartidaStaging> findByOrderByDataDesc(Pageable page);
//	
//	List<PartidaStaging> findByVencedorIn(List<Long> vencedores);
}
