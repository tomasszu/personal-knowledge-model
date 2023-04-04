/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tomass.tz18004;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;


public class viewKnowledgeFrame extends javax.swing.JFrame {
    
    private Knowledge currKnow = null;
    private Concept currConc = null;
    private Vector<Knowledge> knowList;
    private Vector<Concept> concList;
    private Vector<Link> linkList;
    private Person chosenPers;
    private File knowFile;
    private Integer editRow;
    private Map<Integer, String> personKnowMap;
    private Map<Integer, String> linkedKnowledgeMap;
    manageLinks linkFrame;
    calculationsFrame calcFrame;
    inferenceManagerForm infManagerFrame;
    /**
     * Creates new form viewKnowledgeFrame
     * @param chosenPers
     */
    public viewKnowledgeFrame(Person chosenPerson) {
        initComponents();
        this.chosenPers = chosenPerson;
        headerLabel.setText(chosenPerson.getName() + " personīgās zināšanas");
        knowFile = new File("./knowledgeFiles/"+chosenPers.getName()+"_"+chosenPers.getSurname()+".xml");
        //concFile = new File("./knowledgeFiles/Concepts/"+chosenPers.getName()+"_"+chosenPers.getSurname()+".xml");
        knowList = new Vector<Knowledge>();
        concList = new Vector<Concept>();
        editRow = null;
        setKnowMap(0);
        initializeArray();
        prepareTable();
        formatLinksTable();
    }

    public viewKnowledgeFrame() {
        
    }

    private void prepareTable()
    {
        //DefaultTableModel tModel = (DefaultTableModel)knowTable.getModel();
        DefaultTableModel tModel = new DefaultTableModel();
        //if(tModel.getRowCount() != 0) tModel.removeRow(0);
        tModel.setColumnIdentifiers(new Object[]{"Id", "Klase", "Nosaukums", "Tēma", "Personīgā koef.", "Tips"});
        if(!knowList.isEmpty() || !concList.isEmpty())
        {
            knowTable.setEnabled(true);
            for(Integer i = 0; i < knowList.size(); i++)
            {
                String typeStr = null;
                if(knowList.get(i).getType() != null)
                {
                    KnowledgeType type = knowList.get(i).getType();
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
                }
               tModel.addRow(new Object[]{knowList.get(i).getId(), "Ziņa", knowList.get(i).getName(), knowList.get(i).getTopic(), knowList.get(i).getQuotient(), typeStr});
            }
            for(Integer i = 0; i < concList.size(); i++)
            {
               tModel.addRow(new Object[]{concList.get(i).getId(), "Koncepts", concList.get(i).getName(), concList.get(i).getTopic(), "-", "-"});
            }
            knowTable.setModel(tModel);
        }
        else
        {   knowTable.setModel(tModel);
            knowTable.setEnabled(false);
        }
        knowTable.setDefaultEditor(Object.class, null);
        
    }
    
    private void formatLinksTable()
    {
        DefaultTableModel tModel = new DefaultTableModel();
        tModel.setColumnIdentifiers(new Object[]{"Subjekts", "Relācija", "Objekts"});
        linkTable.setModel(tModel);
        linkTable.setEnabled(false);
        linkTable.setDefaultEditor(Object.class, null);
        
    }
    
