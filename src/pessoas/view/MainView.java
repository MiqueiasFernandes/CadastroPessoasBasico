package pessoas.view;

import javax.swing.JMenuItem;

public class MainView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        adicionarJItem = new javax.swing.JMenuItem();
        listarPessoasJItem = new javax.swing.JMenuItem();
        configurarJItem = new javax.swing.JMenuItem();
        sairJItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro de pessoas");

        jMenu1.setText("Opções");

        adicionarJItem.setText("Adicionar");
        jMenu1.add(adicionarJItem);

        listarPessoasJItem.setText("Listar pessoas");
        jMenu1.add(listarPessoasJItem);

        configurarJItem.setText("Configurar");
        jMenu1.add(configurarJItem);

        sairJItem.setText("Sair");
        jMenu1.add(sairJItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JMenuItem getAdicionarJItem() {
        return adicionarJItem;
    }

    public JMenuItem getListarPessoasJItem() {
        return listarPessoasJItem;
    }

    public JMenuItem getConfigurarJItem() {
        return configurarJItem;
    }

    public JMenuItem getSairJItem() {
        return sairJItem;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem adicionarJItem;
    private javax.swing.JMenuItem configurarJItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem listarPessoasJItem;
    private javax.swing.JMenuItem sairJItem;
    // End of variables declaration//GEN-END:variables
}
