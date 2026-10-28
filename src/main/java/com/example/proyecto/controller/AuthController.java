package com.example.proyecto.controller;

import com.example.proyecto.dto.UsuarioDtos.*;
import com.example.proyecto.security.UsuarioDetailsService;
import com.example.proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioDetailsService detailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final long expiracionMin;

    public AuthController(UsuarioService usuarioService,
                          UsuarioDetailsService detailsService,
                          PasswordEncoder passwordEncoder,
                          JwtEncoder jwtEncoder,
                          @Value("${jwt.expiracion-minutos}") long expiracionMin) {
        this.usuarioService = usuarioService;
        this.detailsService = detailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.expiracionMin = expiracionMin;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registro(@RequestBody RegistroRequest req) {
        return usuarioService.registrar(req);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest req) {
        UserDetails u;
        try {
            u = detailsService.loadUserByUsername(req.email());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        if (!u.isEnabled() || !passwordEncoder.matches(req.password(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String rol = u.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        Instant ahora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(u.getUsername())   // el email; por eso auth.getName() sigue funcionando
                .issuedAt(ahora)
                .expiresAt(ahora.plus(expiracionMin, ChronoUnit.MINUTES))
                .claim("rol", rol)
                .build();

        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)
        ).getTokenValue();

        return new TokenResponse(token, "Bearer", expiracionMin * 60);
    }

    @GetMapping("/aviso-privacidad")
    public String avisoPrivacidad() {
        return """
        1. Responsable
Brian Sepulveda Romero / canciones_api, con domicilio en Zumpango, La trinidad y correo de contacto elbriansr@gmail.com, es responsable del tratamiento de sus datos personales.

2. Datos personales que recabamos
Nombre, correo electrónico y contraseña (almacenada de forma cifrada). No recabamos datos personales sensibles.

3. Finalidades
Primarias (necesarias para el servicio):
  - Crear y administrar su cuenta de usuario.
  - Autenticarlo y controlar sus permisos según su rol.
Secundarias (opcionales):
  - Enviarle promociones o información de novedades.
Si no desea que sus datos se usen para las finalidades secundarias, puede manifestarlo en cualquier momento mediante el derecho de oposición descrito más abajo.

4. Derechos ARCO
Usted puede ejercer sus derechos de Acceso, Rectificación, Cancelación y Oposición:
  - Acceso: consultar los datos que tenemos sobre usted.
  - Rectificación: corregir datos inexactos o incompletos.
  - Cancelación: solicitar que se elimine su cuenta y se
    anonimicen sus datos.
  - Oposición: negarse al uso de sus datos para finalidades
    secundarias.

5. Transferencias
No transferimos sus datos personales a terceros.

6. Limitar el uso o divulgación
Puede limitar el uso de sus datos mediante la oposición o cancelando su cuenta.

7. Cambios al aviso
Cualquier modificación se le notificará por correo.

Fecha de última actualización: 22 de octubre de 2026
        """;
    }
}