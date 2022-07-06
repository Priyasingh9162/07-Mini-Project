package priya.in.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import priya.in.entity.CaseTriger;

public interface CtRepo extends JpaRepository<CaseTriger,Integer> {


	@Query(value="select * from case_triger where case_no=:caseNo", nativeQuery=true)
	CaseTriger getTriger(int caseNo);
}
