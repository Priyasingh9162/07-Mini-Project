package priya.in.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import priya.in.entity.CaseDetail;

public interface CaseRepo extends JpaRepository<CaseDetail,Integer>{

	@Query(value="select * from case_details where case_no=:caseNo", nativeQuery=true)
	CaseDetail getDetail(int caseNo);
	
	

}
