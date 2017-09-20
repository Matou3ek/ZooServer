package cz.matousek.zoo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.matousek.zoo.dao.Animal;
import cz.matousek.zoo.dao.AnimalDao;

@Service("animalService")
public class AnimalServiceImp implements AnimalService{

	@Autowired
	private AnimalDao animalDao;
	
	@Override
	public List<Animal> getAnimals() {
		return animalDao.getAnimals();
	}

	@Override
	public void update(Animal animal) {
		animalDao.update(animal);
	}
	
	@Override
	public void add(Animal animal) {
		animalDao.add(animal);
	}

	@Override
	public Animal find(int id) {
		return animalDao.find(id);
	}

	@Override
	public void remove(int id) {
		animalDao.remove(id);
	}
}
