package pt.old.school.sharks.rocketleague.torneios.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.old.school.sharks.rocketleague.torneios.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {
	@Query("{name:'?0'}")
	Employee findItemByName(String name);
    
    @Query(value="{dept:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<Employee> findAll(String dept);
    
    public long count();
}
