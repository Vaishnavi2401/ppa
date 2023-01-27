package com.cdac.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cdac.Entity.Certificate;
import com.cdac.Entity.Template;
import com.cdac.Entity.Template_tenant;

import com.cdac.Services.CertificateService;
import com.cdac.Services.TemplateService;
import com.cdac.Services.Template_tenantService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

//import io.swagger.v3.oas.models.Paths;
@CrossOrigin("*")
@RequestMapping("/certificate")
@Validated
@RestController
public class CertificategenerationController {

	@Autowired
	private CertificateService certificateService;

	@Autowired
	private Template_tenantService template_tenantService;

	@Autowired
	private TemplateService templateService;
	@Value("${certificate.path}")
	private String certpath;
	@Value("${certificate.qr.path}")
	private String certqrpath;
	@Value("${certificate.bgimg.path}")
	private String certbgimgpath;
	@Value("${certificate.learnerurlapi}")
	private String learnerurlapi;
	@Value("${certificate.courseurlapi}")
	private String courseurlapi;
	@Value("${certificate.certificates.path}")
	private String certificatespath;
	@Value("${certificate.certificatesshow.path}")
	private String certificatespathshow;
	// RESTful API methods for Retrieval operations

	@GetMapping("/verifycertificate/{certificateid}")

	public ResponseEntity<?> verifycertificate(@PathVariable int certificateid) {
		Certificate c = certificateService.getByCertificateId(certificateid);
		if (c != null)
			return new ResponseEntity<>("Valid Certificate.\n Certificate Id" + c.getCertificateId() + "\nCreated on:"
					+ c.getCreationDate() + "\nCertificate Hash:" + c.getCertificateHash(), HttpStatus.OK);
		else
			return new ResponseEntity<>("Invalid Certificate", HttpStatus.OK);

	}

