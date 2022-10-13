package pt.old.school.sharks.rocketleague.torneios.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Criterios {

	private String data;
	
	private int[] jogadores;
	
	private Jogador J1;
	private Jogador J2;
	private Jogador J3;
	
	public Criterios() {
		super();
	}
	
	public Criterios(String data, Jogador j1, Jogador j2, Jogador j3) {
		super();
		this.data = data;
		J1 = j1;
		J2 = j2;
		J3 = j3;
	}

	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	public int[] getJogadores() {
		return jogadores;
	}

	public void setJogadores(int[] jogadores) {
		this.jogadores = jogadores;
	}

	public Jogador getJ1() {
		return J1;
	}

	public void setJ1(Jogador j1) {
		J1 = j1;
	}

	public Jogador getJ2() {
		return J2;
	}

	public void setJ2(Jogador j2) {
		J2 = j2;
	}

	public Jogador getJ3() {
		return J3;
	}

	public void setJ3(Jogador j3) {
		J3 = j3;
	}
}
