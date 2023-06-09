package tomass.tz18004;

import java.awt.List;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;


public class choosePersonFrame extends javax.swing.JFrame {
    
    DefaultListModel model = new DefaultListModel();
    private Person chosenPers;
    private File chosenFile;
    viewKnowledgeFrame viewKnowledge;
    

    public choosePersonFrame() {
        initComponents();
        File folder = new File("./personFiles/");
        File[] listOfPersonFiles = folder.listFiles();
        for(int i = 0; i < listOfPersonFiles.length; i++){
            String filename = listOfPersonFiles[i].getName();
            if(filename.endsWith(".xml")||filename.endsWith(".XML")) {
                model.addElement(filename);
                System.out.println(filename);
            }
        }
//        for (int i = 0; i < 15; i++) {
//        model.addElement("Element " + i);
//        }
        personList.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        chooseBtn = new javax.swing.JButton();
        personScrollPane = new javax.swing.JScrollPane();
        personList = new javax.swing.JList<>();
        refreshBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        detailsScrollPane = new javax.swing.JScrollPane();
        personTable = new javax.swing.JTable();
        changeBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        viewKnowlBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chooseBtn.setText("Izvēlēties");
        chooseBtn.setEnabled(false);
        chooseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseBtnActionPerformed(evt);
            }
        });

        personList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        personList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                personListMouseClicked(evt);
            }
        });
        personScrollPane.setViewportView(personList);

        refreshBtn.setText("Atsvaidzināt");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Izmainīt laukus:");

        personTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        personTable.setCellSelectionEnabled(true);
        personTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                personTableMouseClicked(evt);
            }
        });
        personTable.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                personTableInputMethodTextChanged(evt);
            }
        });
        personTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                personTablePropertyChange(evt);
            }
        });
        personTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                personTableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                personTableKeyTyped(evt);
            }
        });
        detailsScrollPane.setViewportView(personTable);

        changeBtn.setText("Izmainīt");
        changeBtn.setEnabled(false);
        changeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Dzēst");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        viewKnowlBtn.setText("Apskatīt personas zināšanas");
        viewKnowlBtn.setEnabled(false);
        viewKnowlBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewKnowlBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chooseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(refreshBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(changeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(detailsScrollPane)
                            .addComponent(personScrollPane)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(viewKnowlBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(refreshBtn)
                        .addGap(18, 18, 18)
                        .addComponent(chooseBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn))
                    .addComponent(personScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(changeBtn))
                    .addComponent(detailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewKnowlBtn)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        model.removeAllElements();
        File folder = new File("./personFiles/");
        File[] listOfPersonFiles = folder.listFiles();
        for (File listOfPersonFile : listOfPersonFiles) {
            String filename = listOfPersonFile.getName();
            if(filename.endsWith(".xml")||filename.endsWith(".XML")) {
                model.addElement(filename);
                System.out.println(filename);
            }
        }
        personList.setModel(model);
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void chooseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseBtnActionPerformed
        String selectedFile = personList.getSelectedValue();
        try {
            chosenFile = new File("./personFiles/" + selectedFile);
            FileInputStream fis = new FileInputStream(chosenFile);
            XMLDecoder decoder = new XMLDecoder(fis);
            chosenPers = (Person) decoder.readObject();
            decoder.close();
            fis.close();
            System.out.print(chosenPers.getName());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(choosePersonFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(choosePersonFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel tModel = (DefaultTableModel)personTable.getModel();
        if(tModel.getRowCount() != 0) tModel.removeRow(0);
        tModel.setColumnIdentifiers(new Object[]{"Vārds", "Uzvārds", "Vecums"});
        tModel.addRow(new Object[]{chosenPers.getName(), chosenPers.getSurname(), chosenPers.getAge()});
        personTable.setModel(tModel);
        viewKnowlBtn.setEnabled(rootPaneCheckingEnabled);
    }//GEN-LAST:event_chooseBtnActionPerformed

    private void personTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_personTableMouseClicked
        changeBtn.setEnabled(rootPaneCheckingEnabled);        
    }//GEN-LAST:event_personTableMouseClicked

    private void changeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeBtnActionPerformed
        deletebyFileName();
        try {
            DefaultTableModel tModel = (DefaultTableModel)personTable.getModel();
            String name = (String)tModel.getValueAt(0, 0);
            chosenPers.setName(name);
            String surname = (String)tModel.getValueAt(0, 1);
            chosenPers.setSurname(surname);
            if(!(tModel.getValueAt(0, 2) instanceof Integer))
            {
                String ageS = (String)tModel.getValueAt(0, 2);
                Integer age = Integer.valueOf(ageS);
                chosenPers.setAge(age);
            }
            else
            {
                chosenPers.setAge((Integer)tModel.getValueAt(0, 2));                
            }
            //System.out.print((Integer)tModel.getValueAt(0, 2));
            chosenFile = new File("./personFiles/"+name+"_"+surname+".xml");
            FileOutputStream fos = new FileOutputStream(chosenFile);
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(chosenPers);
            encoder.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(choosePersonFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(choosePersonFrame.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_changeBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deletebyFileName();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void personListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_personListMouseClicked
        chooseBtn.setEnabled(rootPaneCheckingEnabled);
        deleteBtn.setEnabled(rootPaneCheckingEnabled);
    }//GEN-LAST:event_personListMouseClicked

    private void personTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_personTablePropertyChange
        
    }//GEN-LAST:event_personTablePropertyChange

    private void personTableInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_personTableInputMethodTextChanged
        
    }//GEN-LAST:event_personTableInputMethodTextChanged

    private void personTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_personTableKeyTyped
        
    }//GEN-LAST:event_personTableKeyTyped

    private void personTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_personTableKeyPressed

    }//GEN-LAST:event_personTableKeyPressed

    private void viewKnowlBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewKnowlBtnActionPerformed
        viewKnowledge = new viewKnowledgeFrame(chosenPers);
        viewKnowledge.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewKnowledge.setVisible(true);
    }//GEN-LAST:event_viewKnowlBtnActionPerformed
    
    private void deletebyFileName()
    {
    String selectedFile = personList.getSelectedValue();
    File myObj = new File("./personFiles/" + selectedFile);
    if (myObj.delete()) { 
      System.out.println("Deleted the file: " + myObj.getName());
    } else {
      System.out.println("Failed to delete the file.");
    }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(choosePersonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(choosePersonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(choosePersonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(choosePersonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new choosePersonFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeBtn;
    private javax.swing.JButton chooseBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JScrollPane detailsScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JList<String> personList;
    private javax.swing.JScrollPane personScrollPane;
    private javax.swing.JTable personTable;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton viewKnowlBtn;
    // End of variables declaration//GEN-END:variables
}
