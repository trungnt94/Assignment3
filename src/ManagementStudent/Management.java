/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagementStudent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;


public class Management extends JFrame {

    private JLabel lblID, lblName, lblEmail, lblPhone, lblStudentId;
    private JTextField txtID, txtName, txtEmail, txtPhone, txtStudentId;       
    private JButton btnEdit, btnDelete, btnSearch, btnCreate, btnList;
    private JPanel panel, createForm;    
    private DefaultTableModel tableModel;
    private JTable studentTable;
    private JScrollPane scrollPane;

    public Management() {
        setSize(555, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý Sinh Viên");

        this.lblStudentId = new JLabel();
        this.lblStudentId.setBounds(50, 25, 80, 20);
        this.lblStudentId.setText("Mã sinh viên");

        this.txtStudentId = new JTextField();
        this.txtStudentId.setBounds(150, 25, 150, 20);

        this.btnSearch = new JButton();
        this.btnSearch.setBounds(330, 25, 100, 20);
        this.btnSearch.setText("Tìm kiếm");
        
        this.btnSearch.addActionListener((ActionEvent e) -> {
            try {
                
                Search();
            } catch (SQLException ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.btnList = new JButton();
        this.btnList.setBounds(50,520, 100, 20);
        this.btnList.setText("Danh sách");
        this.btnList.addActionListener((ActionEvent e) -> {
            try {
                queryInfo(tableModel);
            } catch (Exception ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.btnCreate = new JButton();
        this.btnCreate.setBounds(170,520, 100, 20);
        this.btnCreate.setText("Tạo mới");
        this.btnCreate.addActionListener((ActionEvent e) -> {
            try {
                insertInfo();
            } catch (Exception ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
        
        this.btnEdit = new JButton("Sửa");
        this.btnEdit.setBounds(290, 520, 100, 20);        
        this.btnEdit.addActionListener((ActionEvent e) -> {
            try {
                editInfo();
            } catch (SQLException ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.btnDelete = new JButton("Xóa");
        this.btnDelete.setBounds(410, 520, 100, 20);       
        this.btnDelete.addActionListener((ActionEvent e) -> {
            try {
                deleteInfo();
            } catch (SQLException ex) {
                Logger.getLogger(Management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.lblID = new JLabel();
        this.lblID.setBounds(50, 25, 80, 20);
        this.lblID.setText("ID");
        this.txtID = new JTextField();
        this.txtID.setBounds(150, 25, 150, 20);
        
        this.lblName = new JLabel();
        this.lblName.setBounds(50, 55, 80, 20);
        this.lblName.setText("Tên");
        this.txtName = new JTextField();
        this.txtName.setBounds(150, 55, 150, 20);
        
        this.lblEmail = new JLabel();
        this.lblEmail.setBounds(50, 85, 80, 20);
        this.lblEmail.setText("Email");
        this.txtEmail = new JTextField();
        this.txtEmail.setBounds(150, 85, 150, 20);

        this.lblPhone = new JLabel();
        this.lblPhone.setBounds(50, 115, 80, 20);
        this.lblPhone.setText("Điện thoại");
        this.txtPhone = new JTextField();
        this.txtPhone.setBounds(150, 115, 150, 20);
       
        this.studentTable = new JTable();
        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("ID");
        this.tableModel.addColumn("Name");
        this.tableModel.addColumn("Email");
        this.tableModel.addColumn("Phone");
        this.studentTable.setModel(this.tableModel);
        this.scrollPane = new JScrollPane(this.studentTable);
        this.scrollPane.setBounds(20, 70, 500, 220);        
        this.add(this.scrollPane);         
        scrollPane.setVisible(true);
        
        this.panel = new JPanel();
        this.panel.setBounds(80, 320, 350, 170);
        this.panel.setBorder(BorderFactory.createBevelBorder(1));
        panel.add(lblID);
        panel.add(txtID);
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblPhone);
        panel.add(txtPhone);        
        panel.setLayout(null);
        panel.setVisible(true);
                      
        add(lblStudentId);
        add(txtStudentId);
        add(btnSearch);
        add(btnList);
        add(btnCreate);
        add(btnEdit);
        add(btnDelete);
        add(panel);
        setLayout(null);
    }

    public void Search() throws SQLException {
        try{
            String str = "SELECT * FROM student WHERE ID = " + txtStudentId.getText();
            Connection connect = JavaDBConnection.getConnection();
            Statement stt = connect.createStatement();
            ResultSet rs = stt.executeQuery(str);
            if(rs.isBeforeFirst()){
                while(rs.next()){
                    txtName.setText(rs.getString("Name"));
                    txtEmail.setText(rs.getString("Email"));
                    txtPhone.setText(rs.getString("Phone"));
                }
            } else {
                txtName.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
                JOptionPane.showMessageDialog(null, "Error");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void queryInfo(DefaultTableModel tableModel) throws SQLException {
        String ID = txtID.getText();
        String search = "SELECT * FROM student";
        Connection connect = JavaDBConnection.getConnection();
        Statement stt = connect.createStatement();
        ResultSet rs = stt.executeQuery(search);         
            while (rs.next()) {
                    String id   = rs.getString("ID");
                    String name = rs.getString("Name");             
                    String email = rs.getString("Email");
                    String phone = rs.getString("Phone");
                    String[] values = {id,name,email,phone};
                    tableModel.addRow(values);
            }        
    }
    
    public  void insertInfo() throws SQLException {
        if (txtID.getText() == null) {
            JOptionPane.showConfirmDialog(null, "Error");
        } else {
            String insert = "INSERT INTO student VALUES ('"
                    + txtID.getText()    + "',"
                    + txtName.getText()  + "',"
                    + txtEmail.getText() + "',"
                    + txtPhone.getText() + "')";
            Connection connect = JavaDBConnection.getConnection();
            Statement stt = connect.createStatement();
            boolean rs = stt.execute(insert);
            if (rs == true) {
                this.txtName.setText("");
                this.txtEmail.setText("");
                this.txtPhone.setText("");
                JOptionPane.showMessageDialog(new JFrame(), "Thông tin đã được thêm.");
            }            
        }
        
    }    

    public void editInfo() throws SQLException {
        if (txtID.getText() == null) {
            JOptionPane.showConfirmDialog(null, "Error");
        } else {
            String update = "UPDATE student SET Name = '"
                    + txtName.getText()  + "', Email = '"
                    + txtEmail.getText() + "', Phone = '"
                    + txtPhone.getText() + "' WHERE ID = '" + txtStudentId.getText()+"'";           
            Connection connect = JavaDBConnection.getConnection();
            Statement stt = connect.createStatement();
            int rs = stt.executeUpdate(update);
            if (rs == 1){   
                this.txtName.setText("");
                this.txtEmail.setText("");
                this.txtPhone.setText("");
                JOptionPane.showMessageDialog(new JFrame(), "Thông tin đã được sửa.");
            }
            
        }
    }

    public void deleteInfo() throws SQLException{
        if (txtID.getText() == null) {
            JOptionPane.showConfirmDialog(null, "Error");
        } else {
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có chắc muốn xóa?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){ 
                String delete = "DELETE student WHERE ID = '" + txtStudentId.getText()+"'";
                Connection connect = JavaDBConnection.getConnection();
                Statement stt = connect.createStatement();
                boolean rs = stt.execute(delete);
                if(rs == true) {
                this.txtName.setText("");
                this.txtEmail.setText("");
                this.txtPhone.setText("");
                JOptionPane.showMessageDialog(new JFrame(), "Thông tin đã được xóa.");
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Management je = new Management();
        je.setVisible(true);
    }
    

    }
