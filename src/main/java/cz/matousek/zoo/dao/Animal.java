package cz.matousek.zoo.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jackson.AnimalDeserializer;

/**
 * Entity class of animal.
 */

@Entity
@Table(name = "animal")
@JsonDeserialize(using = AnimalDeserializer.class)
public class Animal implements Serializable {
	
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2739622030641073946L;
	
	/**
	 * ID.
	 */
	@JsonIgnore
	private int id;

	/**
	 * Product name.
	 */
	private String name;

	@JsonCreator
    public Animal() {
    }
	
	@JsonCreator
    public Animal(int id, String name) {
		this.id = id;
        this.name = name;
    }
	
	/**
	 * Gets ID.
	 * @return id of animal
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonGetter("id")
	public int getId() {
		return id;
	}

	/**
	 * Sets ID.
	 * @param id to set
	 */
	@JsonSetter("id")
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets name.
	 * @return name of animal
	 */
	@Column(name = "name", nullable = false)
	@JsonGetter("name")
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 * @param name to set
	 */
	@JsonSetter("name")
	public void setName(String name) {
		this.name = name;
	}
}
