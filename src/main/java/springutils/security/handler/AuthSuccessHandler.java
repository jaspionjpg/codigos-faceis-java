package springutils.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import springutils.security.models.UsuarioApplication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UsuarioApplication usuario =  null;//usuarioApplicationRepository.findOneByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Usuário não existe."));

        request.getSession().setAttribute("usuarioLogado", usuario);
        request.getSession().setAttribute("failure", false);

        getRedirectStrategy().sendRedirect(request, response, usuario.getTipoUsuario().getTelaInicial());
    }

}
