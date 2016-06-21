/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas.collection.oserver;

import java.util.TreeSet;
import pessoas.model.Pessoa;

/**
 *
 * @author mfernandes
 */
public interface Observer {

    public void atualiza(TreeSet<Pessoa> pessoas);
}
