package pt.old.school.sharks.rocketleague.torneios.pojo;

public class EstatisticaJogador {

	int tJogos, tJogosRL, vitorias, vRL, percentagem = 0;
	
	public EstatisticaJogador() {
		super();
	}

	public int gettJogos() {
		return tJogos;
	}

	public void settJogos(int tJogos) {
		this.tJogos = tJogos;
	}

	public int gettJogosRL() {
		return tJogosRL;
	}

	public void settJogosRL(int tJogosRL) {
		this.tJogosRL = tJogosRL;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	public int getvRL() {
		return vRL;
	}

	public void setvRL(int vRL) {
		this.vRL = vRL;
	}

	public int getPercentagem() {
		if(tJogos < 1) {
			return 100;
		}
		return (this.vitorias * 100) / this.tJogos;
	}
}
