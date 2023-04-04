/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tomass.tz18004;

import java.util.Vector;

/**
 *
 * @author Tomass
 */
public class ConnData {
    private Integer know1;
    private Integer know2;
    private Vector<Integer> matchConn;
    private Vector<Integer> diffConn;

    public ConnData(Integer know1, Integer know2) {
        this.know1 = know1;
        this.know2 = know2;
        matchConn = new Vector<Integer>();
        diffConn = new Vector<Integer>();
    }

    public Integer getKnow1() {
        return know1;
    }

    public Integer getKnow2() {
        return know2;
    }

    public Vector<Integer> getMatchConn() {
        return matchConn;
    }

    public Vector<Integer> getDiffConn() {
        return diffConn;
    }
    
    
    
    
}
