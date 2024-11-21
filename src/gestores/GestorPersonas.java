package gestores;

import excepciones.*;
import modelos.Persona;
import java.util.ArrayList;

public abstract class GestorPersonas<T extends Persona> implements IGestionable<String> {
    //Constantes para implementar color como en los menu
    private final String colorRojo = "\u001B[91m";
    private final String colorVerde = "\u001B[92m";
    private final String colorAzul = "\u001B[94m";
    private final String resetColor = "\u001B[0m";

    //El gestor de personas se puede heredar por otros gestores que manejen clases que hereden de Persona.
    //Este arreglo es el que luego se llama desde las clases hijas
    protected ArrayList<T> personas;

    //Constructores
    public GestorPersonas(ArrayList<T> personas) {
        this.personas = personas;
    }
    public GestorPersonas() {
        personas = new ArrayList<>();
    }

    //Getters y Setters
    public ArrayList<T> getPersonas() {
        return personas;
    }
    public void setPersonas(ArrayList<T> personas) {
        this.personas = personas;
    }

    // Métodos ABM y Listar (serán implementados por las clases hijas)
    //Son abstract porque esta clase lo es, pero ademas provienen de la interfaz Gestionable
    @Override
    public abstract void agregar();
    @Override
    public abstract void eliminar(String dni);
    @Override
    public abstract void modificar(String dni);
    @Override
    public abstract void listar();

    //Metodos genericos para pedir datos (comunes a todas las personas)
    //Todos los metodos funcionan de manera similar, reciben la persona a modificar, piden la carga de datos
    //(que pasa por verificaciones) y la modifican, no hace falta retornalo porque esto se hace dentro el gestor
    //entonces se modifica directamente modifica el atributo del cliente que recibe dentro del gestor
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);            }
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);            }
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
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
            }
        } while (!mailValido);
    }
}
