/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */
package keuangan;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author perpustakaan
 */
public final class DlgPerkiraanBiayaRanap extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private final Properties prop = new Properties();
    public DlgCariBangsal bangsal = new DlgCariBangsal(null, true);
    private DlgPenanggungJawab carabayar = new DlgPenanggungJawab(null, true);
    private double all = 0, Laborat = 0, Radiologi = 0, Operasi = 0, Obat = 0, Ranap_Dokter = 0, Ranap_Paramedis = 0, Ranap_Dokter_Paramedis = 0, Ralan_Dokter = 0,
            Ralan_Paramedis = 0, Ralan_Dokter_Paramedis = 0, Tambahan = 0, Potongan = 0, Kamar = 0, Registrasi = 0, Harian = 0, Retur_Obat = 0, Resep_Pulang = 0,
            ttlLaborat = 0, ttlRadiologi = 0, ttlOperasi = 0, ttlObat = 0, ttlRanap_Dokter = 0, ttlRanap_Paramedis = 0, ttlRalan_Dokter = 0,
            ttlRalan_Paramedis = 0, ttlTambahan = 0, ttlPotongan = 0, ttlKamar = 0, ttlRegistrasi = 0, ttlHarian = 0, ttlRetur_Obat = 0, ttlResep_Pulang = 0, deposit = 0, ttlDeposit = 0;
    private String namakamar;
    NumberFormat nf = NumberFormat.getNumberInstance();

    /**
     * Creates new form DlgLhtBiaya
     *
     * @param parent
     * @param modal
     */
    public DlgPerkiraanBiayaRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(885, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "Jenis Bayar", "Kamar/Bangsal", "Registrasi", "Tindakan", "Obt+Emb+Tsl", "Retur Obat",
            "Resep Pulang", "Laborat", "Radiologi", "Potongan", "Tambahan", "Kamar", "Operasi", "Harian", "Total", "Deposit", "Hak Kelas 1", "Hak Kelas 2", "Hak Kelas 3", "Selisih", "Penjaminan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 19; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(105);
                    break;
                case 1:
                    column.setPreferredWidth(70);
                    break;
                case 2:
                    column.setPreferredWidth(170);
                    break;
                case 3:
                    column.setPreferredWidth(150);
                    break;
                case 4:
                    column.setPreferredWidth(150);
                    break;
                case 17:
                    column.setPreferredWidth(100);
                    break;
                case 18:
                    column.setPreferredWidth(100);
                    break;
                default:
                    column.setPreferredWidth(75);
                    break;
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
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

        bangsal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        bangsal.dispose();
                    }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (bangsal.getTable().getSelectedRow() != -1) {
                    txtKdBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                    NmBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                }
                txtKdBangsal.requestFocus();
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

        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (carabayar.getTable().getSelectedRow() != -1) {
                    kdPenjab.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(), 1).toString());
                    nmPenjab.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(), 2).toString());
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
                carabayar.onCari();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        carabayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    carabayar.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void setRM(String no_rekamedis) {
        TCari.setText(no_rekamedis);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label17 = new widget.Label();
        txtKdBangsal = new widget.TextBox();
        NmBangsal = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label18 = new widget.Label();
        kdPenjab = new widget.TextBox();
        nmPenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perkiraan Biaya Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 80));

        label17.setText("Kamar/Bangsal :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));

        txtKdBangsal.setName("txtKdBangsal"); // NOI18N
        txtKdBangsal.setPreferredSize(new java.awt.Dimension(100, 24));
        txtKdBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdBangsalKeyPressed(evt);
            }
        });

        NmBangsal.setEditable(false);
        NmBangsal.setName("NmBangsal"); // NOI18N
        NmBangsal.setPreferredSize(new java.awt.Dimension(160, 23));

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });

        label18.setText("Jenis Bayar :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(85, 23));

        kdPenjab.setName("kdPenjab"); // NOI18N
        kdPenjab.setPreferredSize(new java.awt.Dimension(80, 23));
        kdPenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdPenjabKeyPressed(evt);
            }
        });

        nmPenjab.setEditable(false);
        nmPenjab.setName("nmPenjab"); // NOI18N
        nmPenjab.setPreferredSize(new java.awt.Dimension(180, 23));

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
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

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
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

        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(28, 23));

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
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

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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

        javax.swing.GroupLayout panelGlass5Layout = new javax.swing.GroupLayout(panelGlass5);
        panelGlass5.setLayout(panelGlass5Layout);
        panelGlass5Layout.setHorizontalGroup(
            panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addComponent(txtKdBangsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(NmBangsal, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addComponent(BtnSeek2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(kdPenjab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(nmPenjab, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnSeek3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addComponent(BtnCari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(BtnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77)
                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        panelGlass5Layout.setVerticalGroup(
            panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKdBangsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NmBangsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSeek2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kdPenjab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nmPenjab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSeek3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnCari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(BtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {

            Sequel.queryu("delete from temporary");
            for (int r = 0; r < tabMode.getRowCount(); r++) {
                Sequel.menyimpan("temporary", "'0','"
                        + tabMode.getValueAt(r, 0).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 1).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 2).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 3).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 4).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 5).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 6).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 7).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 8).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 9).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 10).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 11).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 12).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 13).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 14).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 15).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 16).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 17).toString().replaceAll("'", "`") + "','"
                        + tabMode.getValueAt(r, 18).toString().replaceAll("'", "`") + "','','','','','','','','','','','','','','','','','',''", "Rekap Perkiraan Ranap");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptPerkiraanBiayaRanap.jasper", "report", "::[ Perkiraan Biaya Rawat Inap ]::", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnKeluar, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed

    tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    } else {
        Valid.pindah(evt, TCari, BtnPrint);
    }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
