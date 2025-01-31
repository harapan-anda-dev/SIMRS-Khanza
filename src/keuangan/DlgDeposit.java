/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPemberianInfus.java
 *
 * Created on Jun 6, 2010, 10:59:33 PM
 */

package keuangan;

import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dosen
 */
public class DlgDeposit extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String now=dateFormat.format(date);
    private String filter_tipe_bayar = "";
    private int i=0, id=0;
    private PreparedStatement ps;
    private ResultSet rs;

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgDeposit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);  

        Object[] row={"No.Rawat","Pasien","Diterima dari","Tanggal","Besar Deposit","Petugas","Tipe Bayar", "Keterangan", "id"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(300);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(300);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(300);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        kdptg.setDocument(new batasInput((byte)20).getKata(kdptg));
        BesarDeposit.setDocument(new batasInput((byte)15).getOnlyAngka2(BesarDeposit));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        } 
        ChkInput.setSelected(false);
        isForm();
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    TPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                kdptg.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        jam();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnKwitansiDeposit = new javax.swing.JMenuItem();
        MnKwitansiTotalSelisihRanap = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label12 = new widget.Label();
        filterTipeBayar = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        DTPTgl = new widget.Tanggal();
        jLabel10 = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        TPetugas = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        jLabel15 = new widget.Label();
        BesarDeposit = new widget.TextBox();
        ChkJln = new widget.CekBox();
        tipeBayar = new widget.ComboBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        diterimaDari = new widget.TextBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        keteranganDeposit = new javax.swing.JTextArea();
        jLabel11 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKwitansiDeposit.setBackground(new java.awt.Color(255, 255, 254));
        MnKwitansiDeposit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKwitansiDeposit.setForeground(java.awt.Color.darkGray);
        MnKwitansiDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKwitansiDeposit.setText("Kwitansi Deposit");
        MnKwitansiDeposit.setName("MnKwitansiDeposit"); // NOI18N
        MnKwitansiDeposit.setPreferredSize(new java.awt.Dimension(250, 28));
        MnKwitansiDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKwitansiDepositActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKwitansiDeposit);

        MnKwitansiTotalSelisihRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnKwitansiTotalSelisihRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKwitansiTotalSelisihRanap.setForeground(java.awt.Color.darkGray);
        MnKwitansiTotalSelisihRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKwitansiTotalSelisihRanap.setText("Kwitansi Total Selisih Ranap");
        MnKwitansiTotalSelisihRanap.setName("MnKwitansiTotalSelisihRanap"); // NOI18N
        MnKwitansiTotalSelisihRanap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnKwitansiTotalSelisihRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKwitansiTotalSelisihRanapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKwitansiTotalSelisihRanap);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Deposit/Titipan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-04-2020" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-04-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        label12.setText("Tipe Bayar :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(label12);

        filterTipeBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Uang Muka Perawatan", "Uang Muka Obat RI", "Selisih Rawat Inap", "Tanpa Pengembalian" }));
        filterTipeBayar.setName("filterTipeBayar"); // NOI18N
        filterTipeBayar.setPreferredSize(new java.awt.Dimension(160, 23));
        filterTipeBayar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterTipeBayarItemStateChanged(evt);
            }
        });
        filterTipeBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                filterTipeBayarKeyPressed(evt);
            }
        });
        panelGlass9.add(filterTipeBayar);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(160, 107));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(78, 12, 125, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(205, 12, 200, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-04-2020" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(78, 42, 95, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 42, 75, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(180, 42, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(245, 42, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(310, 42, 62, 23);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 72, 70, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        FormInput.add(kdptg);
        kdptg.setBounds(74, 72, 130, 23);

        TPetugas.setEditable(false);
        TPetugas.setBackground(new java.awt.Color(202, 202, 202));
        TPetugas.setHighlighter(null);
        TPetugas.setName("TPetugas"); // NOI18N
        FormInput.add(TPetugas);
        TPetugas.setBounds(206, 72, 200, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(410, 72, 28, 23);

        jLabel15.setText("Tipe Pembayaran :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(437, 72, 140, 23);

        BesarDeposit.setText("0");
        BesarDeposit.setFocusTraversalPolicyProvider(true);
        BesarDeposit.setName("BesarDeposit"); // NOI18N
        BesarDeposit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BesarDepositKeyPressed(evt);
            }
        });
        FormInput.add(BesarDeposit);
        BesarDeposit.setBounds(580, 42, 190, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        FormInput.add(ChkJln);
        ChkJln.setBounds(375, 42, 23, 23);

        tipeBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uang Muka Perawatan", "Uang Muka Obat RI", "Selisih Rawat Inap" }));
        tipeBayar.setName("tipeBayar"); // NOI18N
        tipeBayar.setPreferredSize(new java.awt.Dimension(130, 23));
        tipeBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipeBayarActionPerformed(evt);
            }
        });
        tipeBayar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tipeBayarPropertyChange(evt);
            }
        });
        FormInput.add(tipeBayar);
        tipeBayar.setBounds(580, 72, 130, 23);

        jLabel16.setText("Besar Deposit/Titipan : Rp.");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(437, 42, 140, 23);

        jLabel17.setText("Diterima dari :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(437, 12, 140, 23);

        diterimaDari.setFocusTraversalPolicyProvider(true);
        diterimaDari.setName("diterimaDari"); // NOI18N
        diterimaDari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diterimaDariKeyPressed(evt);
            }
        });
        FormInput.add(diterimaDari);
        diterimaDari.setBounds(580, 12, 190, 23);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        keteranganDeposit.setColumns(20);
        keteranganDeposit.setRows(5);
        keteranganDeposit.setName("keteranganDeposit"); // NOI18N
        jScrollPane1.setViewportView(keteranganDeposit);

        FormInput.add(jScrollPane1);
        jScrollPane1.setBounds(860, 10, 244, 84);

        jLabel11.setText("Keterangan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(780, 10, 75, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            TCari.requestFocus();
        }else{            
            Valid.pindah(evt,kdptg,DTPTgl);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,TNoRw,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Maaf, Pasien tidak boleh kosong...!!!");
            TNoRw.requestFocus();
        }else if(kdptg.getText().trim().equals("")||TPetugas.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Maaf, Petugas tidak boleh kosong...!!!");
            kdptg.requestFocus();
        }else if(diterimaDari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Maaf, Diterima dari tidak boleh kosong...!!!");
            diterimaDari.requestFocus();
        }else if(BesarDeposit.getText().equals("")||BesarDeposit.getText().equals("0")){
            Valid.textKosong(BesarDeposit,"Deposit");
        }else if(tipeBayar.getSelectedItem().equals("")){
            Valid.textKosong(tipeBayar,"Tipe Bayar");
        }else if(keteranganDeposit.getText().equals("")){
            Valid.textKosong(keteranganDeposit,"Keterangan");
        }else{
            if(Sequel.menyimpantf("deposit(no_rawat, diterima_dari, tgl_deposit, besar_deposit, nip, tipe_bayar, keterangan)","?,?,?,?,?,?,?","Deposit",7,new String[]{
                TNoRw.getText(), diterimaDari.getText(), Valid.SetTgl(DTPTgl.getSelectedItem()+"")+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),
                BesarDeposit.getText(), kdptg.getText(), tipeBayar.getSelectedItem().toString(), keteranganDeposit.getText().toString().trim()})==true){
                tampil();
                BtnBatalActionPerformed(evt);
            }else{
                kdptg.requestFocus();
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TPasien,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
       ChkInput.setSelected(true);
        isForm(); 
        
        BesarDeposit.setText("0");
        kdptg.setText("");
        TPetugas.setText("");
        diterimaDari.setText("");
        keteranganDeposit.setText("");
        cmbJam.setSelectedItem(now.substring(11,13));
        cmbMnt.setSelectedItem(now.substring(14,16));
        cmbDtk.setSelectedItem(now.substring(17,19));
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(rootPane,"Maaf, data sudah habis...!!!!");
            DTPTgl.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            try{
                Sequel.queryu2("delete from deposit where no_rawat=? and tgl_deposit=?",2,new String[]{
                    tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()
                });
                tampil();
                JOptionPane.showMessageDialog(rootPane,"Alhamdulillah, Berhasil hapus data!");
                BtnBatalActionPerformed(evt);
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(rootPane,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        if(filterTipeBayar.getSelectedItem().toString().equals("Semua")){
            filter_tipe_bayar = "";
        }else if(filterTipeBayar.getSelectedItem().toString().equals("Tanpa Pengembalian")){
            filter_tipe_bayar = " deposit.besar_deposit >= 0 and ";
        }else{
            filter_tipe_bayar = " deposit.tipe_bayar like '%" + filterTipeBayar.getSelectedItem().toString() + "%' and ";
        }
        
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptDeposit.jasper","report","::[ Data Deposit/Titipan Pasien ]::",
                "select deposit.no_rawat,pasien.nm_pasien as pasien, "
                +"deposit.tgl_deposit, "
                + "deposit.besar_deposit,"
                + "petugas.nama as petugas, "
                + "deposit.tipe_bayar, "
                + "deposit.keterangan "
                +"from deposit "
                + "inner join reg_periksa on deposit.no_rawat=reg_periksa.no_rawat "
                + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                + "inner join petugas on deposit.nip=petugas.nip "
                +"where "+ filter_tipe_bayar +" deposit.tgl_deposit between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00"+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59"+"' and deposit.no_rawat like '%"+TCari.getText().trim()+"%' or "
                +filter_tipe_bayar + " deposit.tgl_deposit between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00"+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59"+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "
                +filter_tipe_bayar + " deposit.tgl_deposit between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00"+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59"+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or " 
                +filter_tipe_bayar + " deposit.tgl_deposit between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00"+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59"+"' and deposit.nip like '%"+TCari.getText().trim()+"%' or "
                +filter_tipe_bayar + " deposit.tgl_deposit between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00"+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59"+"' and petugas.nama like '%"+TCari.getText().trim()+"%' or "
                +filter_tipe_bayar + " deposit.tgl_deposit between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00"+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59"+"' and deposit.keterangan like '%"+TCari.getText().trim()+"%' "
                +"order by deposit.tgl_deposit desc",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,BesarDeposit);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",TPetugas,kdptg.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekPetugasActionPerformed(null);
        }else{
            Valid.pindah(evt,BesarDeposit,BtnSimpan);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        petugas.isCek();
        petugas.emptTeks();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugasActionPerformed

    private void BesarDepositKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BesarDepositKeyPressed
        Valid.pindah(evt,cmbDtk,kdptg);
    }//GEN-LAST:event_BesarDepositKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnKwitansiDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKwitansiDepositActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Valid.panggilUrl("billing/LaporanBilling6.php?norawat="+TNoRw.getText().replaceAll(" ","_")+"&pasien="+TPasien.getText().replaceAll(" ","_")+"&tanggal="+DTPTgl.getSelectedItem()
                    +"&deposit="+BesarDeposit.getText()+"&kd_petugas="+kdptg.getText().replaceAll(" ","_")+"&petugas="+TPetugas.getText().replaceAll(" ","_")
                    +"&diterima_dari="+diterimaDari.getText().replaceAll(" ","_")+"&tipe_bayar="+tipeBayar.getSelectedItem().toString().replaceAll(" ","_")+"&keterangan="+keteranganDeposit.getText().toString().replaceAll(" ","_"));
        }
    }//GEN-LAST:event_MnKwitansiDepositActionPerformed

    private void diterimaDariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diterimaDariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_diterimaDariKeyPressed

    private void filterTipeBayarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterTipeBayarItemStateChanged
        filter_tipe_bayar = filterTipeBayar.getSelectedItem().toString().trim();
        if(filter_tipe_bayar.equals("Semua")){
            filter_tipe_bayar = "";
        }
    }//GEN-LAST:event_filterTipeBayarItemStateChanged

    private void filterTipeBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterTipeBayarKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
    }//GEN-LAST:event_filterTipeBayarKeyPressed

    private void tipeBayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tipeBayarPropertyChange
        keteranganDeposit.setText("");
        keteranganDeposit.setText(tipeBayar.getSelectedItem().toString());
    }//GEN-LAST:event_tipeBayarPropertyChange

    private void tipeBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipeBayarActionPerformed
        keteranganDeposit.setText("");
        keteranganDeposit.setText(tipeBayar.getSelectedItem().toString());
    }//GEN-LAST:event_tipeBayarActionPerformed

    private void MnKwitansiTotalSelisihRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKwitansiTotalSelisihRanapActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Valid.panggilUrl("billing/LaporanBilling14.php?norawat="+TNoRw.getText().replaceAll(" ","_")+"&pasien="+TPasien.getText().replaceAll(" ","_")
                    +"&petugas="+TPetugas.getText().replaceAll(" ","_"));
        }
    }//GEN-LAST:event_MnKwitansiTotalSelisihRanapActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(id == 0){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, Belum ada data yang dipilih!");
            TNoRw.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, No Rawat tidak boleh kosong guys!");
            TNoRw.requestFocus();
        }else if(kdptg.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, Pertugas tidak boleh kosong guys!");
            kdptg.requestFocus();
        }else if(diterimaDari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, Diterima dari tidak boleh kosong guys!");
            diterimaDari.requestFocus();
        }else if(Integer.valueOf(BesarDeposit.getText()) == 0){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, Besar deposit tidak boleh 0 guys!");
            BesarDeposit.requestFocus();
        }else if(keteranganDeposit.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane,"Astaghfirullah, Keterangan deposit tidak boleh kosong guys!");
            keteranganDeposit.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.mengedit("deposit","id=" + id,"diterima_dari=?, besar_deposit=?, nip=?, tipe_bayar=?, keterangan=?",5,new String[]{
                    diterimaDari.getText(), BesarDeposit.getText(), kdptg.getText(),
                    tipeBayar.getSelectedItem().toString(), keteranganDeposit.getText()
                });
                tampil();
                kosongkanForm();
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void kosongkanForm(){
        BesarDeposit.setText("0");
        kdptg.setText("");
        TPetugas.setText("");
        diterimaDari.setText("");
        keteranganDeposit.setText("");
        cmbJam.setSelectedItem(now.substring(11,13));
        cmbMnt.setSelectedItem(now.substring(14,16));
        cmbDtk.setSelectedItem(now.substring(17,19));
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }
    
    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDeposit dialog = new DlgDeposit(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox BesarDeposit;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnKwitansiDeposit;
    private javax.swing.JMenuItem MnKwitansiTotalSelisihRanap;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPetugas;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.TextBox diterimaDari;
    private widget.ComboBox filterTipeBayar;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private widget.TextBox kdptg;
    private javax.swing.JTextArea keteranganDeposit;
    private widget.Label label12;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    private widget.ComboBox tipeBayar;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public void tampil() {        
        Valid.tabelKosong(tabMode);
        if(filterTipeBayar.getSelectedItem().toString().equals("Semua")){
            filter_tipe_bayar = "";
        }else{
            filter_tipe_bayar = " deposit.tipe_bayar like '%" + filterTipeBayar.getSelectedItem().toString() + "%' and ";
        }
        
        try{       
            ps=koneksi.prepareStatement("select deposit.id as id, deposit.no_rawat, "
                    + "concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as pasien, "
                    + "deposit.tgl_deposit, "
                    + "deposit.besar_deposit, "
                    + "concat(deposit.nip,' ',petugas.nama) as petugas, "
                    + "deposit.tipe_bayar, "
                    + "deposit.diterima_dari, "
                    + "deposit.keterangan "
                    + "from deposit "
                    + "inner join reg_periksa on deposit.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join petugas on deposit.nip=petugas.nip " 
                    +"where "+filter_tipe_bayar+" deposit.tgl_deposit between ? and ? and deposit.no_rawat like ? or "
                    +filter_tipe_bayar + " deposit.tgl_deposit between ? and ? and reg_periksa.no_rkm_medis like ? or "
                    +filter_tipe_bayar + " deposit.tgl_deposit between ? and ? and pasien.nm_pasien like ? or " 
                    +filter_tipe_bayar + " deposit.tgl_deposit between ? and ? and deposit.nip like ? or "
                    +filter_tipe_bayar + " deposit.tgl_deposit between ? and ? and petugas.nama like ? or "
                    +filter_tipe_bayar + " deposit.tgl_deposit between ? and ? and deposit.keterangan like ? "
                    + "order by deposit.tgl_deposit desc");
            
            ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(9,"%"+TCari.getText().trim()+"%");
            ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(12,"%"+TCari.getText().trim()+"%");
            ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(15,"%"+TCari.getText().trim()+"%");
            ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
            ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
            ps.setString(18,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new String[]{rs.getString("no_rawat"),
                               rs.getString("pasien"),
                               rs.getString("diterima_dari"),
                               rs.getString("tgl_deposit"),
                               Valid.SetAngka(rs.getDouble("besar_deposit")),
                               rs.getString("petugas"),
                               rs.getString("tipe_bayar"),
                               rs.getString("keterangan"),
                               rs.getString("id")
                });
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }        
        LCount.setText(""+tabMode.getRowCount());
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            Valid.SetTgl(DTPTgl,tbObat.getValueAt(tbObat.getSelectedRow(),3).toString().substring(0,10));
            cmbJam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString().substring(11,13));
            cmbMnt.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString().substring(14,16));
            cmbDtk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString().substring(17,19)); 
            BesarDeposit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            String petugas = StringUtils.substringAfter(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString(), " ");
            String kdpetugas = StringUtils.substringBefore(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString(), " ");
            TPetugas.setText(petugas);
            kdptg.setText(kdpetugas);
            diterimaDari.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            tipeBayar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            keteranganDeposit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            id = Integer.valueOf(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select concat(pasien.no_rkm_medis,' ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                        "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
    }
    
    /**
     *
     * @param norwt
     * @param tgl1
     * @param tgl2
     */
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        isRawat();
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,128));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    /**
     *
     */
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdeposit_pasien());
//        BtnHapus.setEnabled(akses.getdeposit_pasien());
        if(akses.getkode().equals("Admin Utama")){
            BtnHapus.setEnabled(true);
        }else{
            BtnHapus.setEnabled(false);
        }   
        BtnPrint.setEnabled(akses.getdeposit_pasien());
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    
}
