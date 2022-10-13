package pt.old.school.sharks.rocketleague.torneios.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.Jogador;
import pt.old.school.sharks.rocketleague.torneios.model.Partida;
import pt.old.school.sharks.rocketleague.torneios.repository.EquipaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.JogadorRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.SequenceGeneratorService;

@Controller
public class PartidasController {
	
	@Autowired
	JogadorRepository jogadorRepo;
	
	@Autowired
	EquipaRepository equipaRepo;
	
	@Autowired
	PartidaRepository partidaRepo;
	
	@Autowired
	SequenceGeneratorService sequenceService;
	
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Date dataPartida = new Date();
	private static SimpleDateFormat dataPT=new SimpleDateFormat("dd/MM/yyyy");  
	
	boolean pedidoSubmetidoComSucesso = false;
	List<String> erros = new ArrayList<String>();

	
	@GetMapping(value="/criarPartida")
	public String criarPartida(Model model) throws ParseException {
		List<Jogador> jogadores = jogadorRepo.findAll();
		model.addAttribute("jogadores", jogadores);
		//to do: só quero os resultados do próprio dia
		List<Partida> resultados = partidaRepo.getByDate(getDataPartida());
		model.addAttribute("resultados", resultados);
		
		Partida partida = new Partida();
		partida.setData(dataPartida);
		model.addAttribute("partida", partida);
		
		model.addAttribute("pedidoSubmetidoComSucesso", pedidoSubmetidoComSucesso);
		setPedidoSubmetidoComSucesso(false);
		return "criarPartida";
	}
	
	@PostMapping(value="/criarPartida")
	public String registarPartida(Model model,
			@ModelAttribute("data") String data,
			@RequestParam(value="equipa_azul", required=false) List<String> jogadoresAzul,
			@RequestParam(value="equipa_laranja", required=false) List<String> jogadoresLaranja, 
			@ModelAttribute(value="resultado_azul") int resultado_azul, 
			@ModelAttribute(value="resultado_laranja") int resultado_laranja) throws ParseException {
		erros = new ArrayList<String>();
		validaEquipas(jogadoresAzul, jogadoresLaranja);
		
		if(resultado_azul == resultado_laranja) {
			erros.add("jogo não pode acabar empatado");
		}
		
		if(!erros.isEmpty()) {
			model.addAttribute("erro", erros.get(0));
			model.addAttribute("voltar", "criarPartida");
			return "error/error";
		}
		
		setDataPartida(dataPT.parse(data));
		Partida p = new Partida();
		p.setData(getDataPartida());
		preencheEquipas(p, jogadoresAzul, jogadoresLaranja);
		p.setGolosAzul(resultado_azul);
		p.setGolosLaranja(resultado_laranja);
		p.setVencedor(resultado_azul > resultado_laranja ? p.getEquipaAzul().getId() : p.getEquipaLaranja().getId());
		p.setId(sequenceService.generateSequence("Auto_Increment_Trigger"));
		partidaRepo.save(p);
		
		setPedidoSubmetidoComSucesso(true);
		return criarPartida(model);
	}

	private void validaEquipas(List<String> jogadoresAzul, List<String> jogadoresLaranja) {
		if(jogadoresAzul == null || jogadoresLaranja == null|| jogadoresAzul.size() < 1 || jogadoresLaranja.size() < 1) {
			erros.add("todas as equipas têm de ter jogadores");
			return;
		}
		if(jogadoresAzul.size() > 3 || jogadoresLaranja.size() > 3) {
			erros.add("jogadores em excesso, cada equipa pode ter no máximo 3");
			
		}
		if(jogadoresAzul.size() != jogadoresLaranja.size()){
			erros.add("equipas devem ter o mesmo numero de jogadores");
		}
		for(String a : jogadoresAzul) {
			for(String l : jogadoresLaranja) {
				if(a.equals(l)) {
					erros.add("o mesmo jogador não pode pertencer às duas equipas");
					return;
				}
			}
		}
		
	}
	
	private Partida preencheEquipas(Partida p, List<String> jogadoresAzul, List<String> jogadoresLaranja) {
		List<Jogador> azuis = jogadorRepo.getByIds(jogadoresAzul);
		List<Jogador> laranjas = jogadorRepo.getByIds(jogadoresLaranja);
		
		Equipa eAzul;
		if(jogadoresAzul.size() == 1) {
			eAzul = equipaRepo.findByJ1(azuis.get(0));
		}else if(jogadoresAzul.size() == 2) {
			eAzul = equipaRepo.findByJ1AndJ2(azuis.get(0), azuis.get(1));
		}else  {
			eAzul = equipaRepo.findByJ1AndJ2AndJ3(azuis.get(0), azuis.get(1), azuis.get(2));
		}
		Equipa eLaranja;
		if(jogadoresLaranja.size() == 1) {
			eLaranja = equipaRepo.findByJ1(laranjas.get(0));
		}else if(jogadoresLaranja.size() == 2) {
			eLaranja = equipaRepo.findByJ1AndJ2(laranjas.get(0), laranjas.get(1));
		}else  {
			eLaranja = equipaRepo.findByJ1AndJ2AndJ3(laranjas.get(0), laranjas.get(1), laranjas.get(2));
		}
		
		p.setEquipaAzul(eAzul);
		p.setEquipaLaranja(eLaranja);
		return p;
	}
	
	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public boolean isPedidoSubmetidoComSucesso() {
		return pedidoSubmetidoComSucesso;
	}

	public void setPedidoSubmetidoComSucesso(boolean pedidoSubmetidoComSucesso) {
		this.pedidoSubmetidoComSucesso = pedidoSubmetidoComSucesso;
	}
}
