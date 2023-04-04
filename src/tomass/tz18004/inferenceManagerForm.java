/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tomass.tz18004;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Highlighter;
import javax.swing.text.*;

/**
 *
 * @author Tomass
 */
public class inferenceManagerForm extends javax.swing.JFrame {

    private Vector<Knowledge> knowList;
    private Vector<Concept> concList;
    private Vector<Link> linkList;
    private Vector<ConnData> connectionList;
    private String[] headers;
    private Integer[] subHeaders;
    private Person chosenPers;
    private String[][] matrix;
    private Integer minAmt;
    
    
    public inferenceManagerForm(Person chosenPers, Vector<Knowledge> knowList, Vector<Concept> concList) {
        this.knowList = knowList;
        this.concList = concList;
        this.chosenPers = chosenPers;
        this.linkList = null;
        this.connectionList = null;
        this.headers = new String[knowList.size()+concList.size()+2];
        this.subHeaders = new Integer[knowList.size()+concList.size()+2];
        this.matrix = new String[knowList.size()+concList.size()+1][knowList.size()+concList.size()+2];
        initComponents();
        linkListFill();
        headersFill();
        matrixFill();
        matrixTableFill();
        formatInfTables();
        String information = "Spiediet uz \"*\" savienojuma indikatoru, lai redzētu savienojuma nozīmi!";
        showDescFrame meaningLog = new showDescFrame(this,true, information);
        meaningLog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //meaningLog.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        meaningLog.setVisible(true);
        connSpinner.setValue(1);
        minAmt = 1;
    }
    
    public inferenceManagerForm() {
        initComponents();
    }
    
    private void formatInfTables()
    {
        DefaultTableModel tModel = new DefaultTableModel();
        connTable.setModel(tModel);
        connTable.setEnabled(false);
    }


    private void linkListFill()
    {
        linkList = new Vector<Link>();
        String Predicate;
        Integer id;
        Map<Integer,String> personKnowMap = chosenPers.getKnows();
        if(personKnowMap != null)
        {
            for (Map.Entry<Integer,String> aKnow : personKnowMap.entrySet())
            {
                id = aKnow.getKey();
                Predicate = aKnow.getValue();
                //linkList.add(new Link(0, Predicate, id)); //0 = chosenPers
                linkList.add(new Link(id, Predicate, 0)); //0 = chosenPers
            }
        }
        for(Integer i = 0; i < knowList.size(); i++)
        {
            Map<Integer,String> list =  knowList.get(i).getLinkedKnowledge();
            if(list != null)
            {
                for (Map.Entry<Integer,String> aKnow : list.entrySet())
                {
                    id = aKnow.getKey();
                    Predicate = aKnow.getValue();
                    linkList.add(new Link(knowList.get(i).getId(), Predicate, id));
                }
            }
        }
        for(Integer i = 0; i < concList.size(); i++)
        {
            Map<Integer,String> list =  concList.get(i).getLinkedKnowledge();
            if(list != null)
            {
                for (Map.Entry<Integer,String> aKnow : list.entrySet())
                {
                    id = aKnow.getKey();
                    Predicate = aKnow.getValue();
                    linkList.add(new Link(concList.get(i).getId(), Predicate, id));
                }           
            }
        }
    }
    
    private void headersFill()
    {
        headers[0] = " ";
        headers[1] = chosenPers.getName();
        subHeaders[0] = null;
        subHeaders[1] = 0;
        for(Integer i = 0; i < knowList.size(); i++)
        {
            headers[2+i] = knowList.get(i).getName();
            subHeaders[2+i] = knowList.get(i).getId();
        }
        for(Integer i = 0; i < concList.size(); i++)
        {
            headers[2+knowList.size()+i] = concList.get(i).getName();
            subHeaders[2+knowList.size()+i] = concList.get(i).getId();
        }        
    }
    
