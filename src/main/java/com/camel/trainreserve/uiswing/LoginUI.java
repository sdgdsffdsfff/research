/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.trainreserve.uiswing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * @author dengqb
 * @date 2014年12月29日
 */
public class LoginUI extends JFrame implements ActionListener {
    private JLabel lblUserName;
    private JLabel lblPassword;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JButton btnOk;
    private JButton btnExit;
    
    public LoginUI(){
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        lblUserName = new JLabel("用户名：");
        txtUserName = new JTextField(12);
        
        p1.add(lblUserName,BorderLayout.WEST);
        p1.add(txtUserName,BorderLayout.EAST);
        
        this.add(p1,BorderLayout.NORTH);
        this.setLocation(400,300);
        this.setSize(300,100);
        this.setTitle("登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    public static void main(String[] args){
        new LoginUI();
    }

}
