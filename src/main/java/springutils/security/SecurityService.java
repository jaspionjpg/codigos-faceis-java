package springutils.security;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springutils.security.models.UsuarioApplication;

import java.util.HashSet;

@Service
public class SecurityService implements UserDetailsService {

    public String getUsernameUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;

        if (principal == null) {
            return StringUtils.EMPTY;
        }

        if (principal instanceof String) {
            return (String) principal;
        }

        if (principal instanceof User) {
            return ((User) principal).getUsername();
        }

        return StringUtils.EMPTY;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioApplication usuarioApplication = null; //usuarioApplicationRepository.findOneByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não existe."));

        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + usuarioApplication.getTipoUsuario().name()));

        if (usuarioApplication.getPerfil() != null) {
            usuarioApplication.getPerfil().getPermissoes().forEach(permissao -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + permissao.getRole())));
        }

        return new User(usuarioApplication.getUsername(), usuarioApplication.getPassword(), grantedAuthorities);
    }
}
