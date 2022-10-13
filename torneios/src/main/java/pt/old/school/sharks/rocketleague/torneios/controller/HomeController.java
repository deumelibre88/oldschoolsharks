package pt.old.school.sharks.rocketleague.torneios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.old.school.sharks.rocketleague.torneios.model.Partida;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaRepository;

@Controller
public class HomeController {

	@Autowired
	PartidaRepository partidaRepo;
	
	@RequestMapping(value="/")
	public String index(Model model) {
		List<Partida> partidas = partidaRepo.findByOrderByDataDesc(PageRequest.of(0, 6));
//		List<Partida> resultados = partidaRepo.findAll();
		model.addAttribute("resultados", partidas);
		return "index";
	}
	
}