    private void matrixFill()
    {
        matrix[0][0] = chosenPers.getName();
        matrix[0][1] = "-";
        Map<Integer,String> knowMap = chosenPers.getKnows();
        Integer id;
        for (Map.Entry<Integer,String> aKnow : knowMap.entrySet())
        {
            id = aKnow.getKey();
            for(int i = 0; i< (headers.length - 1); i++)
            {
                if(subHeaders[i+1].equals(id))
                {
                    matrix[0][i+1] = "*";
                }
                
            }
        }       
        for(Integer i = 0; i < knowList.size(); i++)
        {
            matrix[i+1][0] = knowList.get(i).getName();
            matrix[i+1][i+2] = "-";
            knowMap = knowList.get(i).getLinkedKnowledge();
            if(knowMap != null)
            {
                for (Map.Entry<Integer, String> entry : knowMap.entrySet()) {
                    id = entry.getKey();
                    for(int j = 0; j< (subHeaders.length - 1); j++)
                    {
                        if(subHeaders[j+1].equals(id))
                        {
                            matrix[i+1][j+1] = "*";
                            System.out.println(subHeaders[j+1] + " = " + id);
                        }

                    }                
                }
            }
        }
        for(Integer i = 0; i < concList.size(); i++)
        {
            matrix[i+knowList.size()+1][0] = concList.get(i).getName();
            matrix[i+knowList.size()+1][i+knowList.size()+2] = "-";
            knowMap = concList.get(i).getLinkedKnowledge();
            if(knowMap != null)
            {
                for (Map.Entry<Integer, String> entry : knowMap.entrySet()) {
                    id = entry.getKey();
                    for(int j = 0; j< (subHeaders.length - 1); j++)
                    {
                        if(subHeaders[j+1].equals(id))
                        {
                            matrix[i+knowList.size()+1][j+1] = "*";
                            System.out.println(subHeaders[j+1] + " = " + id);
                        }

                    }                
                }
            }
        }
    }
    
    private void matrixTableFill()
    {
        DefaultTableModel tModel = new DefaultTableModel();
        //tModel.setColumnIdentifiers(headers);
        tModel.setDataVector(matrix, headers);
        knowTable.setModel(tModel);
        knowTable.setRowHeight(25);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setBackground(Color.getHSBColor((float)0, (float)0, (float) 0.87));
        //render.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        //} new Highlighter(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        knowTable.getColumnModel().getColumn(0).setCellRenderer(render);
        knowTable.setDefaultEditor(Object.class, null);
    }
    
    private void connectionInference()
    {
        connectionList = new Vector<ConnData>();
        Integer compObj, compSubj, compObj2, compSubj2;
        for(Integer i = 0; i < linkList.size(); i++)
        {
            compSubj = linkList.get(i).getSubject();
            compObj = linkList.get(i).getObject();
            for(Integer j = i+1; j < linkList.size(); j++)
            {
                if(compObj.equals(linkList.get(j).getObject()))
                {
                    provisionConnData(compSubj, linkList.get(j).getSubject());
                }
            }
        }
        for(Integer i = 0; i < linkList.size(); i++)
        {
            compSubj = linkList.get(i).getSubject();
            compObj = linkList.get(i).getObject();
            System.out.println("--------------------------------------------------------------");
            System.out.println("LinkList [" + i +"] = {" + compSubj + " -> " + compObj + "}");
            System.out.println("--------------------------------------------------------------");
            for(Integer j = i+1; j < linkList.size(); j++)
            {
                compObj2 = linkList.get(j).getObject();
                compSubj2 = linkList.get(j).getSubject();
                System.out.println("Compare to LinkList [" + j +"] = {" + compSubj2 + " -> " + compObj2 + "}");
                for(Integer k = 0; k < connectionList.size(); k++)
                {
                    Integer k1,k2;
                    k1 = connectionList.get(k).getKnow1();
                    k2 = connectionList.get(k).getKnow2();
                    if(k1.equals(compSubj) && k2.equals(compSubj2))
                    {
                        System.out.println("[ Connection Data for (" + k1 +") & (" + k2 +") exists]");
                        if(compObj.equals(compObj2))
                        {
                            System.out.println("°°°°°°°°°°Matching entry°°°°°°°°°°°°");
                            System.out.println("°°°°°°°Both (" + compSubj +") & (" + compSubj2 +") -> (" + compObj + ")°°°°°°°°");
                            System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");
                            connectionList.get(k).getMatchConn().add(compObj);
                        }
                        else
                        {
                            System.out.println("********Differing Entry**************");
                            if(!connectionList.get(k).getDiffConn().contains(compObj))
                                //if(!compObj.equals(compSubj) && !compObj.equals(compSubj2))
                                if(!compObj.equals(compSubj))
                                {
                                    System.out.println("******* Saving 1.(" + compSubj +") -> (" + compObj +") ***********");
                                    connectionList.get(k).getDiffConn().add(compObj);
                                }
                            if(!connectionList.get(k).getDiffConn().contains(compObj2))
                                //if(!compObj2.equals(compSubj) && !compObj.equals(compSubj2))
                                if(!compObj2.equals(compSubj))
                                {
                                    System.out.println("******* Saving 2.(" + compSubj2 +") -> (" + compObj2 +") ***********");
                                    connectionList.get(k).getDiffConn().add(compObj2);
                                    // maybe site ari japievieno compObj2, jo tas ar atskiras
                                }
                            System.out.println("*****************************************");
                        }
                        System.out.println("break.");
                        break;
                    }
                }
            }            
        }
        connDataPostCleanup();
        for(Integer i = 0; i < connectionList.size(); i++)
        {
            System.out.println("For: " + connectionList.get(i).getKnow1() + " / " + connectionList.get(i).getKnow2());
            if(!connectionList.get(i).getMatchConn().isEmpty())
            {
            System.out.println("MatchConn: " + connectionList.get(i).getMatchConn());
            };
            if(!connectionList.get(i).getDiffConn().isEmpty())
            {
            System.out.println("DiffConn: " + connectionList.get(i).getDiffConn());
            };
        }
        
    }
    
