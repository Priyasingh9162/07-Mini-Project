package priya.in.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name="Case_Details")
public class CaseDetail {
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	@Column(name="Case_No")
	private Integer caseNo;
	@Column(name="Holder_Name")
	private String holderName;
	@Column(name="Holder_SSN")
	private String holderSSN;
	@Column(name="Benifit_AMT")
	private Long benifitAmt;
	@Column(name="Denaied_Reason")
	private String denaiedReason;
	@Column(name="Start_Date")
	private LocalDate startDate;
	@Column(name="End_Date")
	private LocalDate endDate;
	@Column(name="Plan_Name")
	private String planName;
	@Column(name="Plan_Status")
	private String planStatus;
	
	public CaseDetail() {
		
	}
	
}
