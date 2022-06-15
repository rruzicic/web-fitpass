package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Content;
import beans.SportsObject;
import util.PersonalConfig;

public class SportsObjectDAO {
	private List<SportsObject> sportsObjects;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\sports_objects.json";
	
	public SportsObjectDAO() {
		sportsObjects = new ArrayList<SportsObject>();
		loadSportsObjects();
	}
	
	public List<SportsObject> findAll(){
		return sportsObjects; 
	}
	
	public SportsObject findByName(String name) {
		for (SportsObject sportsObject : sportsObjects) {
			if(sportsObject.getName().equals(name)) {
				return sportsObject;
			}
		}
		return null;
	}
	
	public boolean exists(SportsObject sportsObject) {
		for(SportsObject so : sportsObjects) {
			if(so.getName().equals(sportsObject.getName())) return true;
		}
		return false;
	}
	
	public boolean newSportObject(SportsObject sportsObject) {
		if(exists(sportsObject)) return false;
		sportsObjects.add(sportsObject);
		saveSportsObjects();
		return true;
	}
	
	public boolean addContents(String sportsObjectName, Content content) {
		SportsObject sportsObject = findByName(sportsObjectName);
		for (Content objectContent : sportsObject.getContent()) {
			if(objectContent.getName().equals(content.getName())) {
				return false;
			}
		}
		List<Content> contents = sportsObject.getContent();
		contents.add(content);
		sportsObject.setContent(contents);
		saveSportsObjects();
		return true;
	}
	
	private void loadSportsObjects() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			sportsObjects = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), SportsObject[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println("greska1");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			System.out.println("greska2 s");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("greska3");
		}
	}
	
	private void saveSportsObjects() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), sportsObjects);
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println("greska 1 upis");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			System.out.println("greska 2 upis");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("greska 3 upis");
		}
	}
}
