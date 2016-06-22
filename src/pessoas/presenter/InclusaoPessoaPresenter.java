package pessoas.presenter;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import pessoas.collection.IPessoaDAO;
import pessoas.model.Pessoa;
import pessoas.view.InclusaoPessoaView;

public class InclusaoPessoaPresenter {

    private IPessoaDAO pessoas;
    private InclusaoPessoaView view;

    public InclusaoPessoaPresenter(IPessoaDAO pessoas) {

        view = new InclusaoPessoaView();

        this.pessoas = pessoas;

        view.getBtnSalvar().addActionListener(this::btnSalvarPessoa);

        view.getBtnFechar().addActionListener(this::btnFechar);

        view.setVisible(true);

    }

    public void btnSalvarPessoa(ActionEvent evt) {
        String nome = view.getTxtNome().getText();
        String telefone = view.getTxtTelefone().getText();

        try {

            if ((!nome.equals("")) && (!telefone.equals(""))) {
                Pessoa p = new Pessoa(nome, telefone);
                if (!pessoas.add(p)) {
                    throw new Exception("Pessoa já existente");
                } else {
                    JOptionPane.showMessageDialog(view, nome + " cadastrado com sucesso!");
                    view.getTxtNome().setText("");
                    view.getTxtTelefone().setText("");
                }
            } else {
                throw new Exception("Você precisa informar os campos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }

    }

    public void btnFechar(ActionEvent evt) {
        view.setVisible(false);
        view.dispose();
    }

    public InclusaoPessoaView getView() {
        return view;
    }

}
