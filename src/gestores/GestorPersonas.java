package gestores;

import excepciones.*;
import modelos.Persona;
import java.util.ArrayList;

public abstract class GestorPersonas<T extends Persona> implements IGestionable<String> {
    protected ArrayList<T> personas;

    public GestorPersonas(ArrayList<T> personas) {
        this.personas = personas;
    }

    public GestorPersonas() {
        personas = new ArrayList<>();
    }

    public ArrayList<T> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<T> personas) {
        this.personas = personas;
    }

    // Métodos ABM y Listar (serán implementados por las clases hijas)
    @Override
    public abstract void agregar();

    @Override
    public abstract void eliminar(String dni);

    @Override
    public abstract void modificar(String dni);

    @Override
    public abstract void listar();

    // Métodos genéricos para pedir datos (comunes a todas las personas)
    protected void pedirDNI(T persona, String mensaje) {
        boolean dniValido = false;
        do {
            try {
                String dni = GestorEntradas.pedirCadena(mensaje);

                if (Verificador.verificarDNI(dni) && Verificador.verificarDNIUnico(dni, personas)) {
                    persona.setDni(dni);
                    dniValido = true;
                }
            } catch (DNIInvalidoException | DNIExistenteException e) {
                System.err.println(e.getMessage());
            }
        } while (!dniValido);
    }

    protected void pedirNombre(T persona, String mensaje) {
        boolean nombreValido = false;
        do {
            try {
                String nombre = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarNombre(nombre)) {
                    persona.setNombre(nombre);
                    nombreValido = true;
                }
            } catch (NombreInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!nombreValido);
    }

    protected void pedirApellido(T persona, String mensaje) {
        boolean apellidoValido = false;
        do {
            try {
                String apellido = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarApellido(apellido)) {
                    persona.setApellido(apellido);
                    apellidoValido = true;
                }
            } catch (ApellidoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!apellidoValido);
    }

    protected void pedirNacionalidad(T persona, String mensaje) {
        boolean nacionalidadValida = false;
        do {
            try {
                String nacionalidad = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarNacionalidad(nacionalidad)) {
                    persona.setNacionalidad(nacionalidad);
                    nacionalidadValida = true;
                }
            } catch (NacionalidadInvalidaException e) {
                System.err.println(e.getMessage());
            }
        } while (!nacionalidadValida);
    }

    protected void pedirDomicilio(T persona, String mensaje) {
        boolean domicilioValido = false;
        do {
            try {
                String domicilio = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarDomicilio(domicilio)) {
                    persona.setDomicilio(domicilio);
                    domicilioValido = true;
                }
            } catch (DomicilioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!domicilioValido);
    }

    protected void pedirTelefono(T persona, String mensaje) {
        boolean telefonoValido = false;
        do {
            try {
                String telefono = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarTelefono(telefono)) {
                    persona.setTelefono(telefono);
                    telefonoValido = true;
                }
            } catch (TelefonoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!telefonoValido);
    }

    protected void pedirMail(T persona, String mensaje) {
        boolean mailValido = false;
        do {
            try {
                String mail = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarMail(mail)) {
                    persona.setMail(mail);
                    mailValido = true;
                }
            } catch (MailInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!mailValido);
    }
}
