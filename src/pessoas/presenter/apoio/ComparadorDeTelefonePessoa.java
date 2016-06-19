/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pessoas.presenter.apoio;

import java.util.Comparator;
import pessoas.model.Pessoa;

/**
 *
 * @author Clayton
 */
public class ComparadorDeTelefonePessoa implements Comparator<Pessoa> {
   @Override
   public int compare(Pessoa aPerson, Pessoa anotherPerson) {
      return aPerson.getTelefone().compareTo(anotherPerson.getTelefone());
   }   
}