    private void provisionConnData(Integer know1, Integer know2)
    {
        if(connectionList == null)
        {
            connectionList = new Vector<ConnData>();
        }
        if(!(know1.equals(know2)))
        {
            Integer k1,k2;
            for(Integer i = 0; i < connectionList.size(); i++)
            {
                k1 = connectionList.get(i).getKnow1();
                k2 = connectionList.get(i).getKnow2();
                if(know1.equals(k1) && know2.equals(k2)) return;
            }
            ConnData initD = new ConnData(know1, know2);
            connectionList.add(initD);
        }
    }
    
    private void connDataPostCleanup()
    {
        Vector<Integer> matchList;
        Vector<Integer> diffList;
        for(Integer i = 0; i < connectionList.size(); i++)
        {
            matchList = connectionList.get(i).getMatchConn();
            diffList = connectionList.get(i).getDiffConn();
            for(Integer j = 0; j < matchList.size(); j++)
            {
                if(diffList.contains(matchList.get(j))) diffList.remove(matchList.get(j));
            }
        }
    }
    
    private String knowNameById(Integer id)
    {
        if(id.equals(0)) return (chosenPers.getName() + " (Person)");
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
    
    private Vector<String> IdVectorToString(Vector<Integer> idList)
    {
        Vector<String> nameList = new Vector<String>();
        for(Integer i = 0; i < idList.size(); i++)
        {
            nameList.add(knowNameById(idList.get(i)));
        }
        return nameList;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        knowTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        inferConnBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        connTable = new javax.swing.JTable();
        infoLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ConnTextArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        connAdviceTextArea = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        connSpinner = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1444, 820));

        jPanel1.setPreferredSize(new java.awt.Dimension(1444, 812));

        knowTable.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
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
        knowTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                knowTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(knowTable);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        jLabel1.setText("Uzskatāma savienojumu tabula, manuālai izteikšanai");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        jLabel2.setText("Automātiska izteikšana, apkopojot ziņu kopīgos savienojumus");

