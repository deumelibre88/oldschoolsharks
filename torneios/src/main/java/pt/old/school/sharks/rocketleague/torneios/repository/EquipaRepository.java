package pt.old.school.sharks.rocketleague.torneios.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.Jogador;

public interface EquipaRepository extends MongoRepository<Equipa, Integer>{

	Equipa findByJ1(Jogador j1);
	Equipa findByJ1AndJ2(Jogador j1, Jogador j2);
	Equipa findByJ1AndJ2AndJ3(Jogador j1, Jogador j2, Jogador j3);
	
	@Query("{ $or : [ {'j1._id':?0}, {'j2._id':?0}, {'j3._id':?0} ]}")
	List<Equipa> getByJogadorId(int id);
	
	@Query("{ $and : [  { $or : [ {'j1._id':?0}, {'j2._id':?0}, {'j3._id':?0} ]} , { $or : [ {'j1._id':?1}, {'j2._id':?1}, {'j3._id':?1} ]} ]}")
	List<Equipa> getByJogadorId(int id1, int id2);
	
	@Query("{ $and : [  { $or : [ {'j1._id':?0}, {'j2._id':?0}, {'j3._id':?0} ]} , { $or : [ {'j1._id':?1}, {'j2._id':?1}, {'j3._id':?1} ]}, { $or : [ {'j1._id':?2}, {'j2._id':?2}, {'j3._id':?2} ]} ]}")
	Equipa getByJogadorId(int id1, int id2, int id3);
}
