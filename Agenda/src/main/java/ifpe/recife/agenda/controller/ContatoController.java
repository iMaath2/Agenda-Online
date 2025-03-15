package ifpe.recife.agenda.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ifpe.recife.agenda.model.Contato;
import ifpe.recife.agenda.service.ContatoService;

import java.util.Arrays;
import java.util.List;

@Controller
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        List<Contato> contatos = contatoService.obterContatosPorUsuarioId(usuarioId);
        model.addAttribute("contatos", contatos);
        return "dashboard";
    }

    @GetMapping("/novo-contato")
    public String formularioNovoContato() {
        return "novo-contato";
    }

    @PostMapping("/novo-contato")
    public String submeterNovoContato(@RequestParam String nome, @RequestParam String email, @RequestParam String endereco,
                                   @RequestParam("phone") String[] telefones, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        contatoService.salvarContato(usuarioId, nome, email, endereco, Arrays.asList(telefones));
        return "redirect:/dashboard";
    }

    @GetMapping("/pesquisar-contatos")
    public String pesquisarContatos(@RequestParam(value = "query", required = false) String query, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        List<Contato> contatos;
        if (query != null && !query.isEmpty()) {
            contatos = contatoService.pesquisarContatosPorNome(usuarioId, query);
        } else {
            contatos = contatoService.obterContatosPorUsuarioId(usuarioId);
        }
        model.addAttribute("contatos", contatos);
        return "dashboard";
    }
}