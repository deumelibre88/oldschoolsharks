package pt.old.school.sharks.rocketleague.torneios.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("jogador")
public class Jogador {
	
	@Id
	long id;
	
	String nick;

	public Jogador() {
		super();
	}
	
	public Jogador(int id, String nick) {
		super();
		this.id = id;
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
