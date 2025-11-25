package modelo;

public class Usuario {


    //Declaracion de atributos

    private String nombre;
    private String apellido;
    private String nombre_Usuario;
    private String telefono;
    private String email;
    private String password;


    //gettrs y settrs para nombres

    public String getNombre() {
        return nombre;

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;

    }


    //gettrs y settrs para apellido

    public String getApellido() {
        return apellido;

    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    //gettrs y settrs para nombre_Usuario

    public String getNombre_Usuario() {
        return nombre_Usuario;

    }

    public void setNombre_Usuario(String nombre_Usuario) {
        this.nombre_Usuario = nombre_Usuario;
    }


    //gettrs y settrs para telefono

    public String getTelefono() {
        return telefono;

    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    //gettrs y settrs para email

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;

    }


    //gettrs y settrs para password

    public String getPassword() {
        return password;

    }

    public void setPassword(String password) {
        this.password = password;
    }


}
