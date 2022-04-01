package stas.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Message {
	@Id
	@GeneratedValue
	private long id;
	private String text;
}
