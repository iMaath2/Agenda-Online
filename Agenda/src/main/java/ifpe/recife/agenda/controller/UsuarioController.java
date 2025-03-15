package ifpe.recife.agenda.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ifpe.recife.agenda.model.Usuario;
import ifpe.recife.agenda.service.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registrar")
    public String formularioRegistro() {
        return "registrar";
    }

    @PostMapping("/registrar")
    public String submeterRegistro(@RequestParam String nome, @RequestParam String email, @RequestParam String senha, Model model) {
        Usuario usuarioRegistrado = usuarioService.registrarUsuario(nome, email, senha);
        if (usuarioRegistrado != null) {
            model.addAttribute("mensagemSucesso", "Usuário registrado com sucesso. Faça login.");
            return "login";
        } else {
            model.addAttribute("mensagemErro", "Email já cadastrado.");
            return "registrar";
        }
    }

    @GetMapping("/login")
    public String formularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String submeterLogin(@RequestParam String email, @RequestParam String senha, HttpServletRequest request, Model model) {
        Usuario usuario = usuarioService.logarUsuario(email, senha);
        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioId", usuario.getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("mensagemErro", "Email ou senha inválidos.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/perfil")
    public String formularioPerfil(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        Usuario usuario = usuarioService.encontrarUsuarioPorId(usuarioId);
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/perfil")
    public String submeterPerfil(@RequestParam String nome, @RequestParam String senha, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuarioId, nome, senha);
        if (usuarioAtualizado != null) {
            model.addAttribute("mensagemSucesso", "Perfil atualizado com sucesso.");
            model.addAttribute("usuario", usuarioAtualizado);
        } else {
            model.addAttribute("mensagemErro", "Erro ao atualizar perfil.");
        }
        return "perfil";
    }
}