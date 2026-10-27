package com.example.proyecto.controller;

import com.example.proyecto.dto.UsuarioDtos.*;
import com.example.proyecto.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registro(@RequestBody RegistroRequest req) {
        return usuarioService.registrar(req);
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