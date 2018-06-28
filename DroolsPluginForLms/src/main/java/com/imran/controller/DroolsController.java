package com.imran.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.imran.model.BookAuthor;
import com.imran.model.DroolsFileEntity;
import com.imran.service.DroolsService;



@Controller
public class DroolsController {
	
	@Autowired
	DroolsService droolsService;
	
	@GetMapping(value="/list",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<DroolsFileEntity>> list(){
		return new ResponseEntity<List<DroolsFileEntity>>(droolsService.droolsList(), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String dashboard(ModelMap map) {
		return "index";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addBook(ModelMap map, @RequestParam("file") MultipartFile file,
			@RequestParam("title") String title){
		Map<String, Object> result = new HashMap<String, Object>();
		DroolsFileEntity droolsFileObj;
		if (title.equals("")) {
			  result.put("isError", Boolean.FALSE);
			  result.put("message","Title Must hava a value");
			  return result;
		}
		if (!file.getOriginalFilename().trim().equals("")) {
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
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String viewCreate(Model map) {
		map.addAttribute("droolsFileEntity",new DroolsFileEntity());
		return "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createPost(ModelMap map, @RequestParam("file") MultipartFile file,
			@RequestParam("title") String title) throws Exception {
		
		DroolsFileEntity droolsFileObj;
		
		if (title.equals("")) {
			map.addAttribute("message", "Require field  is Empty");
			return "redirect:/create";
		}
		if (!file.getOriginalFilename().equals("")) {
			boolean formateCheck = droolsService.checkFile(file);

			if (!formateCheck) {
				map.addAttribute("message", "Invalid file formate");
				return "redirect:/create";
			}
			String uploadedFileName = droolsService.uploadFileName(file);
			if (uploadedFileName != null) {
				droolsFileObj = new DroolsFileEntity(title, uploadedFileName);
				droolsService.createOrUpdateDrools(droolsFileObj);
			}

		} else {
			map.addAttribute("message", "File not Found");
			return "redirect:/create";
		}

		map.addAttribute("message", "File dded Successfully");
		return "redirect:/";

	}
	
	@RequestMapping(value="/edit/{id}")
	public String editView(ModelMap map, @PathVariable("id") long id){
		DroolsFileEntity droolsFileEntity =  droolsService.findByDroolstId(id);
		if(droolsFileEntity == null){
			map.addAttribute("message", "Rules not found");	
			return "redirect:/";
		}
		map.put("droolsFileEntity",droolsFileEntity);
		return "edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePost(ModelMap map, @RequestParam("file") MultipartFile file,
			@RequestParam("title") String title, @RequestParam("id") String id,  @RequestParam("drools_file") String drools_file) throws Exception {
		DroolsFileEntity droolsFileEntity;
		System.out.println("Title: "+title+" Id: "+id+" currentFile: "+drools_file);
		
		if (title.equals("")) {
			map.addAttribute("message", "Require field is Empty");
			return "redirect:/";
		}
		if (!file.getOriginalFilename().equals("")) {
			boolean formateCheck = droolsService.checkFile(file);
			if (!formateCheck) {
				map.addAttribute("message", "Invalid file formate");
				return "redirect:/";
			}
			String uploadedFileName = droolsService.uploadFileName(file);
			if (uploadedFileName != null) {
				droolsFileEntity = new DroolsFileEntity(Integer.parseInt(id), title, uploadedFileName);
				droolsService.updateDrools(droolsFileEntity);
				droolsService.removeFile(drools_file);
			    map.addAttribute("message", "Update Successfully");
			    return "redirect:/";
			}

		} else {
			droolsFileEntity = new DroolsFileEntity(Integer.parseInt(id), title, drools_file);
			droolsService.updateDrools(droolsFileEntity);
            map.addAttribute("message", "Update Successfully");
			return "redirect:/";
		}

		map.addAttribute("message", "Update Successfully");
		return "redirect:/";

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
	