//        akses.setform("DlgKamarInap");
        bangsal.isCek();
        bangsal.emptTeks();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NmBangsal.setText("");
        kdPenjab.setText("");
        nmPenjab.setText("");
        txtKdBangsal.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampil();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari1.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnSeek2.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_formWindowActivated

    private void txtKdBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdBangsalKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", NmBangsal, txtKdBangsal.getText());
                BtnAll.requestFocus();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtKdBangsalKeyPressed

    private void kdPenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdPenjabKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmPenjab, kdPenjab.getText());
                BtnAll.requestFocus();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_kdPenjabKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        // TODO add your handling code here:
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek3KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPerkiraanBiayaRanap dialog = new DlgPerkiraanBiayaRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.TextBox NmBangsal;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdPenjab;
    private widget.Label label10;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmPenjab;
    private widget.panelisi panelGlass5;
    private widget.Table tbBangsal;
    private widget.TextBox txtKdBangsal;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public void tampil() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,bangsal.nm_bangsal,kamar.kd_kamar,reg_periksa.biaya_reg, penjab.png_jawab "
                    + "from kamar_inap inner join reg_periksa inner join pasien inner join bangsal inner join kamar inner join penjab "
                    + "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.kd_pj=penjab.kd_pj "
                    + "where "
                    + "(kamar_inap.stts_pulang='-' or kamar_inap.stts_pulang='AKTIF') and bangsal.nm_bangsal like ? and penjab.kd_pj like ? and kamar_inap.no_rawat like ? or "
                    + "(kamar_inap.stts_pulang='-' or kamar_inap.stts_pulang='AKTIF') and bangsal.nm_bangsal like ? and penjab.kd_pj like ? and reg_periksa.no_rkm_medis like ? or "
                    + "(kamar_inap.stts_pulang='-' or kamar_inap.stts_pulang='AKTIF') and bangsal.nm_bangsal like ? and penjab.kd_pj like ? and pasien.nm_pasien like ? or "
                    + "(kamar_inap.stts_pulang='-' or kamar_inap.stts_pulang='AKTIF') and bangsal.nm_bangsal like ? and penjab.kd_pj like ? and kamar.kd_kamar like ? or "
                    + "(kamar_inap.stts_pulang='-' or kamar_inap.stts_pulang='AKTIF') and bangsal.nm_bangsal like ? and penjab.kd_pj like ? and penjab.png_jawab like ? "
                    + "order by bangsal.nm_bangsal");
            try {
                ps.setString(1, "%" + NmBangsal.getText() + "%");
                ps.setString(2, "%" + kdPenjab.getText() + "%");
                ps.setString(3, "%" + TCari.getText() + "%");
                
                ps.setString(4, "%" + NmBangsal.getText() + "%");
                ps.setString(5, "%" + kdPenjab.getText() + "%");
                ps.setString(6, "%" + TCari.getText() + "%");
                
                ps.setString(7, "%" + NmBangsal.getText() + "%");
                ps.setString(8, "%" + kdPenjab.getText() + "%");
                ps.setString(9, "%" + TCari.getText() + "%");
                
                ps.setString(10, "%" + NmBangsal.getText() + "%");
                ps.setString(11, "%" + kdPenjab.getText() + "%");
                ps.setString(12, "%" + TCari.getText() + "%");
                
                ps.setString(13, "%" + NmBangsal.getText() + "%");
                ps.setString(14, "%" + kdPenjab.getText() + "%");
                ps.setString(15, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                all = 0;
                ttlLaborat = 0;
                ttlRadiologi = 0;
                ttlOperasi = 0;
                ttlObat = 0;
                ttlRanap_Dokter = 0;
                ttlRanap_Paramedis = 0;
                ttlRalan_Dokter = 0;
                ttlRalan_Paramedis = 0;
                ttlTambahan = 0;
                ttlPotongan = 0;
                ttlKamar = 0;
                ttlRegistrasi = 0;
                ttlHarian = 0;
                ttlRetur_Obat = 0;
                ttlResep_Pulang = 0;
                String hak_kelas_1 = "0";
                String hak_kelas_2 = "0";
                String hak_kelas_3 = "0";
                String selisih = "0";
                String penjaminan = "0";

                while (rs.next()) {
                    Registrasi = rs.getDouble("biaya_reg");
                    ttlRegistrasi += Registrasi;

                    Laborat = Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where no_rawat=?", rs.getString("no_rawat")) + Sequel.cariIsiAngka("select sum(biaya_item) from detail_periksa_lab where no_rawat=?", rs.getString("no_rawat"));
                    ttlLaborat += Laborat;

                    Radiologi = Sequel.cariIsiAngka("select sum(biaya) from periksa_radiologi where no_rawat=?", rs.getString("no_rawat"));
                    ttlRadiologi = ttlRadiologi + Radiologi;

                    Operasi = Sequel.cariIsiAngka("select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayadokter_anak+biayaperawaat_resusitas+biayadokter_anestesi+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biayaalat+biayasewaok+akomodasi+bagian_rs+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayasarpras+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat=?", rs.getString("no_rawat"));
                    ttlOperasi = ttlOperasi + Operasi;

                    Obat = Sequel.cariIsiAngka("select sum(total) from detail_pemberian_obat where no_rawat=?", rs.getString("no_rawat")) + Sequel.cariIsiAngka("select sum(besar_tagihan) from tagihan_obat_langsung where no_rawat=?", rs.getString("no_rawat")) + Sequel.cariIsiAngka("select sum(hargasatuan*jumlah) from beri_obat_operasi where no_rawat=?", rs.getString("no_rawat"));
                    ttlObat = ttlObat + Obat;

                    Ranap_Dokter = Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_dr where no_rawat=?", rs.getString("no_rawat"));
                    ttlRanap_Dokter = ttlRanap_Dokter + Ranap_Dokter;

                    Ranap_Dokter_Paramedis = Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_drpr where no_rawat=?", rs.getString("no_rawat"));
                    ttlRanap_Dokter = ttlRanap_Dokter + Ranap_Dokter_Paramedis;

                    Ranap_Paramedis = Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_pr where no_rawat=?", rs.getString("no_rawat"));
                    ttlRanap_Paramedis = ttlRanap_Paramedis + Ranap_Paramedis;

                    Ralan_Dokter = Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_dr where no_rawat=?", rs.getString("no_rawat"));
                    ttlRalan_Dokter = ttlRalan_Dokter + Ralan_Dokter;

                    Ralan_Dokter_Paramedis = Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_drpr where no_rawat=?", rs.getString("no_rawat"));
                    ttlRalan_Dokter = ttlRalan_Dokter + Ralan_Dokter_Paramedis;

                    Ralan_Paramedis = Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_pr where no_rawat=?", rs.getString("no_rawat"));
                    ttlRalan_Paramedis = ttlRalan_Paramedis + Ralan_Paramedis;

                    Tambahan = Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=?", rs.getString("no_rawat"));
                    ttlTambahan = ttlTambahan + Tambahan;

                    Potongan = Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=?", rs.getString("no_rawat"));
                    ttlPotongan = ttlPotongan + Potongan;

                    Kamar = Sequel.cariIsiAngka("select sum(ttl_biaya) from kamar_inap where no_rawat=?", rs.getString("no_rawat")) + Sequel.cariIsiAngka("select sum(biaya_sekali.besar_biaya) from biaya_sekali inner join kamar_inap on kamar_inap.kd_kamar=biaya_sekali.kd_kamar where kamar_inap.no_rawat=?", rs.getString("no_rawat"));
                    ttlKamar = ttlKamar + Kamar;

                    Harian = Sequel.cariIsiAngka("select sum(biaya_harian.jml*biaya_harian.besar_biaya*kamar_inap.lama) from kamar_inap inner join biaya_harian on kamar_inap.kd_kamar=biaya_harian.kd_kamar where kamar_inap.no_rawat=?", rs.getString("no_rawat"));
                    ttlHarian = ttlHarian + Harian;

                    Retur_Obat = (-1) * Sequel.cariIsiAngka("select sum(subtotal) from detreturjual where no_retur_jual like ? ", "%" + rs.getString("no_rawat") + "%");
                    ttlRetur_Obat = ttlRetur_Obat + Retur_Obat;

                    Resep_Pulang = Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=? ", rs.getString("no_rawat"));
                    ttlResep_Pulang = ttlResep_Pulang + Resep_Pulang;

                    deposit = Sequel.cariIsiAngka("select sum(besar_deposit) from deposit where no_rawat=? ", rs.getString("no_rawat"));
                    ttlDeposit += deposit;

                    hak_kelas_1 = Sequel.cariIsi("select hak_kelas_1 from coderbpjs where no_rawat='" + rs.getString("no_rawat") + "' order by id_coder desc");
                    hak_kelas_2 = Sequel.cariIsi("select hak_kelas_2 from coderbpjs where no_rawat='" + rs.getString("no_rawat") + "' order by id_coder desc");
                    hak_kelas_3 = Sequel.cariIsi("select hak_kelas_3 from coderbpjs where no_rawat='" + rs.getString("no_rawat") + "' order by id_coder desc");
                    selisih = Sequel.cariIsi("select selisih from coderbpjs where no_rawat='" + rs.getString("no_rawat") + "' order by id_coder desc");
                    penjaminan = Sequel.cariIsi("select penjaminan from coderbpjs where no_rawat='" + rs.getString("no_rawat") + "' order by id_coder desc");
                    if (hak_kelas_1.isEmpty()) {
                        hak_kelas_1 = "0";
                    } else {
                        hak_kelas_1 = nf.format(Double.parseDouble(hak_kelas_1));
                    }
                    if (hak_kelas_2.isEmpty()) {
                        hak_kelas_2 = "0";
                    } else {
                        hak_kelas_2 = nf.format(Double.parseDouble(hak_kelas_2));
                    }
                    if (hak_kelas_3.isEmpty()) {
                        hak_kelas_3 = "0";
                    } else {
                        hak_kelas_3 = nf.format(Double.parseDouble(hak_kelas_3));
                    }
                    if (selisih.isEmpty()) {
                        selisih = "0";
                    } else {
                        selisih = nf.format(Double.parseDouble(selisih));
                    }
                    if (penjaminan.isEmpty()) {
                        penjaminan = "0";
                    } else {
                        penjaminan = nf.format(Double.parseDouble(penjaminan));
                    }

                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("png_jawab"), rs.getString("kd_kamar") + " " + rs.getString("nm_bangsal"),
                        Valid.SetAngka(Registrasi), Valid.SetAngka(Ranap_Dokter + Ranap_Dokter_Paramedis + Ranap_Paramedis + Ralan_Dokter + Ralan_Dokter_Paramedis + Ralan_Paramedis),
                        Valid.SetAngka(Obat), Valid.SetAngka(Retur_Obat), Valid.SetAngka(Resep_Pulang), Valid.SetAngka(Laborat), Valid.SetAngka(Radiologi), Valid.SetAngka(Potongan),
                        Valid.SetAngka(Tambahan), Valid.SetAngka(Kamar), Valid.SetAngka(Operasi), Valid.SetAngka(Harian), Valid.SetAngka(Laborat + Radiologi + Operasi + Obat + Ranap_Dokter
                        + Ranap_Dokter_Paramedis + Ranap_Paramedis + Ralan_Dokter + Ralan_Dokter_Paramedis + Ralan_Paramedis + Tambahan + Potongan + Kamar + Registrasi + Harian + Retur_Obat + Resep_Pulang),
                        Valid.SetAngka(deposit), hak_kelas_1, hak_kelas_2, hak_kelas_3, selisih, penjaminan
                    });

                    all = all + Laborat + Radiologi + Operasi + Obat + Ranap_Dokter + Ranap_Dokter_Paramedis + Ranap_Paramedis + Ralan_Dokter + Ralan_Dokter_Paramedis + Ralan_Paramedis + Tambahan + Potongan + Kamar + Registrasi + Harian + Retur_Obat + Resep_Pulang;
                }
                tabMode.addRow(new Object[]{
                    ">> Total ", ":", "", "", "", Valid.SetAngka(ttlRegistrasi), Valid.SetAngka(ttlRanap_Dokter + ttlRanap_Paramedis + ttlRalan_Dokter + ttlRalan_Paramedis),
                    Valid.SetAngka(ttlObat), Valid.SetAngka(ttlRetur_Obat), Valid.SetAngka(ttlResep_Pulang), Valid.SetAngka(ttlLaborat), Valid.SetAngka(ttlRadiologi), Valid.SetAngka(ttlPotongan),
                    Valid.SetAngka(ttlTambahan), Valid.SetAngka(ttlKamar), Valid.SetAngka(ttlOperasi), Valid.SetAngka(ttlHarian), Valid.SetAngka(all), Valid.SetAngka(ttlDeposit)
                });
            } catch (Exception e) {
                System.out.println("Notif 1 : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }

            this.setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    /**
     *
     */
    public void isCek() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            namakamar = prop.getProperty("KAMARAKTIFRANAP");
        } catch (Exception ex) {
            namakamar = "";
        }

        if (!namakamar.equals("")) {
            if (akses.getkode().equals("Admin Utama")) {
                NmBangsal.setText("");
                BtnSeek2.setEnabled(true);
                NmBangsal.setEditable(true);
            } else {
                NmBangsal.setText(namakamar);
                BtnSeek2.setEnabled(false);
                NmBangsal.setEditable(false);
            }
        } else {
            BtnSeek2.setEnabled(true);
            NmBangsal.setEditable(true);
        }
    }

}
