package priya.in.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Example;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import priya.in.entity.CaseDetail;
import priya.in.entity.CaseTriger;
import priya.in.repo.CaseRepo;
import priya.in.repo.CtRepo;

@Service
public class CoServiceImpl implements CoService {

	@Autowired
	private CaseRepo cr;

	@Autowired
	private CtRepo ct;

	@Autowired(required = true)
	private JavaMailSender mailsender;

	@Override
	public void processRecord() throws MessagingException, Exception {
		CaseTriger temp = new CaseTriger();
		temp.setStatus("Pending");
		List<CaseTriger> all = ct.findAll(Example.of(temp));
		ExecutorService exservice= Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Object> pool= new ExecutorCompletionService<>(exservice);
		
		for (CaseTriger c : all) {
			pool.submit(new Callable<Object>() {
				public Object call() throws MessagingException, Exception {
					testPool(c);
					return null;
				}

			});
			

		}
	}

	private void pdfGenrator(CaseDetail detail, File file) {
		System.out.println("pdfff");
		Document doc = new Document();
		try {
			FileOutputStream fos = new FileOutputStream(file);

			PdfWriter writer = PdfWriter.getInstance(doc, fos);
			System.out.println("PDF created.");
			doc.open();
			doc.add(new Paragraph("This is a notice."));
			PdfPTable table = new PdfPTable(10);
			table.setWidthPercentage(100f);
			table.setWidths(new float[] { 1.5f, 1.5f, 2.0f, 1.5f, 1.5f, 2.0f, 1.5f, 1.5f, 2.0f, 1.5f });
			table.setSpacingBefore(10);
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font1.setColor(20, 20, 20);
			cell.setPhrase(new Phrase("ID", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase(" Hoder Name", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Case No", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Benifit AMT", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Denaied Reason", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Start Date", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("End Date", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("SSN", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Plan Name", font1));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Plan Status", font1));
			table.addCell(cell);
			table.addCell(String.valueOf(detail.getId()) + "");
			table.addCell(String.valueOf(detail.getHolderName()));
			table.addCell(String.valueOf(detail.getCaseNo()));
			table.addCell(String.valueOf(detail.getBenifitAmt()));
			table.addCell(detail.getDenaiedReason());
			table.addCell(String.valueOf(detail.getStartDate()));
			table.addCell(String.valueOf(detail.getEndDate()));
			table.addCell(detail.getHolderSSN());
			table.addCell(detail.getPlanName());
			table.addCell(detail.getPlanStatus());
			doc.add(table);
			doc.close();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendEmailWithAttachment(String toemail, String body, String Sub, String attachment)
			throws MessagingException, Exception {
		MimeMessage message = mailsender.createMimeMessage();
		MimeMessageHelper mime = new MimeMessageHelper(message, true);
		mime.setFrom("priyakumarishalu@gmail.com");
		mime.setTo(toemail);
		mime.setText(body);
		mime.setSubject(Sub);
		FileSystemResource fl = new FileSystemResource(new File(attachment));
		mime.addAttachment(fl.getFilename(), fl);
		mailsender.send(message);
		System.out.println("mail sent with attachment");
	}
	
	public void testPool(CaseTriger c) throws MessagingException, Exception {
		CaseDetail detail = cr.getDetail(c.getCaseNo());
		String path = "C:\\Users\\LENOVO\\Desktop\\pdf\\" + detail.getCaseNo() + ".pdf";
		File file = new File(path);
		this.pdfGenrator(detail, file);
		this.sendEmailWithAttachment("priyakumarishalu25@gmail.com", "","Regarding" + detail.getPlanName() + "plan aproval", path);
		c.setPdf(path.getBytes());
		c.setStatus("Completed");
		CaseTriger save = ct.save(c);
		if (save != null)
			System.out.println("Completed");
	}
}
