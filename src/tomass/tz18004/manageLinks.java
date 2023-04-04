/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tomass.tz18004;

import java.awt.Color;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;

public class manageLinks extends javax.swing.JFrame {
    
    private Integer chosenId;
    private Integer chosenIndex;
    private Vector<Knowledge> knowList;
    private Vector<Concept> concList;
    private Vector<Link> linkList;
    private Person chosenPers;
    private File knowFile;


    public manageLinks(Integer Id, Vector<Knowledge> knowList, Vector<Concept> concList, Vector<Link> linkList, Person chosenPers) {
        this.chosenId = Id;
        System.out.println(chosenId);
        this.knowList = knowList;
        this.concList = concList;
        this.linkList = linkList;
        this.chosenPers = chosenPers;
        initComponents();
        knowChoiceBox.removeAllItems();
        knowFile = new File("./knowledgeFiles/"+chosenPers.getName()+"_"+chosenPers.getSurname()+".xml");
    }
    
    public manageLinks() {
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        deleteRBtn = new javax.swing.JRadioButton();
        createRBtn = new javax.swing.JRadioButton();
        knowChoiceBox = new javax.swing.JComboBox<>();
        instructionLabel = new javax.swing.JLabel();
        linkMeaningField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        finishBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(java.awt.SystemColor.inactiveCaption);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pārvaldīt saiknes ar zināšanām un konceptiem");
        jLabel1.setAutoscrolls(true);

        buttonGroup1.add(deleteRBtn);
        deleteRBtn.setText("Dzēst esošu");
        deleteRBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRBtnActionPerformed(evt);
            }
        });

        buttonGroup1.add(createRBtn);
        createRBtn.setText("Izveidot jaunu");
        createRBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRBtnActionPerformed(evt);
            }
        });

        knowChoiceBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        knowChoiceBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                knowChoiceBoxActionPerformed(evt);
            }
        });

        instructionLabel.setText("Izvēlieties zināšanu vai konceptu: ");

        jLabel3.setText("Saiknes nozīme:");

        finishBtn.setText("Saglabāt");
        finishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(deleteRBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(linkMeaningField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(knowChoiceBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createRBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(instructionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(finishBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 8, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteRBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createRBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(instructionLabel)
                .addGap(18, 18, 18)
                .addComponent(knowChoiceBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(linkMeaningField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(finishBtn)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteRBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRBtnActionPerformed
        linkMeaningField.setEditable(false);
        instructionLabel.setText("Izvēlieties saikni:" );
        knowChoiceBox.removeAllItems();
        String subName = null, objName = null;
        for(Integer i = 0; i < linkList.size(); i++)
        {
            //meaning = linkList.get(i).getMeaning();
            subName = getNameById(linkList.get(i).getSubject());
            objName = getNameById(linkList.get(i).getObject());
            System.out.println(linkList.get(i).getSubject() + " -> " + linkList.get(i).getObject());
            knowChoiceBox.addItem(subName + " -> " + objName);
            
        }
        //knowChoiceBox.addItem("Viens");
        //knowChoiceBox.getModel();
        
        knowChoiceBox.getSelectedItem();
    }//GEN-LAST:event_deleteRBtnActionPerformed

    private void knowChoiceBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knowChoiceBoxActionPerformed
        
        //linkMeaningField.setEditable(true);
        if(deleteRBtn.isSelected())
        {
            if(knowChoiceBox.getSelectedIndex() >= 0)
            {
                chosenIndex = knowChoiceBox.getSelectedIndex();
                String meaning = linkList.get(chosenIndex).getMeaning();
                linkMeaningField.setText(meaning);
            }
            finishBtn.setText("Dzēst");
            
        }
        else if(createRBtn.isSelected())
        {
            if(knowChoiceBox.getSelectedIndex() >= 0)
            {
                chosenIndex = knowChoiceBox.getSelectedIndex();
                String meaning = "";
                linkMeaningField.setText(meaning);
            }
            finishBtn.setText("Izveidot");
            
        }
    }//GEN-LAST:event_knowChoiceBoxActionPerformed

    private void createRBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRBtnActionPerformed
        knowChoiceBox.removeAllItems();
        linkMeaningField.setEditable(true);
        instructionLabel.setText("Izvēlieties personu, ziņu vai konceptu:" );
        if(checkClass() == "Knowledge")
        {
            knowChoiceBox.addItem("(Persona) " + chosenPers.getName() + " " + chosenPers.getSurname());
            if(!knowList.isEmpty())
            {
                for(Integer i = 0; i < knowList.size(); i++)
                {
                    if(knowList.get(i).getId().equals(chosenId)) knowChoiceBox.addItem("");
                    else knowChoiceBox.addItem(knowList.get(i).getName());
                }
            };
        }
        if(!concList.isEmpty())
        {
            for(Integer i = 0; i < concList.size(); i++)
            {
                if(concList.get(i).getId().equals(chosenId)) knowChoiceBox.addItem("");
                else knowChoiceBox.addItem(concList.get(i).getName());
            }
        }        
    }//GEN-LAST:event_createRBtnActionPerformed

    private void finishBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBtnActionPerformed
        if(deleteRBtn.isSelected())
        {
            Integer subject = linkList.get(chosenIndex).getSubject();
            Integer object = linkList.get(chosenIndex).getObject();
            System.out.println(subject + " -> " + object);
            if(subject.equals(0))
            {
                chosenPers.deleteKnow(chosenId);
            }
            else //if(subject.equals(chosenId))
            {
                //deleteLinkById(subject, object);
//            }
//            else
//            {
                System.out.println(subject + " (rightpath) -> " + object);
                deleteLinkById(subject, object);
            }
            
        }
        else if(createRBtn.isSelected())
        {
            Integer objectId = null;
            String meaning = null;
            if(linkMeaningField.getText() != null) meaning = linkMeaningField.getText();
            if(checkClass() == "Knowledge")
            {
                System.out.println("Knowledge");
                if(chosenIndex.equals(0))
                {
                    chosenPers.getKnows().put(chosenId, meaning);
                    writeToPersonFile();
                    this.dispose();
                    return;
                }
                else
                {
                    Integer index = null;
                    if(chosenIndex <= knowList.size())
                    {
                        index = chosenIndex - 1;
                        objectId = knowList.get(index).getId();
                        if(objectId.equals(chosenId)){ knowChoiceBox.setBackground(Color.red); return;}
                    }
                    else if(chosenIndex <= (knowList.size() + concList.size()))
                    {
                        index = chosenIndex - 1 - knowList.size();
                        objectId = concList.get(index).getId();
                        if(objectId.equals(chosenId)) { knowChoiceBox.setBackground(Color.red); return;}
                    }
                    System.out.println(chosenId + " -> " + objectId);
                }
            }
            else
            {
                System.out.println("Concept");
                System.out.println("chosenIndex: " + chosenIndex);
                objectId = concList.get(chosenIndex).getId();
                if(objectId.equals(chosenId)){ knowChoiceBox.setBackground(Color.red); return;}
                System.out.println(chosenId + " -> " + objectId);
            }
            for(Integer i = 0; i < knowList.size(); i++)
            {
                if(knowList.get(i).getId().equals(chosenId))
                {
                    Map<Integer, String> links;
                    if(knowList.get(i).getLinkedKnowledge() != null)
                    {
                        knowList.get(i).getLinkedKnowledge().put(objectId, meaning);
                    }
                    else
                    {
                        links = new HashMap<>();
                        links.put(objectId, meaning);
                        knowList.get(i).setLinkedKnowledge(links);
                    }
                    System.out.println(knowList.get(i).getId() + " -> " + knowList.get(i).getLinkedKnowledge().get(objectId));
                    break;
                }
            }
            for(Integer i = 0; i < concList.size(); i++)
            {
                if(concList.get(i).getId().equals(chosenId))
                {
                    Map<Integer, String> links;
                    if(concList.get(i).getLinkedKnowledge() != null)
                    {
                        concList.get(i).getLinkedKnowledge().put(objectId, meaning);
                    }
                    else
                    {
                        links = new HashMap<>();
                        links.put(objectId, meaning);
                        concList.get(i).setLinkedKnowledge(links);
                    }
                    System.out.println(concList.get(i).getId() + " -> " + concList.get(i).getLinkedKnowledge().get(objectId));
                    break;
                }
            }  
        }
        writeToFile();
        this.dispose();
    }//GEN-LAST:event_finishBtnActionPerformed

    private String checkClass()
    {
        if(!knowList.isEmpty())
        {
            for(Integer i = 0; i < knowList.size(); i++)
            {
                if(knowList.get(i).getId().equals(chosenId))
                    return "Knowledge";
            }
        };
        if(!concList.isEmpty())
        {
            for(Integer i = 0; i < concList.size(); i++)
            {
                if(concList.get(i).getId().equals(chosenId))
                    return "Concept";
            }
        }
        return null;
    }

    private String getNameById(Integer id)
    {
        if(id.equals(0)) return chosenPers.getName();
        else
            {
            if(!knowList.isEmpty())
            {
                for(Integer i = 0; i < knowList.size(); i++)
                {
                    if(knowList.get(i).getId().equals(id))
                        return knowList.get(i).getName();
                }
            };
            if(!concList.isEmpty())
            {
                for(Integer i = 0; i < concList.size(); i++)
                {
                    if(concList.get(i).getId().equals(id))
                        return concList.get(i).getName();
                }
            }
            return null;
        }
        
    }
    
    private void deleteLinksById(Integer id)
    {
        for(Integer i = 0; i < knowList.size(); i++)
        {
            Map<Integer,String> list =  knowList.get(i).getLinkedKnowledge();
            if(list != null)
            {
                if(list.containsKey(id)) list.remove(id);            
            }
        }
        for(Integer i = 0; i < concList.size(); i++)
        {
            Map<Integer,String> list =  concList.get(i).getLinkedKnowledge();
            if(list != null)
            {
                if(list.containsKey(id)) list.remove(id);
            }
        }
    }
    
    private void deleteLinkById(Integer subject, Integer object)
    {
        for(Integer i = 0; i < knowList.size(); i++)
        {
            if(knowList.get(i).getId().equals(subject))
            {
                Map<Integer,String> list =  knowList.get(i).getLinkedKnowledge();
                if(list != null)
                {
                    if(list.containsKey(object)) list.remove(object);
                    return;
                }
            }
        }
        for(Integer i = 0; i < concList.size(); i++)
        {
            if(concList.get(i).getId().equals(subject))
            {
                Map<Integer,String> list =  concList.get(i).getLinkedKnowledge();
                if(list != null)
                {
                    if(list.containsKey(object)) list.remove(object);
                    return;
                }
            }
        }
    }
    
    private void writeToPersonFile()
    {
        try{
            FileOutputStream fos = new FileOutputStream(new File("./personFiles/"+chosenPers.getName()+"_"+chosenPers.getSurname()+".xml"));
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(chosenPers);
            encoder.close();
            fos.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    private void writeToFile()
    {
        try {
            FileOutputStream fos = new FileOutputStream(knowFile);
            XMLEncoder encoder = new XMLEncoder(fos);
            for(Integer i = 0; i < knowList.size(); i++)
            {
                
               encoder.writeObject(knowList.get(i));
            }
            for(Integer i = 0; i < concList.size(); i++)
            {
                
               encoder.writeObject(concList.get(i));
            }
            encoder.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
           Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
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
            java.util.logging.Logger.getLogger(manageLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageLinks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manageLinks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton createRBtn;
    private javax.swing.JRadioButton deleteRBtn;
    private javax.swing.JButton finishBtn;
    private javax.swing.JLabel instructionLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> knowChoiceBox;
    private javax.swing.JTextField linkMeaningField;
    // End of variables declaration//GEN-END:variables
}
