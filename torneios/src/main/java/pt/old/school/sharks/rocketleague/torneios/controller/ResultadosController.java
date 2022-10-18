package pt.old.school.sharks.rocketleague.torneios.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.Jogador;
import pt.old.school.sharks.rocketleague.torneios.model.Partida;
import pt.old.school.sharks.rocketleague.torneios.model.PartidaStaging;
import pt.old.school.sharks.rocketleague.torneios.pojo.Criterios;
import pt.old.school.sharks.rocketleague.torneios.repository.EquipaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.JogadorRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaStagingRepository;

@Controller
public class ResultadosController {
	
	@Autowired
	JogadorRepository jogadorRepo;
	
	@Autowired
	EquipaRepository equipaRepo;
	
	@Autowired
	PartidaRepository partidaRepo;
	
	@Autowired
	PartidaStagingRepository partidaStagingRepo;
		
	List<String> erros = new ArrayList<String>();
	private static SimpleDateFormat dataPT=new SimpleDateFormat("dd/MM/yyyy");
		
	@GetMapping(value="/resultados")
	public String onListas(Model model) {
		List<Jogador> jogadores = jogadorRepo.findAll();
		model.addAttribute("jogadores", jogadores);
		model.addAttribute("partidas", new ArrayList<Partida>());
		model.addAttribute("criterios", new Criterios());
		return "listas";
	}
	
	@PostMapping(value="/resultados")
	public String pesquisarByEquipa(Model model, @ModelAttribute Criterios criterios) throws ParseException {
		erros = new ArrayList<String>();
		if(criterios.getJogadores() != null && criterios.getJogadores().length > 3) {
			erros.add("Não existem equipas com mais de 3 jogadores!");
		}
		
		if(!erros.isEmpty()) {
			model.addAttribute("erro", erros.get(0));
			model.addAttribute("voltar", "criarPartida");
			return "error/error";
		}
		
		
		List<Equipa> equipas = new ArrayList<Equipa>();
		if(criterios.getJogadores() != null && criterios.getJogadores().length > 0) {			
			if(criterios.getJogadores().length == 1) {
				equipas.addAll(equipaRepo.getByJogadorId(criterios.getJogadores()[0]));
			}
			else if(criterios.getJogadores().length == 2) {
				equipas.addAll(equipaRepo.getByJogadorId(criterios.getJogadores()[0], criterios.getJogadores()[1]));
			}
			else if(criterios.getJogadores().length == 3) {
				equipas.add(equipaRepo.getByJogadorId(criterios.getJogadores()[0], criterios.getJogadores()[1], criterios.getJogadores()[2]));
			}
		}
		
		List<Partida> partidas;
		Sort sort = Sort.by(Sort.Direction.DESC, "data");
		if(criterios.getData() != null && !criterios.getData().isEmpty()) {
			//procura só pela data
			if(equipas.size() < 1) {
				partidas = partidaRepo.getByDate(dataPT.parse(criterios.getData()));
			}
			//procura pela data e pela equipa
			else {
				partidas = partidaRepo.findByDataAndEquipas(dataPT.parse(criterios.getData()), equipas, sort);
			}
		}
		//procura só pela equipa
		else {
			partidas = partidaRepo.findByEquipas(equipas, sort);
		}
		
		model.addAttribute("jogadores", jogadorRepo.findAll());
		model.addAttribute("partidas", partidas);
		model.addAttribute("criterios", criterios);
		
		return "listas";
	}
	
	@GetMapping(value="/aprovarPartida")
	public String onAprovarPartida(Model model) {
		List<PartidaStaging> partidas = partidaStagingRepo.getPorAprovar();
		model.addAttribute("partidas", partidas);
		model.addAttribute("aprovadas", new ArrayList<String>());
		return "aprovarPartida";
	}
	
	@PostMapping(value="/aprovarPartida")
	@Transactional
	public String aprovarPartidas(Model model, @RequestParam  List<Integer> aprovadas) throws ParseException {
		aprovadas.get(0);
		List<PartidaStaging> partidasStaging = (List<PartidaStaging>) partidaStagingRepo.findAllById(aprovadas);
		List<Partida> novasPartidasAprovadas = partidasStaging.stream().
				map( p -> new Partida(p.getId(), p.getEquipaAzul(), p.getEquipaLaranja(), p.getGolosAzul(), p.getGolosLaranja(), p.getVencedor(), p.getData())).collect(Collectors.toList());
		novasPartidasAprovadas.get(0);
		partidaRepo.saveAll(novasPartidasAprovadas);
		for(PartidaStaging p : partidasStaging) {
			p.setAprovado(true);
		}
		partidaStagingRepo.saveAll(partidasStaging);
		return onAprovarPartida(model);
		
	}
}
