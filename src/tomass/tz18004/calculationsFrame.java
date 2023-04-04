package tomass.tz18004;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static tomass.tz18004.KnowledgeType.EXPLICIT;
import static tomass.tz18004.KnowledgeType.MIXED;

/**
 *
 * @author Tomass
 */
public class calculationsFrame extends javax.swing.JFrame {

    private Vector<Link> linkList;
    private Vector<Knowledge> knowList;
    private Vector<Concept> concList;
    private Integer chosenId;
    private Person chosenPers;
    private Integer index;

    public calculationsFrame(Integer Id, Person chosenPers, Vector<Knowledge> knowList, Vector<Concept> concList, Vector<Link> linkList) {
        this.chosenId = Id;
        this.chosenPers = chosenPers;
        this.linkList = linkList;
        this.knowList = knowList;
        this.concList = concList;
        index = determineIndexById(chosenId);
        initComponents();
        if(knowList.get(index).getQuotient() != null)
        {
            coefField.setEnabled(true);
            coefField.setText(knowList.get(index).getQuotient().toString());
        };
        if(knowList.get(index).getType() != null)
        {
            typeField.setEnabled(true);
            KnowledgeType type = knowList.get(index).getType();
            String typeStr = null;
            if(null == type) typeStr = "Neizteiktā";
            else switch (type) {
                case EXPLICIT:
                    typeStr = "Formālā";
                    break;
                case MIXED:
                    typeStr = "Tiešā";
                    break;
                default:
                    typeStr = "Neizteiktā";
                    break;
            }
            typeField.setText(typeStr);
        }
    }

    public calculationsFrame() {
        initComponents();
    }
    
    private Integer determineIndexById(Integer id)
    {
        if(!knowList.isEmpty())
        {
            for(Integer i = 0; i < knowList.size(); i++)
            {
                if(knowList.get(i).getId().equals(id))
                    return i;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        coefField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        calcCoefBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        typeField = new javax.swing.JTextField();
        calcTypeBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aprēķinu funkcijas");

        coefField.setEditable(false);
        coefField.setEnabled(false);

        jLabel2.setText("Personīgais koeficients pašlaik:");

        calcCoefBtn.setText("Veikt aprēķinu");
        calcCoefBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcCoefBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Noteiktais tips pašlaik:");

        typeField.setEditable(false);
        typeField.setEnabled(false);

        calcTypeBtn.setText("Noteikt");
        calcTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcTypeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(calcCoefBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(coefField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(typeField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(calcTypeBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coefField)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(calcCoefBtn)
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeField))
                .addGap(18, 18, 18)
                .addComponent(calcTypeBtn)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calcCoefBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcCoefBtnActionPerformed
        knowList.get(index).setQuotient(linkList);
        coefField.setEnabled(true);
        coefField.setText(knowList.get(index).getQuotient().toString());
        writeToFile();
    }//GEN-LAST:event_calcCoefBtnActionPerformed

    private void calcTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcTypeBtnActionPerformed
        knowList.get(index).setType();
        typeField.setEnabled(true);
        KnowledgeType type = knowList.get(index).getType();
        if(type == KnowledgeType.EXPLICIT) typeField.setText("Formālā");
        else if(type == KnowledgeType.MIXED) typeField.setText("Tiešā");
        else typeField.setText("Neizteiktā");
        writeToFile();
    }//GEN-LAST:event_calcTypeBtnActionPerformed

    
    private void writeToFile()
    {
        File knowFile = new File("./knowledgeFiles/"+chosenPers.getName()+"_"+chosenPers.getSurname()+".xml");
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
            java.util.logging.Logger.getLogger(calculationsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(calculationsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(calculationsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(calculationsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new calculationsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calcCoefBtn;
    private javax.swing.JButton calcTypeBtn;
    private javax.swing.JTextField coefField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField typeField;
    // End of variables declaration//GEN-END:variables
}
