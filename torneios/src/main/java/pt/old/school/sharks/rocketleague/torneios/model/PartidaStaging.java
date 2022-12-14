package pt.old.school.sharks.rocketleague.torneios.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document("partida_staging")
public class PartidaStaging {

	@Id
	private long id;
	
	private int jogo = 0; //0 => rocket league; 1 => Fifa 22
	private Equipa equipaAzul;
	private Equipa equipaLaranja;
	private int golosAzul;
	private int golosLaranja;
	private long vencedor;
	@DateTimeFormat(pattern = "dd/MM/YYYY")
	private Date data;
	private String criador;
	private boolean aprovado;
		
	public PartidaStaging() {
		super();
	}
	public PartidaStaging(long id, Equipa equipaAzul, Equipa equipaLaranja, int golosAzul, int golosLaranja, long vencedor,
			Date data, String criador, boolean aprovado) {
		super();
		this.id = id;
		this.equipaAzul = equipaAzul;
		this.equipaLaranja = equipaLaranja;
		this.golosAzul = golosAzul;
		this.golosLaranja = golosLaranja;
		this.vencedor = vencedor;
		this.data = data;
		this.criador = criador;
		this.aprovado = aprovado;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getJogo() {
		return jogo;
	}
	public void setJogo(int jogo) {
		this.jogo = jogo;
	}
	public Equipa getEquipaAzul() {
		return equipaAzul;
	}
	public void setEquipaAzul(Equipa equipaAzul) {
		this.equipaAzul = equipaAzul;
	}
	public Equipa getEquipaLaranja() {
		return equipaLaranja;
	}
	public void setEquipaLaranja(Equipa equipaLaranja) {
		this.equipaLaranja = equipaLaranja;
	}
	public int getGolosAzul() {
		return golosAzul;
	}
	public void setGolosAzul(int golosAzul) {
		this.golosAzul = golosAzul;
	}
	public int getGolosLaranja() {
		return golosLaranja;
	}
	public void setGolosLaranja(int golosLaranja) {
		this.golosLaranja = golosLaranja;
	}
	public long getVencedor() {
		return vencedor;
	}
	public void setVencedor(long vencedor) {
		this.vencedor = vencedor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getCriador() {
		return criador;
	}
	public void setCriador(String criador) {
		this.criador = criador;
	}
	public boolean isAprovado() {
		return aprovado;
	}
	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}
}
