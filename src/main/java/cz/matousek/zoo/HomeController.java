package cz.matousek.zoo;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.HttpJspPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.matousek.zoo.dao.Animal;
import cz.matousek.zoo.service.AnimalService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private AnimalService animalService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/update/{id}",headers="Content-Type=application/json", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable int id, @RequestBody String jsonObject) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		Animal animal = parseJsonToObject(jsonObject);

		if(animal == null) {
			return new ResponseEntity<String>("Animal doesnt exist.", headers, HttpStatus.BAD_REQUEST);
		}
		animal.setId(id);
		animalService.update(animal);

		return new ResponseEntity<String>("Animal with ID:" + animal.getId() +" has been updated", headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/remove/{id}",produces="application/json", method = RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable int id) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		Animal animal = animalService.find(id);
		if(animal == null) {
			return new ResponseEntity<String>("Animal doesnt exist.", headers, HttpStatus.BAD_REQUEST);
		}
		
		animalService.remove(id);

		return new ResponseEntity<String>("Animal with ID:" + animal.getId() +" has been removed", headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/animals", produces= "application/json", method = RequestMethod.GET)
	public ResponseEntity<String> animals() {
		
		String json = parseObjectToString(animalService.getAnimals());
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/animals/{id}", produces= "application/json", method = RequestMethod.GET)
	public ResponseEntity<String> animal(@PathVariable int id) {
		
		String json = parseObjectToString(Arrays.asList(animalService.find(id)));
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add",headers="Content-Type=application/json", method = RequestMethod.POST)
	public ResponseEntity<String> add(@RequestBody String jsonObject) {

		Animal animal = parseJsonToObject(jsonObject);
		
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		
		if(animal == null) {
			return new ResponseEntity<String>("Animal doesnt exist.", headers, HttpStatus.BAD_REQUEST);
		}
		animalService.add(animal);

		return new ResponseEntity<String>("Animal has been created.", headers, HttpStatus.OK);
	}
	
	private String parseObjectToString(List<Animal> list) {
		ObjectMapper om = new ObjectMapper(); 
		String stringJson = "";
		try {
			stringJson = om.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringJson;
	}
	
	private Animal parseJsonToObject(String jsonObject) {
		ObjectMapper om = new ObjectMapper();
		Animal animal = null;
		try {
			animal = om.readValue(jsonObject, Animal.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return animal;
	}
}
