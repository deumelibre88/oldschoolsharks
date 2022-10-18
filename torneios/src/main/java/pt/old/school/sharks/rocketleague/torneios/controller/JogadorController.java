package pt.old.school.sharks.rocketleague.torneios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.old.school.sharks.rocketleague.torneios.model.Equipa;
import pt.old.school.sharks.rocketleague.torneios.model.Jogador;
import pt.old.school.sharks.rocketleague.torneios.model.Partida;
import pt.old.school.sharks.rocketleague.torneios.pojo.EstatisticaJogador;
import pt.old.school.sharks.rocketleague.torneios.repository.EquipaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.JogadorRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaRepository;

@Controller
public class JogadorController {
	
	@Autowired
	JogadorRepository jogadorRepo;
	
	@Autowired
	EquipaRepository equipaRepo;
	
	@Autowired
	PartidaRepository partidaRepo;

	@GetMapping(value="/jogador")
	public String index(Model model, @RequestParam(name = "id") int id) {
		Jogador jogador = jogadorRepo.getById(String.valueOf(id));		
		List<Equipa> equipasDesteJogador = equipaRepo.getByJogadorId(id);
		Sort sort = Sort.by(Sort.Direction.DESC, "data");
		List<Partida> partidas = partidaRepo.findByEquipas(equipasDesteJogador, sort);
		EstatisticaJogador estatisticaJogador = new EstatisticaJogador();
		estatisticaJogador.settJogos(partidas.size());
		estatisticaJogador.settJogosRL(partidas.size());
		List<Long> idsEquipasDesteJogador = equipasDesteJogador.stream().map(Equipa :: getId).collect(Collectors.toList());
		List<Partida> partidasVencedoras = partidaRepo.findByVencedorIn(idsEquipasDesteJogador);
		estatisticaJogador.setVitorias(partidasVencedoras.size());
		estatisticaJogador.setvRL(partidasVencedoras.size());
		
		model.addAttribute("jogador", jogador);
		model.addAttribute("ultimasPartidas", partidas.subList(0, 9));
		model.addAttribute("estatisticaJogador", estatisticaJogador);
		
		return "jogador";
	}
}
