package pt.old.school.sharks.rocketleague.torneios.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.old.school.sharks.rocketleague.torneios.model.Jogador;
import pt.old.school.sharks.rocketleague.torneios.model.PartidaStaging;
import pt.old.school.sharks.rocketleague.torneios.model.Partida;
import pt.old.school.sharks.rocketleague.torneios.repository.EquipaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.JogadorRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.PartidaStagingRepository;
import pt.old.school.sharks.rocketleague.torneios.repository.SequenceGeneratorService;

@Controller
public class HomeController {
	
	@Autowired
	JogadorRepository jogadorRepo;
	
	@Autowired
	EquipaRepository equipaRepo;

	@Autowired
	PartidaRepository partidaRepo;
	
	@Autowired
	PartidaStagingRepository partidaStagingRepo;
	
	@Autowired
	SequenceGeneratorService sequenceService;
	
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Date dataPartida ;
	private static SimpleDateFormat dataPT=new SimpleDateFormat("dd/MM/yyyy");  
	
	boolean pedidoSubmetidoComSucesso = false;
	List<String> erros = new ArrayList<String>();
	
	@RequestMapping(value="/")
	public String index(Model model, Authentication auth) {
		List<Partida> partidas = partidaRepo.findByOrderByDataDesc(PageRequest.of(0, 6));
		model.addAttribute("partidas", partidas);
		
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			List<Jogador> jogadores = jogadorRepo.findAll();
			model.addAttribute("jogadores", jogadores);
			//to do: só quero os resultados do próprio dia
			List<PartidaStaging> resultados;
			try {
				resultados = partidaStagingRepo.getByDate(getDataPartida());
			} catch (ParseException e) {
				resultados = null;
			}
			model.addAttribute("resultados", resultados);
			
			PartidaStaging partidaStaging = new PartidaStaging();
			partidaStaging.setData(dataPartida);
			model.addAttribute("partidaStaging", partidaStaging);
			
			model.addAttribute("pedidoSubmetidoComSucesso", pedidoSubmetidoComSucesso);
			setPedidoSubmetidoComSucesso(false);
		}		
		
		return "index";
	}

	public Date getDataPartida() throws ParseException {
		if(dataPartida == null) {
			dataPartida = dataPT.parse(dataPT.format(new Date()));
		}
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public static SimpleDateFormat getDataPT() {
		return dataPT;
	}

	public static void setDataPT(SimpleDateFormat dataPT) {
		HomeController.dataPT = dataPT;
	}

	public boolean isPedidoSubmetidoComSucesso() {
		return pedidoSubmetidoComSucesso;
	}

	public void setPedidoSubmetidoComSucesso(boolean pedidoSubmetidoComSucesso) {
		this.pedidoSubmetidoComSucesso = pedidoSubmetidoComSucesso;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
}
