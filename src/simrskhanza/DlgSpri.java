/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPasienMati.java
 *
 * Created on Aug 30, 2010, 7:46:01 AM
 */
package simrskhanza;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import interfaces.SpriDao;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgCariPerawatanRanap;
import model.Dokter;
import model.Spri;

public class DlgSpri extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private DlgPasien pasien = new DlgPasien(null, false);
    private PreparedStatement ps, psdiagnosa;
    private ResultSet rs;

    /**
     *
     */
    public DlgCariPerawatanRanap perawatan = new DlgCariPerawatanRanap(null, false);

    /**
     *
     */
    public DlgCariPenyakit diagnosa = new DlgCariPenyakit(null, false);
    private String sql = " spri.norm=pasien.no_rkm_medis  ";
    private String status = "";
    private String id = "";
    private final Connection connect = koneksiDB.condb();
    List<Spri> spris = new ArrayList<>();
    Dokter d = new Dokter();

    private SpriDao spriDao = new SpriDao();
    private Spri spri = new Spri();

    /**
     * Creates new form DlgPasienMati
     *
     * @param frame
     * @param bln
     */
    public DlgSpri(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());

        Object[] row = {
            "Tanggal", //1
            "Jam", //2
            "No.R.Medik",//3 
            "", //4
            "",//5 
            "Ruangan",//6
            "kd_dokter", //7
            "Dokter", //8
            "", //9
            "Diagnosa Penyakit", //10 
            "id"}; //11

        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbSpri.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSpri.setPreferredScrollableViewportSize(new Dimension(500, 500));
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbSpri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbSpri.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(75);//tanggal
                    column.setCellRenderer(leftRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(60);//jam
                    column.setCellRenderer(leftRenderer);
                    break;
                case 2:
                    column.setPreferredWidth(80);//norm
                    column.setCellRenderer(centerRenderer);
                    break;
                case 3:
                    column.setPreferredWidth(180);//nama
                    column.setCellRenderer(leftRenderer);
                    break;
                case 4:
                    column.setPreferredWidth(100);//jk
                    column.setCellRenderer(leftRenderer);
                    break;
                case 5:
                    column.setPreferredWidth(120);//ttl
                    column.setCellRenderer(leftRenderer);
                    break;
                case 6:
                    column.setPreferredWidth(75);//tgllahir
                    column.setCellRenderer(leftRenderer);
                    break;
                case 7:
                    column.setPreferredWidth(130);//gd
                    column.setCellRenderer(leftRenderer);
                    break;
                case 8:
                    column.setPreferredWidth(90);//stt nikah
                    column.setCellRenderer(leftRenderer);
                    break;
                case 9:
                    column.setPreferredWidth(190);//agama
                    column.setCellRenderer(leftRenderer);
                    break;
                case 10:
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    break;
                default:
                    break;
            }
        }

        tbSpri.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRM.setDocument(new batasInput((byte) 15).getKata(TNoRM));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        txtRencanaPerawatan.setDocument(new batasInput((byte) 100).getKata(txtRencanaPerawatan));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (pasien.getTable().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 1).toString());
                        TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 2).toString());
                    }
                    if (pasien.getTable2().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(), 1).toString());
                        TPasien.setText(pasien.getTable2().getValueAt(pasien.getTable2().getSelectedRow(), 2).toString());
                    }
                    if (pasien.getTable3().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(), 1).toString());
                        TPasien.setText(pasien.getTable3().getValueAt(pasien.getTable3().getSelectedRow(), 2).toString());
                    }
                    TNoRM.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pasien.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pasien.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (akses.getform().equals("DlgPasienMati")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        perawatan.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgRawatInap")) {
                    if (perawatan.dokter.getTable().getSelectedRow() != -1) {
                        txtKdDokter.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(), 0).toString());
                        txtNmDokter.setText(perawatan.dokter.getTable().getValueAt(perawatan.dokter.getTable().getSelectedRow(), 1).toString());
                        txtRencanaPerawatan.requestFocus();

                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (diagnosa.getTable().getSelectedRow() != -1) {
                    txtKdPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(), 0).toString());
                    txtNmPenyakit.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(), 1).toString());
                    cmbUpf.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        jam();
