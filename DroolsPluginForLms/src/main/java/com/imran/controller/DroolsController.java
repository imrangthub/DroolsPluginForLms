package com.imran.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.imran.model.DroolsFileEntity;
import com.imran.service.DroolsService;



@Controller
public class DroolsController {
	
	@Autowired
	DroolsService droolsService;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String dashboard(ModelMap map) {
		return "index";
	}
	
	@GetMapping(value="/list",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<DroolsFileEntity>> list(){
		return new ResponseEntity<List<DroolsFileEntity>>(droolsService.droolsList(), HttpStatus.OK);

	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(ModelMap map, 
			@RequestParam(value ="file",  required = false) MultipartFile file,
			@RequestParam("title") String title){
		Map<String, Object> result = new HashMap<String, Object>();
		DroolsFileEntity droolsFileObj;
		if (title.equals("")) {
			  result.put("isError", Boolean.FALSE);
			  result.put("message","Title Must hava a value");
			  return result;
		}
		if (file != null) {
			boolean formateCheck = droolsService.checkFile(file);
			if (!formateCheck) {
				  result.put("isError", Boolean.FALSE);
				  result.put("message","Invalid file formate");
				  return result;
			}
			String uploadedFileName = droolsService.uploadFileName(file);
			if (uploadedFileName != null) {
				droolsFileObj = new DroolsFileEntity(title, uploadedFileName);
				droolsService.createOrUpdateDrools(droolsFileObj);
			}

		} else {
			  result.put("isError", Boolean.FALSE);
			  result.put("message"," file not found");
			  return result;
		}
		  result.put("isError", Boolean.FALSE);
		  result.put("message","Successfully add File.");
		
		return result;
	}
	
	@GetMapping(value="/edit/{id}",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DroolsFileEntity> editView(ModelMap map, @PathVariable("id") long id){
		return new ResponseEntity<DroolsFileEntity>(droolsService.findByDroolstId(id), HttpStatus.OK);

	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(ModelMap map, 
			@RequestParam(value ="file",  required = false) MultipartFile file,
			@RequestParam("title") String title, 
			@RequestParam("id") Long id,
			@RequestParam("currentFile") String currentFile){
		
		System.out.println("title: "+title+" Id: "+id+" CurrentFjile: "+currentFile);
		
		Map<String, Object> result = new HashMap<String, Object>();
	
		if (title.equals("") && id>0) {
			  result.put("isError", Boolean.FALSE);
			  result.put("message","Title Must hava a value");
			  return result;
		}
		if (file!=null) {
			boolean formateCheck = droolsService.checkFile(file);
			if (!formateCheck) {
				  result.put("isError", Boolean.FALSE);
				  result.put("message","Invalid file formate");
				  return result;
			}
			droolsService.removeFile(currentFile);
			String uploadedFileName = droolsService.uploadFileName(file);
			if (uploadedFileName != null) {
				DroolsFileEntity droolsFileObj = new DroolsFileEntity();
				droolsFileObj.setId(id);
				droolsFileObj.setTitle(title);
				droolsFileObj.setDrools_file(uploadedFileName);				
				droolsService.updateDrools(droolsFileObj);				
			  result.put("isError", Boolean.FALSE);
			  result.put("message"," Successfully Update with File");
			  return result;
			}

		} 
		    DroolsFileEntity droolsFileObj = new DroolsFileEntity();
			droolsFileObj.setId(id);
			droolsFileObj.setTitle(title);
			droolsFileObj.setDrools_file(currentFile);				
			droolsService.updateDrools(droolsFileObj);				
	    result.put("isError", Boolean.FALSE);
	    result.put("message"," Successfully Update with out File");
	    return result;
	}
	
	@ResponseBody
	@RequestMapping("/activatedRules/{id}")
	public String activatedRules(@PathVariable Long id){
		droolsService.activatedRules(id);
		return  "Successfully Active This Rules";
	}
	
	@ResponseBody
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id){
		droolsService.removeDrools(id);
		return  "Successfully deleted";
	}

	
	
}
	


