package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.PromoCode;
import util.PersonalConfig;

public class PromoCodeDAO {
	private List<PromoCode> promoCodes;
	private String pathToFile = "webapps/FitPass/data/promo_codes.json";
	
	public PromoCodeDAO() {
		promoCodes = new ArrayList<PromoCode>();
		load();
	}
	
	public PromoCode findByCode(String code) {
		if(code == null) {
			return null;
		}
		for (PromoCode promoCode : promoCodes) {
			if(promoCode.getCode().equals(code) && !promoCode.isDeleted()) {
				return promoCode;
			}
		}
		return null;
	}
	
	public void decrementUsesLeft(PromoCode promoCode) {
		int usesLeft = promoCode.getUsesLeft();
		usesLeft--;
		promoCode.setUsesLeft(usesLeft);
		save();
	}
	
	public boolean newPromoCode(PromoCode promoCode) {
		if (exists(promoCode.getCode())) {
			return false;
		}
		promoCode.setDeleted(false);
		boolean isSaved = promoCodes.add(promoCode);
		save();
		return isSaved;
	}
	
	public List<PromoCode> findAll() {
		List<PromoCode> existingPromoCodes = new ArrayList<PromoCode>();
		for(PromoCode promoCode : promoCodes) {
			if(!promoCode.isDeleted()) {
				existingPromoCodes.add(promoCode);
			}
		}
		return existingPromoCodes;
	}
	
	public boolean delete(String promoCode) {
		if(findByCode(promoCode) != null) {
			findByCode(promoCode).setDeleted(true);
			save();
			return true;
		}
		return false;
	}
	
	private boolean exists(String code) {
		for (PromoCode promoCode : promoCodes) {
			if(promoCode.getCode().equals(code) && !promoCode.isDeleted()) {
				return true;
			}
		}
		return false;
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			promoCodes = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), PromoCode[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), promoCodes);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
