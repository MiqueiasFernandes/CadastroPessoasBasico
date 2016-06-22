/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.collection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import pessoas.model.Usuario;

public class UsuarioDAOTXT implements IUsuarioDAO {

    private Properties usuarios;
    private File file;

    public UsuarioDAOTXT() throws IOException {
        file = new File("data/usuarios.properties");
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public void add(Usuario usuario) throws Exception {
        if (usuarios != null && file != null) {
            usuarios.put(usuario.getNome(), usuario.getSenha());
            if (usuario.isAdministrador()) {
                usuarios.put("@dministrador", usuario.getNome());
            }
            usuarios.store(new FileOutputStream(file), "salvo automaticamente.");
        } else {
            throw new Exception("Falha ao salvar usuario no arquivo");
        }
    }

    @Override
    public void carregaUsuarios() throws IOException {
        usuarios = new Properties();
        FileInputStream fis = new FileInputStream(file);
        usuarios.load(fis);
    }

    @Override
    public boolean contains(String nome) {
        return usuarios.containsKey(nome);
    }

    @Override
    public Usuario getUsuarioByName(String nome) {
        if (!contains(nome)) {
            return null;
        }
        return new Usuario(nome, usuarios.getProperty(nome), isAdministrador(nome));
    }

    public boolean isAdministrador(String nome) {
        return nome.equals(usuarios.getProperty("@dministrador"));
    }

    @Override
    public int count() throws Exception {
        if (usuarios != null) {
            return usuarios.stringPropertyNames().size();
        } else {
            throw new Exception("Arquivo de usuarios ainda não foi carregado.");
        }
    }

}
