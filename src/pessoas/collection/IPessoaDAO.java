package pessoas.collection;

import java.util.ArrayList;
import java.util.TreeSet;
import pessoas.collection.observer.Observer;
import pessoas.model.Pessoa;

public abstract class IPessoaDAO<T> {

    protected ArrayList<Observer> observadores = new ArrayList<>();

    public abstract boolean add(Pessoa p) throws Exception;

    public abstract void addAll(TreeSet<Pessoa> pessoas) throws Exception;

    public abstract void addObserver(Observer observer);

    public abstract boolean altera(Pessoa pessoaExistente, String nomeAnterior) throws Exception;

    public abstract void carregaPessoas() throws Exception;

    public abstract boolean contains(Pessoa p) throws Exception;

    public abstract Pessoa getPessoaByName(String nome) throws Exception;

    public abstract TreeSet<Pessoa> getTreeSet() throws Exception;

    public void notifyObservers() throws Exception {
        for (Observer observer : observadores) {
            observer.atualiza(IPessoaDAO.this.getTreeSet());
        }
    }

    public abstract boolean remove(String nome) throws Exception;
}
