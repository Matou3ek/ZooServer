package cz.matousek.zoo.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface AnimalDao {

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