//        
//        Valid.setPlaceHolder(TPasien, "");
//        Valid.setPlaceHolder(TNoRM, "");
////        Valid.setPlaceHolder(txtKdKamar, "");
//        Valid.setPlaceHolder(txtRencanaPerawatan, "");
//        Valid.setPlaceHolder(txtKdDokter, "");
//        Valid.setPlaceHolder(txtNmDokter, "");
//        Valid.setPlaceHolder(txtKdPenyakit, "");
//        Valid.setPlaceHolder(txtNmPenyakit, "");
        DTPTgl.setDate(new Date());
    }

    /**
     *
     * @param status
     */
    public void setForm(String status) {
        this.status = status;
    }

    /**
     *
     * @param teks
     * @param pesan
     */
    public void setPlaceHolder(JTextField teks, String pesan) {
        teks.setText(pesan);
        teks.setForeground(Color.GRAY);
        teks.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (teks.getText().equals("")) {
                    teks.setText(pesan);
                    teks.setForeground(Color.GRAY);
                } else if (teks.getText().equals(pesan)) {
                    teks.setText("");
                    teks.setForeground(Color.getColor("#323232"));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (teks.getText().equals(pesan) || teks.getText().equals("")) {
                    teks.setText(pesan);
                    teks.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakSuratMati = new javax.swing.JMenuItem();
        MnAngkutJenazah = new javax.swing.JMenuItem();
        MnCetakSpri = new javax.swing.JMenuItem();
        txtId = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbSpri = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        btnGanti = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        jLabel18 = new widget.Label();
        DTPCari2 = new widget.Tanggal1();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        DTPCari1 = new widget.Tanggal1();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel9 = new widget.Label();
        txtRencanaPerawatan = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        BtnSeek = new widget.Button();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel16 = new widget.Label();
        txtNmPenyakit = new widget.TextBox();
        txtKdPenyakit = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        jLabel17 = new widget.Label();
        txtKdDokter = new widget.TextBox();
        txtNmDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        cmbUpf = new widget.ComboBox();
        jLabel5 = new widget.Label();
        ChkJln = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        DTPTgl = new widget.Tanggal1();
        jLabel19 = new widget.Label();
        txtRujukan = new widget.TextBox();
        jLabel20 = new widget.Label();
        txtTerapi = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratMati.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratMati.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratMati.setForeground(java.awt.Color.darkGray);
        MnCetakSuratMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratMati.setText("Surat Kematian");
        MnCetakSuratMati.setName("MnCetakSuratMati"); // NOI18N
        MnCetakSuratMati.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakSuratMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratMatiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratMati);

        MnAngkutJenazah.setBackground(new java.awt.Color(255, 255, 254));
        MnAngkutJenazah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAngkutJenazah.setForeground(java.awt.Color.darkGray);
        MnAngkutJenazah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAngkutJenazah.setText("Surat Angkut Jenazah");
        MnAngkutJenazah.setName("MnAngkutJenazah"); // NOI18N
        MnAngkutJenazah.setPreferredSize(new java.awt.Dimension(250, 28));
        MnAngkutJenazah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAngkutJenazahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnAngkutJenazah);

        MnCetakSpri.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSpri.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSpri.setForeground(new java.awt.Color(51, 51, 51));
        MnCetakSpri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSpri.setText("Cetak SPRI");
        MnCetakSpri.setName("MnCetakSpri"); // NOI18N
        MnCetakSpri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSpriActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSpri);

        txtId.setText("jTextField1");
        txtId.setName("txtId"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surat Permintaan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbSpri.setAutoCreateRowSorter(true);
        tbSpri.setComponentPopupMenu(jPopupMenu1);
        tbSpri.setName("tbSpri"); // NOI18N
        tbSpri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSpriMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbSpriMouseEntered(evt);
            }
        });
        tbSpri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbSpriKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbSpri);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));

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

        btnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        btnGanti.setText("Ganti");
        btnGanti.setName("btnGanti"); // NOI18N
        btnGanti.setPreferredSize(new java.awt.Dimension(100, 30));
        btnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGantiActionPerformed(evt);
            }
        });

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));

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

        javax.swing.GroupLayout panelGlass8Layout = new javax.swing.GroupLayout(panelGlass8);
        panelGlass8.setLayout(panelGlass8Layout);
        panelGlass8Layout.setHorizontalGroup(
            panelGlass8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BtnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BtnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnGanti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(BtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );
        panelGlass8Layout.setVerticalGroup(
            panelGlass8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass8Layout.createSequentialGroup()
                .addGroup(panelGlass8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass8Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGlass8Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(LCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGlass8Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(panelGlass8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGanti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelGlass8Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(BtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("s.d.");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(24, 23));

        DTPCari2.setName("DTPCari2"); // NOI18N

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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

        DTPCari1.setName("DTPCari1"); // NOI18N

        javax.swing.GroupLayout panelGlass9Layout = new javax.swing.GroupLayout(panelGlass9);
        panelGlass9.setLayout(panelGlass9Layout);
        panelGlass9Layout.setHorizontalGroup(
            panelGlass9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass9Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DTPCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DTPCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelGlass9Layout.setVerticalGroup(
            panelGlass9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGlass9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGlass9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGlass9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(DTPCari2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGlass9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(DTPCari1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGlass9Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(11, 11, 11))
        );

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setOpaque(false);
        panelBiasa1.setPreferredSize(new java.awt.Dimension(610, 140));
        panelBiasa1.setLayout(null);

        jLabel8.setText("Jam :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelBiasa1.add(jLabel8);
        jLabel8.setBounds(250, 10, 39, 23);

        jLabel4.setText("Ruang Perawatan :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelBiasa1.add(jLabel4);
        jLabel4.setBounds(0, 70, 115, 20);

        jLabel9.setText("Rencana Perawatan :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelBiasa1.add(jLabel9);
        jLabel9.setBounds(0, 100, 115, 23);

        txtRencanaPerawatan.setText("-");
        txtRencanaPerawatan.setHighlighter(null);
        txtRencanaPerawatan.setName("txtRencanaPerawatan"); // NOI18N
        txtRencanaPerawatan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRencanaPerawatanFocusGained(evt);
            }
        });
        txtRencanaPerawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRencanaPerawatanActionPerformed(evt);
            }
        });
        txtRencanaPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRencanaPerawatanKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtRencanaPerawatan);
        txtRencanaPerawatan.setBounds(120, 100, 210, 23);

        TPasien.setToolTipText("");
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TPasienFocusGained(evt);
            }
        });
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        panelBiasa1.add(TPasien);
        TPasien.setBounds(220, 40, 170, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TNoRMFocusGained(evt);
            }
        });
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoRM);
        TNoRM.setBounds(118, 40, 100, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek);
        BtnSeek.setBounds(400, 40, 28, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbJam);
        cmbJam.setBounds(290, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbMnt);
        cmbMnt.setBounds(362, 10, 50, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbDtk);
        cmbDtk.setBounds(420, 10, 62, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(0, 10, 115, 23);

        jLabel16.setText("Dokter :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelBiasa1.add(jLabel16);
        jLabel16.setBounds(430, 70, 80, 23);

        txtNmPenyakit.setHighlighter(null);
        txtNmPenyakit.setName("txtNmPenyakit"); // NOI18N
        txtNmPenyakit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNmPenyakitFocusGained(evt);
            }
        });
        txtNmPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNmPenyakitKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtNmPenyakit);
        txtNmPenyakit.setBounds(650, 40, 200, 23);

        txtKdPenyakit.setHighlighter(null);
        txtKdPenyakit.setName("txtKdPenyakit"); // NOI18N
        txtKdPenyakit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKdPenyakitFocusGained(evt);
            }
        });
        txtKdPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdPenyakitKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtKdPenyakit);
        txtKdPenyakit.setBounds(510, 40, 120, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('1');
        BtnSeek1.setToolTipText("Alt+1");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek1);
        BtnSeek1.setBounds(860, 40, 28, 23);

        jLabel17.setText("Diagnosa :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelBiasa1.add(jLabel17);
        jLabel17.setBounds(430, 40, 80, 23);

        txtKdDokter.setHighlighter(null);
        txtKdDokter.setName("txtKdDokter"); // NOI18N
        txtKdDokter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtKdDokterFocusGained(evt);
            }
        });
        txtKdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdDokterKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtKdDokter);
        txtKdDokter.setBounds(510, 70, 120, 23);

        txtNmDokter.setEditable(false);
        txtNmDokter.setHighlighter(null);
        txtNmDokter.setName("txtNmDokter"); // NOI18N
        txtNmDokter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNmDokterFocusGained(evt);
            }
        });
        txtNmDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNmDokterKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtNmDokter);
        txtNmDokter.setBounds(650, 70, 200, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('1');
        BtnSeek3.setToolTipText("Alt+1");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek3);
        BtnSeek3.setBounds(860, 70, 28, 23);

        cmbUpf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kebidanan", "PD", "PD (Kemuning)", "Kesehatan Anak", "Bedah", "Isolasi", "Sakura", "HCU", "ICU", "ICCU", "PICU", "NICU", "Perinatologi", "NEORISTI" }));
        cmbUpf.setName("cmbUpf"); // NOI18N
        cmbUpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUpfKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbUpf);
        cmbUpf.setBounds(120, 70, 220, 23);

        jLabel5.setText("Pasien :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelBiasa1.add(jLabel5);
        jLabel5.setBounds(0, 40, 115, 23);

        ChkJln.setSelected(true);
        ChkJln.setName("ChkJln"); // NOI18N
        panelBiasa1.add(ChkJln);
        ChkJln.setBounds(490, 10, 20, 18);

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("*");
        jLabel2.setName("jLabel2"); // NOI18N
        panelBiasa1.add(jLabel2);
        jLabel2.setBounds(390, 40, 10, 16);

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("*");
        jLabel3.setName("jLabel3"); // NOI18N
        panelBiasa1.add(jLabel3);
        jLabel3.setBounds(640, 70, 10, 16);

        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("*");
        jLabel11.setName("jLabel11"); // NOI18N
        panelBiasa1.add(jLabel11);
        jLabel11.setBounds(850, 40, 10, 16);

        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("*");
        jLabel12.setName("jLabel12"); // NOI18N
        panelBiasa1.add(jLabel12);
        jLabel12.setBounds(850, 70, 10, 16);

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("*");
        jLabel13.setName("jLabel13"); // NOI18N
        panelBiasa1.add(jLabel13);
        jLabel13.setBounds(330, 100, 10, 16);

        DTPTgl.setName("DTPTgl"); // NOI18N
        panelBiasa1.add(DTPTgl);
        DTPTgl.setBounds(120, 10, 120, 23);

        jLabel19.setText("Terapi :");
        jLabel19.setName("jLabel19"); // NOI18N
        panelBiasa1.add(jLabel19);
        jLabel19.setBounds(660, 100, 50, 23);

        txtRujukan.setText("-");
        txtRujukan.setHighlighter(null);
        txtRujukan.setName("txtRujukan"); // NOI18N
        txtRujukan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRujukanFocusGained(evt);
            }
        });
        txtRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRujukanKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtRujukan);
        txtRujukan.setBounds(510, 100, 150, 23);

        jLabel20.setText("Rujukan :");
        jLabel20.setName("jLabel20"); // NOI18N
        panelBiasa1.add(jLabel20);
        jLabel20.setBounds(430, 100, 80, 23);

        txtTerapi.setText("-");
        txtTerapi.setHighlighter(null);
        txtTerapi.setName("txtTerapi"); // NOI18N
        txtTerapi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTerapiFocusGained(evt);
            }
        });
        txtTerapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTerapiKeyPressed(evt);
            }
        });
        panelBiasa1.add(txtTerapi);
        txtTerapi.setBounds(710, 100, 350, 23);

        javax.swing.GroupLayout internalFrame1Layout = new javax.swing.GroupLayout(internalFrame1);
        internalFrame1.setLayout(internalFrame1Layout);
        internalFrame1Layout.setHorizontalGroup(
            internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, internalFrame1Layout.createSequentialGroup()
                .addGroup(internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Scroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)
                    .addComponent(panelBiasa1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        internalFrame1Layout.setVerticalGroup(
            internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame1Layout.createSequentialGroup()
                .addComponent(panelBiasa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        akses.setform("DlgPasienMati");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt, TNoRM, txtRencanaPerawatan);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        //Valid.pindah(evt, DTPTgl1, cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt, cmbMnt, TNoRM);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TPasien.getText().equals("")) {
            Valid.textKosong(TPasien, "");
        } else if (txtNmPenyakit.getText().equals("")) {
            Valid.textKosong(txtNmPenyakit, "");
        } else if (txtKdDokter.getText().equals("")) {
            Valid.textKosong(txtKdDokter, "");
        } else if (txtNmDokter.getText().equals("")) {
            Valid.textKosong(txtNmDokter, "");
        } else if (txtRencanaPerawatan.getText().equals("")) {
            Valid.textKosong(txtRencanaPerawatan, "");
        } else {
            if (TNoRM.getText().equals("")) {
                TNoRM.setText("");
            }
            if (txtKdPenyakit.getText().equals("")) {
                txtKdPenyakit.setText("");
            }
            d = new Dokter();
            //System.out.println("Tanggal="+ DTPTgl1.getDate());
            //System.out.println("Tanggal format="+Valid.SetDateToString(DTPTgl1.getDate()));
            spri.setTanggal(Valid.SetDateToString(DTPTgl.getDate()));
            spri.setJam(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem());
            spri.setNorm(TNoRM.getText());
            spri.setDiagnosa(txtKdPenyakit.getText());
            spri.setRencana_perawatan(txtRencanaPerawatan.getText().toUpperCase());
            spri.setUpf(cmbUpf.getSelectedItem().toString());
            d.setKd_dokter(txtKdDokter.getText());
            d.setNm_dokter(txtNmDokter.getText());
            spri.setDokter(d);
            spri.setNama(TPasien.getText().toUpperCase());
            spri.setKeluhan(txtNmPenyakit.getText().toUpperCase());
            spri.setStatus("Belum");
            spri.setRujukan(txtRujukan.getText());
            spri.setTerapi(txtTerapi.getText());
            spriDao.save(spri);

            emptTeks();
            tampil();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, txtRencanaPerawatan, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (!TNoRM.getText().trim().equals("")) {
            int a = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin menghapus data?", "Hapus", JOptionPane.OK_CANCEL_OPTION);
            if (a == 0) {
                spriDao.delete(Valid.SetDateToString(DTPCari1.getDate()), txtId, Valid.SetDateToString(DTPCari2.getDate()));
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Pilih data yang akan dihapus.");
        }
        emptTeks();
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            cetakSpri2();
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void cetakSpri() {
        if (!tabMode.getValueAt(tbSpri.getSelectedRow(), 2).equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("ttd", Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
//            System.out.println("ttd = " + Sequel.cariGambar("select logo from setting"));
            //param.put("ttd", "./setting/" + Sequel.cariIsi("select ttd from ttd_dokter where kd_dokter='" + txtKdDokter.getText() + "'"));
            Valid.MyReportqry("rptSpri.jasper", "report", "::[ Surat Laporan Rawat Inap ]::",
                    "SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,if(temp_spri.norm='',temp_spri.nama,pasien.nm_pasien) as nm_pasien,"
                    + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "
                    + "CASE WHEN pasien.jk='' THEN '' WHEN pasien.jk='L' THEN 'Laki-laki' WHEN pasien.jk='P' THEN 'Perempuan' END as jk,pasien.tgl_lahir,"
                    + "temp_spri.rencana_perawatan,temp_spri.upf,temp_spri.nm_dokter,temp_spri.kd_dokter,temp_spri.keluhan "
                    + " FROM temp_spri inner join pasien on temp_spri.norm=pasien.no_rkm_medis "
                    + "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "
                    + "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "
                    + "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "
                    + " WHERE temp_spri.norm = '" + TNoRM.getText().trim() + "' "
                    + "and temp_spri.tanggal between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' "
                    + " order by temp_spri.tanggal ", param);
        } else if (tabMode.getValueAt(tbSpri.getSelectedRow(), 2).equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("ttd", Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
            System.out.println("ttd = " + Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
//                param.put("ttd", "./setting/" + Sequel.cariIsi("select ttd from ttd_dokter where kd_dokter='" + txtKdDokter.getText() + "'"));
            Valid.MyReportqry("rptSpri.jasper", "report", "::[ Surat Laporan Rawat Inap ]::",
                    "SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,if(temp_spri.norm='',temp_spri.nama,pasien.nm_pasien) as nm_pasien,pasien.alamat, "
                    + "CASE WHEN pasien.jk='' THEN '' WHEN pasien.jk='L' THEN 'Laki-laki' WHEN pasien.jk='P' THEN 'Perempuan' END as jk,pasien.tgl_lahir,"
                    + "temp_spri.rencana_perawatan,temp_spri.upf,temp_spri.nm_dokter,temp_spri.kd_dokter,temp_spri.keluhan "
                    + " FROM temp_spri inner join pasien on temp_spri.norm=pasien.no_rkm_medis "
//                    + "inner join dokter on temp_spri.kd_dokter=dokter.kd_dokter "
//                    + "left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit "
                    + " WHERE temp_spri.id = '" + id + "' "
                    + " order by temp_spri.tanggal ", param);
        }
    }

    private void cetakSpri2() {
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        param.put("ttd", Sequel.cariGambar("select ttd from ttd_dokter where kd_dokter ='" + txtKdDokter.getText() + "'"));
        System.out.println("ttd = " + Sequel.cariGambar("select logo from setting"));
        //param.put("ttd", "./setting/" + Sequel.cariIsi("select ttd from ttd_dokter where kd_dokter='" + txtKdDokter.getText() + "'"));
        Valid.MyReportqry("rptDaftarKunjunganPasien.jasper", "report", "::[ Surat Laporan Rawat Inap ]::",
                "SELECT temp_spri.id, temp_spri.tanggal,temp_spri.norm,temp_spri.keluhan,temp_spri.rencana_perawatan,"
                + "temp_spri.nama,temp_spri.rujukan,temp_spri.terapi,temp_spri.nm_dokter,"
                + "pasien.alamat,pasien.umur,pasien.jk as jk_pasien,temp_spri.upf "
                + "FROM temp_spri"
//                + " inner join dokter ON "
//                + " temp_spri.kd_dokter = dokter.kd_dokter "
                + " inner join pasien ON "
                + " pasien.no_rkm_medis = temp_spri.norm "
                + " WHERE temp_spri.tanggal between '" + Valid.SetDateToString(DTPCari1.getDate()) + "' and '" + Valid.SetDateToString(DTPCari2.getDate()) + "' "
                + " order by temp_spri.tanggal ", param);
    }

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        emptTeks();
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TNoRM.setText("");
            TPasien.setText("");
            cmbUpf.setSelectedIndex(0);
            txtKdPenyakit.setText("");
            txtNmPenyakit.setText("");
            txtKdDokter.setText("");
            txtNmDokter.setText("");
            TCari.setText("");
            txtRencanaPerawatan.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbSpriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSpriMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
                System.out.println("getData(), " + e.toString());
            }
        }
}//GEN-LAST:event_tbSpriMouseClicked

private void MnCetakSuratMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratMatiActionPerformed
    if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
    } else {
        String a = Sequel.cariIsi("select no_surat_kematian from temp_spri a inner join pasien_mati b on a.norm=b.no_rkm_medis where a.norm='" + TNoRM.getText() + "'");
        System.out.println("no surat kematian " + a);
        if (!a.equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptSuratKematian.jasper", "report", "::[ Surat Kematian ]::",
                    "select tanggal,jam,pasien_mati.no_rkm_medis,pasien.nm_pasien, "
                    + "pasien.umur,pasien.alamat,jk,tmp_lahir,tgl_lahir,gol_darah,stts_nikah, "
                    + "agama,keterangan from pasien_mati,pasien "
                    + "where pasien_mati.no_rkm_medis=pasien.no_rkm_medis "
                    + "and pasien_mati.no_rkm_medis='" + TNoRM.getText() + "' ", param);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Data Pasien tidak ditemukan pada data pasien meninggal.");
        }
    }
}//GEN-LAST:event_MnCetakSuratMatiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnAngkutJenazahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAngkutJenazahActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptAngkutJenazah.jasper", "report", "::[ Surat Angkut Jenazah ]::",
                    "select tanggal,jam,pasien_mati.no_rkm_medis,pasien.nm_pasien,pasien.pekerjaan, "
                    + "pasien.umur,pasien.alamat,jk,tmp_lahir,tgl_lahir,gol_darah,stts_nikah, "
                    + "agama,keterangan from pasien_mati,pasien "
                    + "where pasien_mati.no_rkm_medis=pasien.no_rkm_medis "
                    + "and pasien_mati.no_rkm_medis='" + TNoRM.getText() + "' ", param);
        }
    }//GEN-LAST:event_MnAngkutJenazahActionPerformed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRM, cmbUpf);
        }
    }//GEN-LAST:event_TNoRMKeyPressed

    private void txtRencanaPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRencanaPerawatanKeyPressed
        Valid.pindah(evt, txtRencanaPerawatan, BtnSimpan);
    }//GEN-LAST:event_txtRencanaPerawatanKeyPressed

    private void tbSpriKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSpriKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                    Logger.getLogger(DlgSpri.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println("getData(), " + e);
                }
            }
        }
    }//GEN-LAST:event_tbSpriKeyReleased

    private void txtNmPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNmPenyakitKeyPressed

    private void txtKdPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdPenyakitKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ", txtNmPenyakit, txtKdPenyakit.getText());

            Valid.pindah(evt, txtKdPenyakit, txtKdDokter);
        }
    }//GEN-LAST:event_txtKdPenyakitKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        // TODO add your handling code here:
        diagnosa.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        diagnosa.setLocationRelativeTo(internalFrame1);
        diagnosa.setVisible(true);
    }//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_BtnSeek1KeyPressed

    private void txtKdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdDokterKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=? ", txtNmDokter, txtKdDokter.getText());
            Valid.pindah(evt, txtKdDokter, txtRencanaPerawatan);
        }
    }//GEN-LAST:event_txtKdDokterKeyPressed

    private void txtNmDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNmDokterKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        // TODO add your handling code here:
        akses.setform("DlgRawatInap");
        perawatan.dokter.emptTeks();
        perawatan.dokter.isCek();
        perawatan.dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        perawatan.dokter.setLocationRelativeTo(internalFrame1);
        perawatan.dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void btnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiActionPerformed
        // TODO add your handling code here:
        d = new Dokter();
        spri.setId(Integer.parseInt(txtId.getText()));
        spri.setTanggal(Valid.SetDateToString(DTPTgl.getDate()));
        spri.setJam(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem());
        spri.setNorm(TNoRM.getText());
        spri.setDiagnosa(txtKdPenyakit.getText());
        spri.setRencana_perawatan(txtRencanaPerawatan.getText().toUpperCase());
        spri.setUpf(cmbUpf.getSelectedItem().toString());
        d.setKd_dokter(txtKdDokter.getText());
        d.setNm_dokter(txtNmDokter.getText());
        spri.setDokter(d);
        spri.setNama(TPasien.getText().toUpperCase());
        spri.setKeluhan(txtNmPenyakit.getText().toUpperCase());
        spri.setStatus("Belum");
        spri.setRujukan(txtRujukan.getText());
        spri.setTerapi(txtTerapi.getText());
        spriDao.update(spri);

        emptTeks();
        tampil();
    }//GEN-LAST:event_btnGantiActionPerformed

    private void cmbUpfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUpfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUpfKeyPressed

    private void TNoRMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TNoRMFocusGained
        // TODO add your handling code here:
        TNoRM.setForeground(Color.black);
    }//GEN-LAST:event_TNoRMFocusGained

    private void TPasienFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TPasienFocusGained
        // TODO add your handling code here:
        TPasien.setForeground(Color.black);
    }//GEN-LAST:event_TPasienFocusGained

    private void txtKdPenyakitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKdPenyakitFocusGained
        // TODO add your handling code here:
        txtKdPenyakit.setForeground(Color.black);
    }//GEN-LAST:event_txtKdPenyakitFocusGained

    private void txtNmPenyakitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNmPenyakitFocusGained
        // TODO add your handling code here:
        txtNmPenyakit.setForeground(Color.black);
    }//GEN-LAST:event_txtNmPenyakitFocusGained

    private void txtKdDokterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtKdDokterFocusGained
        // TODO add your handling code here:
        txtKdDokter.setForeground(Color.black);
    }//GEN-LAST:event_txtKdDokterFocusGained

    private void txtNmDokterFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNmDokterFocusGained
        // TODO add your handling code here:
        txtNmDokter.setForeground(Color.black);
    }//GEN-LAST:event_txtNmDokterFocusGained

    private void txtRencanaPerawatanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRencanaPerawatanFocusGained
        // TODO add your handling code here:
        txtRencanaPerawatan.setForeground(Color.black);
    }//GEN-LAST:event_txtRencanaPerawatanFocusGained

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        // TODO add your handling code here:
        Valid.pindah(evt, TPasien, cmbUpf);
    }//GEN-LAST:event_TPasienKeyPressed

    private void MnCetakSpriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSpriActionPerformed
        // TODO add your handling code here:
        if (TNoRM.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Pilih data yang akan dicetak dahulu.");
            BtnCariActionPerformed(evt);
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                BtnBatal.requestFocus();
            } else if (tabMode.getRowCount() != 0) {

                cetakSpri();

            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSpriActionPerformed

    private void tbSpriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSpriMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbSpriMouseEntered

    private void txtRencanaPerawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRencanaPerawatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRencanaPerawatanActionPerformed

    private void txtRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRujukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRujukanKeyPressed

    private void txtRujukanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRujukanFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRujukanFocusGained

    private void txtTerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTerapiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTerapiKeyPressed

    private void txtTerapiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTerapiFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTerapiFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSpri dialog = new DlgSpri(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek3;
    private widget.Button BtnSimpan;
    private javax.swing.JCheckBox ChkJln;
    private widget.Tanggal1 DTPCari1;
    private widget.Tanggal1 DTPCari2;
    private widget.Tanggal1 DTPTgl;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnAngkutJenazah;
    private javax.swing.JMenuItem MnCetakSpri;
    private javax.swing.JMenuItem MnCetakSuratMati;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.Button btnGanti;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbUpf;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private javax.swing.JLabel jLabel2;
    private widget.Label jLabel20;
    private javax.swing.JLabel jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbSpri;
    private javax.swing.JTextField txtId;
    private widget.TextBox txtKdDokter;
    private widget.TextBox txtKdPenyakit;
    private widget.TextBox txtNmDokter;
    private widget.TextBox txtNmPenyakit;
    private widget.TextBox txtRencanaPerawatan;
    private widget.TextBox txtRujukan;
    private widget.TextBox txtTerapi;
    // End of variables declaration//GEN-END:variables

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
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

    /**
     *
     */
    public void tampil() {
        spris = new ArrayList<>();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.tabelKosong(tabMode);
        if (TCari.getText().equals("")) {
            spris = spriDao.findByDate(Valid.SetDateToString(DTPCari1.getDate()), Valid.SetDateToString(DTPCari2.getDate()));
        } else if (!TCari.getText().equals("")) {
            spris = spriDao.search(TCari.getText(), Valid.SetDateToString(DTPCari1.getDate()), Valid.SetDateToString(DTPCari2.getDate()));
        }
//        else {
//            if (!TNoRM.getText().equals("")) {
//                spris = spriDao.search(TNoRM.getText(), Valid.SetDateToString(DTPCari1.getDate()), Valid.SetDateToString(DTPCari2.getDate()));
//            } else if (!txtKdDokter.getText().equals("") || !txtKdDokter.getText().equals("")) {
//                spris = spriDao.search(txtKdDokter.getText(), Valid.SetDateToString(DTPCari1.getDate()), Valid.SetDateToString(DTPCari2.getDate()));
//            } else if (!txtKdPenyakit.getText().equals("") || txtKdPenyakit.getText().equals("")) {
//                spris = spriDao.search(txtKdPenyakit.getText(), Valid.SetDateToString(DTPCari1.getDate()), Valid.SetDateToString(DTPCari2.getDate()));
//            } else if (!TCari.getText().trim().equals("")) {
//                spris = spriDao.search(TCari.getText(), Valid.SetDateToString(DTPCari1.getDate()), Valid.SetDateToString(DTPCari2.getDate()));
//            }
//        }
        for (Spri s : spris) {
            if (s.getDiagnosa().equals("")) {
                tabMode.addRow(new Object[]{
                    s.getTanggal(),
                    s.getJam(),
                    s.getNorm(),
                    s.getNama(),
                    s.getRencana_perawatan(),
                    s.getUpf(),
                    s.getDokter().getKd_dokter(),
                    s.getDokter().getNm_dokter(),
                    s.getDiagnosa(),
                    s.getKeluhan(),
                    s.getId()
                });
            } else if (!s.getDiagnosa().equals("")) {
                tabMode.addRow(new Object[]{
                    s.getTanggal(),
                    s.getJam(),
                    s.getNorm(),
                    s.getNama(),
                    s.getRencana_perawatan(),
                    s.getUpf(),
                    s.getDokter().getKd_dokter(),
                    s.getDokter().getNm_dokter(),
                    s.getDiagnosa(),
                    s.getKeluhan(),
                    s.getId()
                });
            }
        }
        int b = tabMode.getRowCount();
        LCount.setText("" + b);
        this.setCursor(Cursor.getDefaultCursor());
    }

    /**
     *
     */
    public void emptTeks() {
        TPasien.setText("");
        TNoRM.setText("");
        txtRencanaPerawatan.setText("");
        txtKdDokter.setText("");
        txtNmDokter.setText("");
        txtKdPenyakit.setText("");
        txtNmPenyakit.setText("");
        TCari.setText("");
        txtRujukan.setText("");
        txtTerapi.setText("");
        cmbUpf.setSelectedIndex(0);
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if (tbSpri.getSelectedRow() != -1) {
            id = tbSpri.getValueAt(tbSpri.getSelectedRow(), 10).toString();
            txtId.setText(id);
            cmbJam.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 1).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 1).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 1).toString().substring(6, 8));
            TNoRM.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 2).toString());
            TPasien.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 3).toString());
            txtRencanaPerawatan.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 4).toString());