    private void prepareLinksTable()
    {
        linkList = new Vector<Link>();
        manageLinksBtn.setEnabled(true);
        DefaultTableModel tModel = new DefaultTableModel();
        tModel.setColumnIdentifiers(new Object[]{"Subjekts", "Relācija", "Objekts"});
        String Predicate;
        Integer id;
        String knowName;
        if(currKnow != null)
        {
            manageLinksBtn.setText("Pārvaldīt ziņas saiknes");
            linkTable.setEnabled(true);
            for (Map.Entry<Integer,String> aKnow : personKnowMap.entrySet())
            {
                id = aKnow.getKey();
                if(currKnow.getId().equals(id))
                {
                    Predicate = aKnow.getValue();
                    knowName = findNameById(id);
                    linkList.add(new Link(0, Predicate, id)); //0 = chosenPers
                    tModel.addRow(new Object[]{chosenPers.getName(), Predicate, knowName});
                }
            }
            for (Map.Entry<Integer,String> aLink : linkedKnowledgeMap.entrySet())
            {
                id = aLink.getKey();
                Predicate = aLink.getValue();
                knowName = findNameById(id);
                linkList.add(new Link(currKnow.getId(), Predicate, id));
                tModel.addRow(new Object[]{currKnow.getName(), Predicate, knowName});
            }
            for(Integer i = 0; i < knowList.size(); i++)
            {
                Map<Integer,String> list =  knowList.get(i).getLinkedKnowledge();
                if(list != null)
                {
                    if(list.containsKey(currKnow.getId()))
                    {
                        Predicate = list.get(currKnow.getId());
                        knowName = knowList.get(i).getName();
                        linkList.add(new Link(knowList.get(i).getId(), Predicate, currKnow.getId()));
                        tModel.addRow(new Object[]{ knowName, Predicate, currKnow.getName()});
                    }            
                }
            }
            for(Integer i = 0; i < concList.size(); i++)
            {
                Map<Integer,String> list =  concList.get(i).getLinkedKnowledge();
                if(list != null)
                {
                    if(list.containsKey(currKnow.getId()))
                    {
                        Predicate = list.get(currKnow.getId());
                        knowName = concList.get(i).getName();
                        linkList.add(new Link(concList.get(i).getId(), Predicate, currKnow.getId()));
                        tModel.addRow(new Object[]{ knowName, Predicate, currKnow.getName()});
                    }            
                }
            }
            linkTable.setModel(tModel);
        }
        else if(currConc != null)
        {
            manageLinksBtn.setText("Pārvaldīt koncepta saiknes");
            linkTable.setEnabled(true);
            for (Map.Entry<Integer,String> aLink : linkedKnowledgeMap.entrySet())
            {
                id = aLink.getKey();
                Predicate = aLink.getValue();
                knowName = findNameById(id);
                linkList.add(new Link(currConc.getId(), Predicate, id));
                tModel.addRow(new Object[]{currConc.getName(), Predicate, knowName});
            }
            for(Integer i = 0; i < knowList.size(); i++)
            {
                Map<Integer,String> list =  knowList.get(i).getLinkedKnowledge();
                if(list != null)
                {
                    if(list.containsKey(currConc.getId()))
                    {
                        Predicate = list.get(currConc.getId());
                        knowName = knowList.get(i).getName();
                        linkList.add(new Link(knowList.get(i).getId(), Predicate, currConc.getId()));
                        tModel.addRow(new Object[]{ knowName, Predicate, currConc.getName()});
                    }            
                }
            }
            for(Integer i = 0; i < concList.size(); i++)
            {
                Map<Integer,String> list =  concList.get(i).getLinkedKnowledge();
                if(list != null)
                {
                    if(list.containsKey(currConc.getId()))
                    {
                        Predicate = list.get(currConc.getId());
                        knowName = concList.get(i).getName();
                        linkList.add(new Link(concList.get(i).getId(), Predicate, currConc.getId()));
                        tModel.addRow(new Object[]{ knowName, Predicate, currConc.getName()});
                    }            
                }
            }
            linkTable.setModel(tModel);
        }
        else
        {
            //linkTable.setModel(tModel);
            linkTable.setEnabled(false);
        }        
    }
    
    private String findNameById(Integer id)
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
    
