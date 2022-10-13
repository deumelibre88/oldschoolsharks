package pt.old.school.sharks.rocketleague.torneios.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pt.old.school.sharks.rocketleague.torneios.model.Criterios;
import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.Jogador;
import pt.old.school.sharks.rocketleague.torneios.model.Partida;
import pt.old.school.sharks.rocketleague.torneios.repository.EquipaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.JogadorRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaRepository;

@Controller
public class ResultadosController {
	
	@Autowired
	JogadorRepository jogadorRepo;
	
	@Autowired
	EquipaRepository equipaRepo;
	
	@Autowired
	PartidaRepository partidaRepo;
		
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
		if(criterios.getData() != null && !criterios.getData().isEmpty()) {
			//procura só pela data
			if(equipas.size() < 1) {
				partidas = partidaRepo.getByDate(dataPT.parse(criterios.getData()));
			}
			//procura pela data e pela equipa
			else {
				partidas = partidaRepo.findByDataAndEquipas(dataPT.parse(criterios.getData()), equipas);
			}
		}
		//procura só pela equipa
		else {
			partidas = partidaRepo.findByEquipas(equipas);
		}
		
		model.addAttribute("jogadores", jogadorRepo.findAll());
		model.addAttribute("partidas", partidas);
		model.addAttribute("criterios", criterios);
		
		return "listas";
	}
}
