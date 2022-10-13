package pt.old.school.sharks.rocketleague.torneios.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("equipa")
public class Equipa {
	@Id
	private long id;
	private Jogador j1;
	private Jogador j2;
	private Jogador j3;
	
	public Equipa() {
		super();
	}
	
	public Equipa(int id, Jogador j1, Jogador j2, Jogador j3) {
		super();
		this.id = id;
		this.j1 = j1;
		this.j2 = j2;
		this.j3 = j3;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Jogador getJ1() {
		return j1;
	}

	public void setJ1(Jogador j1) {
		this.j1 = j1;
	}

	public Jogador getJ2() {
		return j2;
	}

	public void setJ2(Jogador j2) {
		this.j2 = j2;
	}

	public Jogador getJ3() {
		return j3;
	}

	public void setJ3(Jogador j3) {
		this.j3 = j3;
	}	
}