    private Integer findListNrById(Integer id, Integer listType)
    {
        if(listType == 0) //Is KnowList
        {
            if(!knowList.isEmpty())
            {
                for(Integer i = 0; i < knowList.size(); i++)
                {
                    if(knowList.get(i).getId().equals(id))
                        return i;
                }
            }
        }
        else
        {
            if(!concList.isEmpty())
            {
                for(Integer i = 0; i < concList.size(); i++)
                {
                    if(concList.get(i).getId().equals(id))
                        return i;
                }
            } 
        }
        return null;
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
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        viewKnPanel = new javax.swing.JPanel();
        headerLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        createBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        nameField = new javax.swing.JTextField();
        topicField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descField = new javax.swing.JTextArea();
        saveBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        knowTable = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        descBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        addLinkCB = new javax.swing.JCheckBox();
        linkMeaning = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        linkTable = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        linkConceptCB = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        concNameField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        concTopicField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        conclinkMeaningF = new javax.swing.JTextField();
        saveConcBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        manageLinksBtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        calcFunctionsBtn = new javax.swing.JButton();
        calcFunctionsBtn1 = new javax.swing.JButton();
        refreshBtn = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel3.setText("jLabel1");

        jLabel5.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        viewKnPanel.setBackground(java.awt.SystemColor.scrollbar);

        headerLabel.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        headerLabel.setText("Personas zināšanas");

        createBtn.setText("Jauna");
        createBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBtnActionPerformed(evt);
            }
        });

        editBtn.setText("Izmainīt");
        editBtn.setEnabled(false);
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        nameField.setEnabled(false);
        nameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameFieldMouseClicked(evt);
            }
        });
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        topicField.setEnabled(false);
        topicField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topicFieldMouseClicked(evt);
            }
        });

        descField.setColumns(20);
        descField.setLineWrap(true);
        descField.setRows(5);
        descField.setEnabled(false);
        descField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                descFieldMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(descField);

        saveBtn.setText("Saglabāt");
        saveBtn.setEnabled(false);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Nosaukums:");

        jLabel4.setText("Tēma:");

        jLabel6.setText("Apraksts:");

        knowTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        knowTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        knowTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        knowTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                knowTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(knowTable);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        descBtn.setText("Apskatīt ziņas aprakstu");
        descBtn.setEnabled(false);
        descBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Saikne ar personu:");

        addLinkCB.setText("Iekļaut saikni");
        addLinkCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLinkCBActionPerformed(evt);
            }
        });

        linkMeaning.setText("zina");
        linkMeaning.setEnabled(false);
        linkMeaning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linkMeaningActionPerformed(evt);
            }
        });

        jLabel7.setText("Saiknes nozīme:");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setText("Ziņas saiknes:");

        linkTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(linkTable);

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setText("Jauns koncepts:");

        linkConceptCB.setText("Pievienot konceptu atlasītajai ziņai");

        jLabel10.setText("Nosaukums:");

        concNameField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                concNameFieldMouseClicked(evt);
            }
        });
        concNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concNameFieldActionPerformed(evt);
            }
        });

        jLabel11.setText("Tēma:");

        jLabel12.setText("Saiknes nozīme:");

        saveConcBtn.setText("Saglabāt");
        saveConcBtn.setEnabled(false);
        saveConcBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConcBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Dzēst");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        manageLinksBtn.setText("Pārvaldīt saiknes");
        manageLinksBtn.setEnabled(false);
        manageLinksBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageLinksBtnActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel13.setText("Citas ar zināšanām saistītās funkcijas:");

        calcFunctionsBtn.setText("Aprēķini izvēlētajai ziņai");
        calcFunctionsBtn.setEnabled(false);
        calcFunctionsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcFunctionsBtnActionPerformed(evt);
            }
        });

        calcFunctionsBtn1.setText("Izteikšanas pārvaldnieks");
        calcFunctionsBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcFunctionsBtn1ActionPerformed(evt);
            }
        });

        refreshBtn.setText("Atsvaidzināt");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout viewKnPanelLayout = new javax.swing.GroupLayout(viewKnPanel);
        viewKnPanel.setLayout(viewKnPanelLayout);
        viewKnPanelLayout.setHorizontalGroup(
            viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewKnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewKnPanelLayout.createSequentialGroup()
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(headerLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(viewKnPanelLayout.createSequentialGroup()
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField)
                            .addComponent(topicField)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addLinkCB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(viewKnPanelLayout.createSequentialGroup()
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(viewKnPanelLayout.createSequentialGroup()
                                        .addComponent(createBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(linkMeaning))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveConcBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewKnPanelLayout.createSequentialGroup()
                                .addComponent(descBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewKnPanelLayout.createSequentialGroup()
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(conclinkMeaningF, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(concTopicField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(concNameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                    .addComponent(linkConceptCB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane2)
                            .addComponent(jSeparator4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calcFunctionsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewKnPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(manageLinksBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(calcFunctionsBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(131, 131, 131))))
        );
        viewKnPanelLayout.setVerticalGroup(
            viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewKnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewKnPanelLayout.createSequentialGroup()
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(manageLinksBtn))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(calcFunctionsBtn)
                        .addGap(18, 18, 18)
                        .addComponent(calcFunctionsBtn1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(viewKnPanelLayout.createSequentialGroup()
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(createBtn)
                                .addComponent(editBtn))
                            .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(descBtn)
                                .addComponent(deleteBtn)
                                .addComponent(refreshBtn)))
                        .addGap(18, 18, 18)
                        .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(viewKnPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(topicField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addLinkCB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(linkMeaning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(saveBtn)
                                .addGap(43, 43, 43))
                            .addGroup(viewKnPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(linkConceptCB))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(concNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(concTopicField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewKnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(conclinkMeaningF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(saveConcBtn)
                                .addContainerGap(45, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewKnPanelLayout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addComponent(jSeparator3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewKnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(viewKnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void manageLinksBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageLinksBtnActionPerformed
        Integer id = null;
        if(currKnow != null) id = currKnow.getId();
        else if(currConc != null) id = currConc.getId();
        linkFrame = new manageLinks(id, knowList, concList, linkList, chosenPers);
        linkFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        linkFrame.setVisible(true);
        manageLinksBtn.setEnabled(false);

    }//GEN-LAST:event_manageLinksBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        Integer id;
        if(currKnow != null)
        {
            id = currKnow.getId();
            chosenPers.deleteKnow(id);
            knowList.remove(currKnow);
            deleteLinksById(id);
        }
        else if(currConc != null)
        {
            id = currConc.getId();
            concList.remove(currConc);
            deleteLinksById(id);
        }
        writeToPersonFile();
        writeToFile();
        prepareTable();
        deleteBtn.setEnabled(false);
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void saveConcBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConcBtnActionPerformed
        String name = concNameField.getText();
        String topic = concTopicField.getText();
        Integer id = initializeArray();
        //currKnow = knowList.get(knowTable.getSelectedRow());
        Concept newConc = new Concept(id, name, topic);
        concList.add(newConc);
        if(linkConceptCB.isSelected())
        {
            String meaning = conclinkMeaningF.getText();
            linkedKnowledgeMap.put(id, meaning);
            //site lkm bus jamaina lai sasiastitu konceptus
            if(currKnow != null)
            {
                knowList.get(knowTable.getSelectedRow()).setLinkedKnowledge(linkedKnowledgeMap);
                System.out.println("(id: "+ id+ " meaning: " +meaning +") for:");
                System.out.println("currKnow: "+ currKnow.getName());
            }
            else if(currConc != null)
            {
                concList.get(setCurrConc()).setLinkedKnowledge(linkedKnowledgeMap);
                System.out.println("(id: "+ id+ " meaning: " +meaning +") for:");
                System.out.println("currConc: "+ currConc.getName());
            }

        }

        writeToFile();

        cleanFields();
        prepareTable();
    }//GEN-LAST:event_saveConcBtnActionPerformed

    private void concNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_concNameFieldActionPerformed
        //
    }//GEN-LAST:event_concNameFieldActionPerformed

    private void concNameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_concNameFieldMouseClicked
        saveConcBtn.setEnabled(true);
    }//GEN-LAST:event_concNameFieldMouseClicked

    private void linkMeaningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linkMeaningActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_linkMeaningActionPerformed

    private void addLinkCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLinkCBActionPerformed
        if(addLinkCB.isSelected())
        linkMeaning.setEnabled(true);
        else linkMeaning.setEnabled(false);
    }//GEN-LAST:event_addLinkCBActionPerformed

    private void descBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descBtnActionPerformed
        DefaultTableModel tModel = (DefaultTableModel)knowTable.getModel();
        int row = knowTable.getSelectedRow();
        String desc = knowList.get(row).getDescription();
        showDescFrame descLog = new showDescFrame(this,true,desc);
        descLog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        descLog.setVisible(true);
        //Šito japadod tad tai klasei ka parametru ...
        //Integer id = (Integer)tModel.getValueAt(row, 0);
    }//GEN-LAST:event_descBtnActionPerformed

    private void knowTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_knowTableMouseClicked
        cleanFields();
        deleteBtn.setEnabled(true);
        editRow = knowTable.getSelectedRow();
        DefaultTableModel tModel = (DefaultTableModel)knowTable.getModel();
        if(tModel.getValueAt(knowTable.getSelectedRow(), 1) == "Ziņa")
        {
            currConc = null;
            linkConceptCB.setText("Pievienot konceptu atlasītajai ziņai");
            descBtn.setEnabled(true);
            editBtn.setEnabled(true);
            linkConceptCB.setEnabled(true);
            conclinkMeaningF.setEnabled(true);
            calcFunctionsBtn.setEnabled(true);

            if(!knowList.isEmpty())
            {
                currKnow = knowList.get(knowTable.getSelectedRow());
                System.out.println("during KTMC currKnow: "+ currKnow.getName());
            }
            setKnowMap(1);
            System.out.println("after KTMC currKnow: "+ currKnow.getName());
        }
        else
        {
            lockNonConcFeatures();
            System.out.println("lockNonConcFeatures()");
            if(!concList.isEmpty()) setCurrConc();
            setKnowMap(1);
        }
        prepareLinksTable();
    }//GEN-LAST:event_knowTableMouseClicked

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        String name = nameField.getText();
        String topic = topicField.getText();
        String description = descField.getText();
        if(editRow == null)
        {
            Integer id = initializeArray();
            currKnow = new Knowledge(id, name, topic, description);
            knowList.add(currKnow);
            if(linkMeaning.isEnabled())
            {
                personMapEntry(linkMeaning.getText(), id);
            }
        }
        else
        {
            //Integer listId = findListNrById(currKnow.getId(),0); // Sameklejam KnowList(0) Id
            knowList.get(editRow).setName(name);
            knowList.get(editRow).setTopic(topic);
            knowList.get(editRow).setDescription(description);
            if(linkMeaning.isEnabled())
            {
                personMapEntry(linkMeaning.getText(), currKnow.getId());
            }
        }

        writeToFile();

        cleanFields();
        prepareTable();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void descFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_descFieldMouseClicked
        if(descField.isEnabled())
        saveBtn.setEnabled(true);
    }//GEN-LAST:event_descFieldMouseClicked

    private void topicFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topicFieldMouseClicked
        if(topicField.isEnabled())
        saveBtn.setEnabled(true);
    }//GEN-LAST:event_topicFieldMouseClicked

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        //
    }//GEN-LAST:event_nameFieldActionPerformed

    private void nameFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameFieldMouseClicked
        if(nameField.isEnabled())
        saveBtn.setEnabled(true);
    }//GEN-LAST:event_nameFieldMouseClicked

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        //DefaultTableModel tModel = (DefaultTableModel)knowTable.getModel();
        //editRow = knowTable.getSelectedRow();
        nameField.setEnabled(true);
        topicField.setEnabled(true);
        descField.setEnabled(true);
        nameField.setText(currKnow.getName());
        topicField.setText(currKnow.getTopic());
        descField.setText(currKnow.getDescription());
        Integer id = currKnow.getId();
        System.out.println(personKnowMap.containsKey(id));
        if(personKnowMap.containsKey(id))
        {
            if(!addLinkCB.isSelected()) addLinkCB.doClick();
            String meaning = chosenPers.getKnows().get(id);
            linkMeaning.setText(meaning);
        }
        else
        {
            if(addLinkCB.isSelected()) addLinkCB.doClick();
            linkMeaning.setText("");
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void createBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBtnActionPerformed
        editRow = null;
        nameField.setEnabled(true);
        topicField.setEnabled(true);
        descField.setEnabled(true);
        nameField.setText("");
        topicField.setText("");
        descField.setText("");
        File KnowlDir = new File("./knowledgeFiles");
        if(!KnowlDir.exists())
        {
            KnowlDir.mkdir();
        }
    }//GEN-LAST:event_createBtnActionPerformed

    private void calcFunctionsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcFunctionsBtnActionPerformed
        Integer id = null;
        if(currKnow != null) id = currKnow.getId();
        //else if(currConc != null) id = currConc.getId();
        calcFrame = new calculationsFrame(id, chosenPers, knowList, concList, linkList);
        calcFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        calcFrame.setVisible(true);
        
        calcFunctionsBtn.setEnabled(false);
        refreshBtn.setBackground(Color.yellow);
    }//GEN-LAST:event_calcFunctionsBtnActionPerformed

    private void calcFunctionsBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcFunctionsBtn1ActionPerformed
        infManagerFrame = new inferenceManagerForm(chosenPers, knowList, concList);
        infManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        infManagerFrame.setVisible(true);
    }//GEN-LAST:event_calcFunctionsBtn1ActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        initializeArray();
        prepareTable();
        refreshBtn.setBackground(Color.getHSBColor((float)0, (float)0, (float) 0.7));
    }//GEN-LAST:event_refreshBtnActionPerformed
    
    private void personMapEntry(String predicate, Integer id)
    {
        
        FileOutputStream fos = null;
        try {
            personKnowMap.put(id, predicate);
            System.out.println("mapEntry() = (" + predicate + ","+ id +")");
            chosenPers.setKnows(personKnowMap);
            
            File PersFile = new File("./personFiles/"+chosenPers.getName()+"_"+chosenPers.getSurname()+".xml");
            fos = new FileOutputStream(PersFile);
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(chosenPers);
            encoder.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    private Integer initializeArray()
    {
        knowList.clear();
        concList.clear();
        Concept conc = new Concept();
        Knowledge know = new Knowledge();
        if(knowFile.canRead())
        {
            System.out.println("\nZinasanu fails eksiste");
            FileInputStream fis;
            try {
                fis = new FileInputStream(knowFile);
                XMLDecoder decoder = new XMLDecoder(fis);
                //currKnow = (Knowledge) decoder.readObject();
                try
                {
                    while(knowFile != null)
                    {
                       Object temp = new Object();
                       temp = decoder.readObject();
                       if(temp.getClass() == Knowledge.class)
                       {
                            know = (Knowledge) temp;
                            knowList.add(know);
                       }
                       else
                       {
                           conc = (Concept) temp;
                           concList.add(conc);
                       }

                    }
                }
                catch(IndexOutOfBoundsException ex)
                {
                    System.out.println("EOF");
                   //Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex); 
                }
                decoder.close();
                fis.close();
                Integer knowMax;
                Integer concMax;
                if(!knowList.isEmpty()) knowMax = knowList.lastElement().getId();
                else knowMax = 0;
                if(!concList.isEmpty()) concMax = concList.lastElement().getId();
                else concMax = 0;
                if(knowMax > concMax) return knowMax+1;
                else return concMax+1;
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(viewKnowledgeFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 1;
        }
        else
        {
            System.out.println("\nZinasanu fails neeksiste");
            return 1;
        }
        
    }
    
    private Integer setCurrConc()
    {
        DefaultTableModel tModel = (DefaultTableModel)knowTable.getModel();
        Integer id = (Integer)tModel.getValueAt(editRow, 0);
        for(Integer i = 0; i < concList.size(); i++)
        {
            if(concList.get(i).getId().equals(id))
            {
                currConc = concList.get(i);
                System.out.println("after KTMC currConc: "+ currConc.getName());
                return i; // atgriež vietu concList sarakstā
            }
        }
        return -1; //te visparejs error
         
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
    
    private void lockNonConcFeatures()
    {
            descBtn.setEnabled(false);
            editBtn.setEnabled(false);
            calcFunctionsBtn.setEnabled(false);
            linkConceptCB.setText("Saistīt ar atlasīto konceptu");
            descBtn.setEnabled(false);
            currKnow = null;
    }
    
    private void setKnowMap(Integer choice)
    {
        if(choice.equals(0)) // set PersonKnowMap
        {
            if(chosenPers.getKnows() != null) personKnowMap = chosenPers.getKnows();
            else personKnowMap = new HashMap<>();
        }
        else           //set linkedKnowledgeMap
        {
            if(currKnow != null)
            {
                if(currKnow.getLinkedKnowledge() != null) linkedKnowledgeMap = currKnow.getLinkedKnowledge();
                else linkedKnowledgeMap = new HashMap<>();
            }
            else if(currConc != null)
            {
                System.out.println("tomass.tz18004.viewKnowledgeFrame.setKnowMap(currConc)");
                if(currConc.getLinkedKnowledge() != null) linkedKnowledgeMap = currConc.getLinkedKnowledge();
                else linkedKnowledgeMap = new HashMap<>();
            }

        }
    }
    
    private void cleanFields()
    {
        concNameField.setText("");
        concTopicField.setText("");
        conclinkMeaningF.setText("");
        saveConcBtn.setEnabled(false);
        if(linkConceptCB.isSelected()) linkConceptCB.doClick();
        
        saveBtn.setEnabled(false);
        nameField.setEnabled(false);
        topicField.setEnabled(false);
        descField.setEnabled(false);
        linkMeaning.setEnabled(false);
        if(addLinkCB.isSelected()) addLinkCB.doClick();
        editRow = null;
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
            java.util.logging.Logger.getLogger(viewKnowledgeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewKnowledgeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewKnowledgeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewKnowledgeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewKnowledgeFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox addLinkCB;
    private javax.swing.JButton calcFunctionsBtn;
    private javax.swing.JButton calcFunctionsBtn1;
    private javax.swing.JTextField concNameField;
    private javax.swing.JTextField concTopicField;
    private javax.swing.JTextField conclinkMeaningF;
    private javax.swing.JButton createBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton descBtn;
    private javax.swing.JTextArea descField;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable knowTable;
    private javax.swing.JCheckBox linkConceptCB;
    private javax.swing.JTextField linkMeaning;
    private javax.swing.JTable linkTable;
    private javax.swing.JButton manageLinksBtn;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton saveConcBtn;
    private javax.swing.JTextField topicField;
    private javax.swing.JPanel viewKnPanel;
    // End of variables declaration//GEN-END:variables
}
