package priya.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name="Case_Triger")
public class CaseTriger {

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer id;
	@Column(name="Case_No")
	private Integer caseNo;
	@Column(name="PDF")
	@Lob
	private byte[] pdf;
	@Column(name="Status")
	private String status;
	
	public CaseTriger() {
		
	}
	
}