        inferConnBtn.setText("Izteikt");
        inferConnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inferConnBtnActionPerformed(evt);
            }
        });

        connTable.setModel(new javax.swing.table.DefaultTableModel(
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
        connTable.setEnabled(false);
        connTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(connTable);

        infoLabel.setText("(Spiediet uz ieraksta, lai apskatītu visu informāciju)");
        infoLabel.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setText("Ziņas ar kopīgajiem savienojumiem:");

        ConnTextArea.setColumns(20);
        ConnTextArea.setRows(5);
        ConnTextArea.setEnabled(false);
        jScrollPane3.setViewportView(ConnTextArea);

        jLabel4.setText("Savienojumu informācija:");

        connAdviceTextArea.setColumns(20);
        connAdviceTextArea.setRows(5);
        connAdviceTextArea.setEnabled(false);
        jScrollPane4.setViewportView(connAdviceTextArea);

        jLabel5.setText("Izteiktie ieteikumi par zināšanām:");

        jLabel6.setText("Min. savienojumu skaits:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(connSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inferConnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(connSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(inferConnBtn)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1431, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connTableMouseClicked
        Integer index = connTable.getSelectedRow();
        Vector<String> matchConn = IdVectorToString(connectionList.get(index).getMatchConn());
        Vector<String> diffConn = IdVectorToString(connectionList.get(index).getDiffConn());
        ConnTextArea.setText("Kopīgie savienojumi: \n" + matchConn + "\nAtšķirīgie savienojumi: \n"
            + diffConn);
        ConnTextArea.setEnabled(true);
        ConnTextArea.setLineWrap(true);
        connAdviceTextArea.setEnabled(true);
        connAdviceTextArea.setLineWrap(true);
        Vector<Integer> diffIdList = connectionList.get(index).getDiffConn();
        Vector<String> connAdvice = new Vector<String>();
        for(Integer i = 0; i < diffIdList.size(); i++)
        {
            Integer from = null;
            Integer to = diffIdList.get(i);
            if(!to.equals(connectionList.get(index).getKnow1()) && !to.equals(connectionList.get(index).getKnow2()))
            {
                for(Integer j = 0; j < linkList.size(); j++)
                {
                    if(linkList.get(j).getSubject() == connectionList.get(index).getKnow1() && linkList.get(j).getObject() == to)
                    {from = connectionList.get(index).getKnow2(); break;}
                    else if(linkList.get(j).getSubject() == connectionList.get(index).getKnow2() && linkList.get(j).getObject() == to)
                    {from = connectionList.get(index).getKnow1(); break;}
                }
                if(to.equals(0))
                {
                    Integer temp = from; from = to; to = temp;
                }
                connAdvice.add("No " + knowNameById(from) + " uz " + knowNameById(to));
            }
        }
        connAdviceTextArea.setText("Iespējams, trūkst savienojumi: \n" + connAdvice);
    }//GEN-LAST:event_connTableMouseClicked

    private void inferConnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inferConnBtnActionPerformed
        minAmt = (Integer) connSpinner.getValue();
        Integer amt = null;
        if(linkList != null)
        {
            connectionInference();
        }
        infoLabel.setEnabled(true);
        connTable.setEnabled(true);
        DefaultTableModel tModel = new DefaultTableModel();
        tModel.setColumnIdentifiers(new Object[]{"Ziņa 1", "Ziņa 2", "Kopīgie savienojumi"});
        for(Integer i = 0; i < connectionList.size(); i++)
        {
            amt = connectionList.get(i).getMatchConn().size();
            if(amt >= minAmt)
            {
                String know1 = knowNameById(connectionList.get(i).getKnow1());
                String know2 = knowNameById(connectionList.get(i).getKnow2());
                tModel.addRow(new Object[]{know1, know2, amt});
            }
        }
        connTable.setModel(tModel);
        connTable.setDefaultEditor(Object.class, null);
    }//GEN-LAST:event_inferConnBtnActionPerformed

    private void knowTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_knowTableMouseClicked
        Integer row, column;
        column = knowTable.getSelectedColumn();
        row = knowTable.getSelectedRow();
        if((row != null) && (column != null))
        {
            if(knowTable.getValueAt(row, column) == "*")
            {
                //System.out.println(column + " * " + row);
                Integer subject = subHeaders[row+1];
                Integer object = subHeaders[column];
                String meaning = null;
                for(Integer i = 0; i < linkList.size(); i++)
                {
                    if(linkList.get(i).getSubject() == subject && linkList.get(i).getObject() == object)
                    {
                        meaning = linkList.get(i).getMeaning();
                        showDescFrame meaningLog = new showDescFrame(this,true, meaning);
                        meaningLog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        meaningLog.setVisible(true);
                    }
                }

            }
        }
    }//GEN-LAST:event_knowTableMouseClicked

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
            java.util.logging.Logger.getLogger(inferenceManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inferenceManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inferenceManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inferenceManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inferenceManagerForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ConnTextArea;
    private javax.swing.JTextArea connAdviceTextArea;
    private javax.swing.JSpinner connSpinner;
    private javax.swing.JTable connTable;
    private javax.swing.JButton inferConnBtn;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable knowTable;
    // End of variables declaration//GEN-END:variables
}
