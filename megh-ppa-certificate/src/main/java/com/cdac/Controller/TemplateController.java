package com.cdac.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.Entity.Template;

import com.cdac.Entity.Template_tenant;

import com.cdac.Services.TemplateService;
import com.cdac.Services.Template_tenantService;

//import io.swagger.v3.oas.models.Paths;
@CrossOrigin("*")
@Validated
@RestController
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private Template_tenantService template_tenantService;

	@GetMapping("/upload")
	public String submit1() {
		return " <form method=\"POST\" action=\"/solutionsubmit\" enctype=\"multipart/form-data\">\n"
				+ " <input type=\"hidden\" value=\"1\" name=\"assignment_id\" />\n"
				+ "  <input type=\"hidden\" value=\"1\" name=\"submitted_by\" />\n"

				+ "\n" + "    <table>\n" + "        <tr>\n" + "            <td>Select a file to upload</td>\n"
				+ "            <td><input type=\"file\" name=\"files\" /></td>\n" + "        </tr>\n" + "        <tr>\n"
				+ "            <td>Select a file to upload</td>\n"
				+ "            <td><input type=\"file\" name=\"files\" /></td>\n" + "        </tr>\n" + "        <tr>\n"
				+ "            <td>Select a file to upload</td>\n"
				+ "            <td><input type=\"file\" name=\"files\" /></td>\n" + "        </tr>\n" + "        <tr>\n"
				+ "            <td><input type=\"submit\" value=\"Submit\" /></td>\n" + "        </tr>\n"
				+ "    </table>\n" + "</form>";
	}

	// RESTful API methods for Retrieval operations

	@GetMapping("/designer/{course_id}/{tenant_id}")
	public Template designeraccess(@PathVariable int course_id, @PathVariable int tenant_id) {
		Template_tenant l = template_tenantService.getByCourseIdAndTenantId(course_id, tenant_id);

		return l.getTemplate();

	}

	// RESTful API method for Create operation

	@PostMapping("/template/{title}/{content}/{temptype}/{file}/{description}/{qrreq}/{course_id}/{tenant_id}")
	public ResponseEntity<?> templateadd(@PathVariable String title, @PathVariable String content,
			@PathVariable String temptype, @PathVariable MultipartFile file, @PathVariable String description,
			@PathVariable int course_id, @PathVariable int tenant_id, @PathVariable Boolean qrreq) {

		Template la = new Template();

		la.setTemplateTitle(title);
		la.setTemplateContent(content);

		la.setTemplateType(temptype);
		la.setDescription(description);
		Template_tenant lat = new Template_tenant();
		lat.setTenantId(tenant_id);
		lat.setCourseId(course_id);

		Template la1 = templateService.save(la);
		template_tenantService.save(lat);

		try {
			Path root = Paths.get("/opt/Template");
			Files.copy(file.getInputStream(),
					root.resolve(la1.getTemplateId() + "." + file.getOriginalFilename().split("\\.")[1]));

			la1.setBackgroundImagePath(la1.getTemplateId() + "." + file.getOriginalFilename().split("\\.")[1]);
			templateService.save(la1);

		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!" + e.getMessage());
		}

		catch (Exception e1) {
			throw new RuntimeException("Could not store the file. Error: " + e1.getMessage());
		}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	// RESTful API method for Update operation
	@PostMapping("/template/{id}/{title}/{content}/{temptype}/{file}/{description}/{course_id}/{tenant_id}")
	public ResponseEntity<?> templateupdate(@PathVariable int id, @PathVariable String title,
			@PathVariable String content, @PathVariable String temptype, @PathVariable MultipartFile file,
			@PathVariable String description, @PathVariable int course_id, @PathVariable int tenant_id) {

		Template la = templateService.getByTemplateId(id);

		if (!la.equals(null)) {
			la.setTemplateTitle(title);
			la.setTemplateContent(content);

			la.setTemplateType(temptype);
			la.setDescription(description);
			Template_tenant lat = new Template_tenant();
			lat.setTenantId(tenant_id);
			lat.setCourseId(course_id);

			Template la1 = templateService.save(la);
			template_tenantService.save(lat);

			if (!file.equals(null)) {

				try {
					Path root = Paths.get("/opt/Template");
					Files.copy(file.getInputStream(),
							root.resolve(la1.getTemplateId() + "." + file.getOriginalFilename().split("\\.")[1]));

					la1.setBackgroundImagePath(la1.getTemplateId() + "." + file.getOriginalFilename().split("\\.")[1]);
					templateService.save(la1);

				} catch (IOException e) {
					throw new RuntimeException("Could not initialize folder for upload!" + e.getMessage());
				}

				catch (Exception e1) {
					throw new RuntimeException("Could not store the file. Error: " + e1.getMessage());
				}

			}

			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>("template id doesnot exist", HttpStatus.OK);
	}

	// RESTful API method for Delete operation
	@PostMapping("/templatedelete/{id}")
	public ResponseEntity<?> deletetemplate(@PathVariable int id) {

		templateService.delete(id);

		return new ResponseEntity<>(HttpStatus.OK);

	}

}