	// RESTful API method for Create operation
	@Configuration
	public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// registry.addResourceHandler("/**").addResourceLocations("classpath:/static","file:/opt/Certificate/Cert/*");
			registry.addResourceHandler("/**").addResourceLocations("classpath:/static");
			// registry.addResourceHandler("/certificates/**").addResourceLocations("file:///opt/Certificate/Cert/");
			registry.addResourceHandler("/certificates/**").addResourceLocations(certificatespathshow);
		}
	}

	@ResponseBody
	@GetMapping("/gencert/{user_id}/{course_id}/{tenant_id}")

	public String gencertificate(HttpServletResponse response, HttpServletRequest request, @PathVariable String user_id,
			@PathVariable int course_id, @PathVariable int tenant_id) throws IOException {

		Template_tenant t1 = template_tenantService.getByCourseIdAndTenantId(course_id, tenant_id);
		Certificate c = null;
		if (t1 != null)
			c = certificateService.getByUserIdAndCourseId(user_id, course_id);
		if (c != null) {

			/*
			 * response.setContentType("application/pdf");
			 * response.setHeader("Content-Disposition",
			 * "attachment; filename="+c.getCertificateId()+".pdf"); InputStream inputStream
			 * = new FileInputStream(new
			 * File(certificatespath+File.separator+c.getCertificateId()+".pdf")); int
			 * nRead; while ((nRead = inputStream.read()) != -1) {
			 * response.getWriter().write(nRead); }
			 * 
			 * return;
			 */
			// return
			// "http://"+request.getServerName()+":8080/certificates/"+c.getCertificateId()+".pdf";
			return "certificates" + File.separator + course_id + File.separator + c.getCertificateId() + ".pdf";
		}

		Certificate la = new Certificate();
		la.setUserId(user_id);
		la.setCourse_id(course_id);
		Template_tenant l = template_tenantService.getByCourseIdAndTenantId(course_id, tenant_id);
		// if(l==null) return new ResponseEntity<>("course id and tenant id combination
		// temlpate doesnot exist",HttpStatus.OK);
		if (l == null)
			return "course id  and tenant id combination temlpate does not exist";
		/*
		 * Template t = l.getTemplate(); la.setTemplate(t);
		 */

		Template t = t1.getTemplate();
		Certificate la1 = certificateService.save(la);
		Path path = Paths.get(certqrpath + File.separator + "qr" + la1.getCertificateId() + ".png");
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			// System.out.println("http://"+request.getServerName()+"/"+"verifycertificate?certificateid="+la1.getCertificateId());
			BitMatrix bitMatrix = qrCodeWriter.encode("http://" + request.getServerName() + "/"
					+ "verifycertificate?certificateid=" + la1.getCertificateId(), BarcodeFormat.QR_CODE, 64, 64);

			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

		} catch (WriterException e) {
			System.out.println("Couldn't generate QR code, WriterException..." + e);
		} catch (IOException e) {
			System.out.println("Couldn't generate QR code, IOException..." + e);
		}

		try {
			InputStream landscapeStream = null;
			if (t.getTemplateType().equals("landscape"))
				landscapeStream = getClass().getResourceAsStream("/static/reports/landscape_certificate.jrxml");
			else if (t.getTemplateType().equals("portrait"))
				landscapeStream = getClass().getResourceAsStream("/static/reports/portrait_certificate.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(landscapeStream);
			JRSaver.saveObject(jasperReport, "landscape_certificate.jasper");

			String bc = "", bc1 = "";
			bc = t.getTemplateContent();
			if (bc.contains("<name>")) {
				// String uri = "http://ngel.hyderabad.cdac.in:8084/api/learner/"+user_id;
				String uri = learnerurlapi + "um_api/learner/" + user_id;
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class);
				// JSONParser parser = new JSONParser();
				JSONObject json = new JSONObject(result);
				bc1 = bc.replace("<name>", json.getString("firstName"));
				bc = bc1;

			}

			if (bc.contains("<coursename>")) {
				// String uri =
				// "http://tmis1.hyderabad.cdac.in:8085/api/getCoursesDetailsEnrolledByUser/"+user_id+"/"+course_id;
				String uri = courseurlapi + "api/getCoursesDetailsEnrolledByUser/" + user_id + "/" + course_id;
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class);
				// JSONParser parser = new JSONParser();
				JSONObject json = new JSONObject(result);
				JSONObject json1 = json.getJSONObject("courseDetails");
				bc1 = bc.replace("<coursename>", json1.getString("courseName"));
				bc = bc1;
			}
			if (bc.contains("<startdate>")) {
				// String uri =
				// "http://tmis1.hyderabad.cdac.in:8085/api/getCoursesDetailsEnrolledByUser/"+user_id+"/"+course_id;
				String uri = courseurlapi + "api/getCoursesDetailsEnrolledByUser/" + user_id + "/" + course_id;
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class);
				// JSONParser parser = new JSONParser();
				JSONObject json = new JSONObject(result);
				bc1 = bc.replace("<startdate>",
						((json.getString("courseStartDate")).substring(8, 10)
								+ (json.getString("courseStartDate")).substring(4, 8)
								+ (json.getString("courseStartDate")).substring(0, 4)));
				bc = bc1;

			}
			if (bc.contains("<enddate>")) {
				// String uri =
				// "http://tmis1.hyderabad.cdac.in:8085/api/getCoursesDetailsEnrolledByUser/"+user_id+"/"+course_id;
				String uri = courseurlapi + "api/getCoursesDetailsEnrolledByUser/" + user_id + "/" + course_id;
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class);
				// JSONParser parser = new JSONParser();
				JSONObject json = new JSONObject(result);
				// System.out.println((json.getString("completionDate")).toString());
				if (json.isNull("completionDate")) {					
					bc1 = bc.replace("<enddate>", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
				}
				else {

					bc1 = bc.replace("<enddate>",
							((json.getString("completionDate")).substring(8, 10)
									+ (json.getString("completionDate")).substring(4, 8)
									+ (json.getString("completionDate")).substring(0, 4)));
				}
				bc = bc1;

			}
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("bodyContent", bc);
			parameters.put("bgpath", certbgimgpath + File.separator + t.getBackgroundImagePath());
			String str1 = "qr" + la1.getCertificateId() + ".png";
			parameters.put("qrcode", certqrpath + File.separator + str1);

			// JasperPrint jasperPrint =
			// JasperFillManager.fillReport(jasperReport,parameters, new
			// JREmptyDataSource());

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JRPdfExporter exporter = new JRPdfExporter();
			File folder = new File(certificatespath + File.separator + course_id);
			if (!folder.exists()) {
				folder.mkdir();
			}
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
					certificatespath + File.separator + course_id + File.separator + la1.getCertificateId() + ".pdf"));

			exporter.exportReport();

			// JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath
			// +"reportName.pdf");
		} catch (JRException e) {
			System.out.println("Couldn't export pdf..." + e);
		}

		Certificate la2 = certificateService.getByCertificateId(la1.getCertificateId());
		la2.setCertificateFilePath(certificatespath);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		la2.setCreationDate(timestamp);

		try {
			File file = new File(
					certificatespath + File.separator + course_id + File.separator + la1.getCertificateId() + ".pdf");
			// Use SHA-1 algorithm
			MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");

			// SHA-1 checksum
			String shaChecksum = getFileChecksum(shaDigest, file);
			la2.setCertificateHash(shaChecksum);
		} catch (Exception e) {
			System.out.println("Couldn't generate checksum" + e);
		}

		certificateService.save(la2);

		/*
		 * response.setContentType("application/pdf");
		 * response.setHeader("Content-Disposition",
		 * "attachment; filename="+la.getCertificateId()+".pdf"); InputStream
		 * inputStream = new FileInputStream(new
		 * File(certificatespath+File.separator+la.getCertificateId()+".pdf")); int
		 * nRead; while ((nRead = inputStream.read()) != -1) {
		 * response.getWriter().write(nRead); }
		 * 
		 * return;
		 */
		// return new ResponseEntity<>(response,HttpStatus.OK);

		// return new ResponseEntity<>(HttpStatus.OK);

		// return
		// "http://"+request.getServerName()+":8080"+"/certificates/"+la.getCertificateId()+".pdf";
		return "certificates" + File.separator + course_id + File.separator + la.getCertificateId() + ".pdf";

	}

	// RESTful API method for Update operation
	// RESTful API method for Delete operation
	// Functions
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		// Get file input stream for reading the file content
		FileInputStream fis = new FileInputStream(file);

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// Read file data and update in message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		;

		// close the stream; We don't need it now.
		fis.close();

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// return complete hash
		return sb.toString();
	}

}