//            Valid.SetTgl(DTPTgl, tbSpri.getValueAt(tbSpri.getSelectedRow(), 0).toString());
            DTPTgl.setDate(Valid.SetStringToDate(tbSpri.getValueAt(tbSpri.getSelectedRow(), 0).toString()));
            if (!tbSpri.getValueAt(tbSpri.getSelectedRow(), 5).toString().trim().equals("")) {
                cmbUpf.setSelectedItem(tbSpri.getValueAt(tbSpri.getSelectedRow(), 5).toString());
            } else {
                cmbUpf.setSelectedIndex(0);
            }
            txtKdPenyakit.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 8).toString());
            txtNmPenyakit.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 9).toString());
            txtKdDokter.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 6).toString());
            txtNmDokter.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 7).toString());
//            txtRujukan.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 20).toString());
//            txtTerapi.setText(tbSpri.getValueAt(tbSpri.getSelectedRow(), 21).toString());
        }
    }

    /**
     *
     */
    public void isCek() {
        BtnSimpan.setEnabled(akses.getpasien_meninggal());
        BtnHapus.setEnabled(akses.getpasien_meninggal());
        BtnPrint.setEnabled(akses.getpasien_meninggal());
    }

    /**
     *
     * @param norm
     */
    public void setNoRm(String norm) {
        TNoRM.setText(norm);
        TCari.setText(norm);
        isPsien();
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
    }

}
