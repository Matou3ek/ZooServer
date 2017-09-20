package cz.matousek.zoo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.matousek.zoo.dao.Animal;

@Repository("animalDao")
public class AnimalDaoImp extends AbstractDao implements AnimalDao{

	@SuppressWarnings("unchecked")
	public List<Animal> getAnimals() {
		Criteria criteria = getSession().createCriteria(Animal.class);
		return criteria.list();
	}
	

	@Override
	public void update(Animal animal) {
		Animal a = (Animal) getSession().createCriteria(Animal.class).add(Restrictions.idEq(animal.getId())).uniqueResult();
		a.setName(animal.getName());
		getSession().save(a);
	}

	@Override
	public void add(Animal animal) {
		getSession().save(animal);
	}

	@Override
	public Animal find(int id) {
		return (Animal) getSession().createCriteria(Animal.class).add(Restrictions.idEq(id)).uniqueResult();
	}
	
	@Transactional
	public void remove(int id) {
		Animal animal = (Animal) getSession().createCriteria(Animal.class).add(Restrictions.idEq(id)).uniqueResult();
		getSession().delete(animal);;
	}
}
