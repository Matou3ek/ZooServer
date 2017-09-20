package cz.matousek.zoo.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cz.matousek.zoo.dao.Animal;


public interface AnimalService {

	@Transactional(readOnly = true)
	List<Animal> getAnimals();
	
	@Transactional
	void update(Animal animal);
	
	@Transactional(readOnly = true)
	Animal find(int id);
	
	@Transactional
	void add(Animal animal);
	
	@Transactional
	void remove(int id);
}
