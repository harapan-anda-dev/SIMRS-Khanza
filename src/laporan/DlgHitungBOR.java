/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */
package laporan;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgKamar;

/**
 *
 * @author perpustakaan
 */
public final class DlgHitungBOR extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode2;

    /**
     *
     */
    public DlgKamar dlgKamar = new DlgKamar(null, false);
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, kamar = 0, jumlahhari = 0;
    private double hari;
    private String kd_bangsal = "";

    /**
     * Creates new form DlgLhtBiaya
     *
     * @param parent
     * @param modal
     */
    public DlgHitungBOR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(885, 674);

        tabMode = new DefaultTableModel(null, new String[]{"No", "No.Rawat", "Nomer RM", "Nama Pasien", "Kamar", "Kelas", "Tgl.Masuk", "Tgl.Keluar", "Lama", "Status"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        Tabel1.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        Tabel1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        Tabel1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = Tabel1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(75);
            } else if (i == 6) {
                column.setPreferredWidth(75);
            } else if (i == 7) {
                column.setPreferredWidth(75);
            } else if (i == 8) {
                column.setPreferredWidth(70);
            } else if (i == 9) {
                column.setPreferredWidth(100);
            }
        }

        Tabel1.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new String[]{"No", "No.Rawat", "Nomer RM", "Nama Pasien", "Kamar", "Tgl.Masuk", "Tgl.Keluar", "Lama", "Status"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        Tabel2.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        Tabel2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        Tabel2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = Tabel2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(180);
            } else if (i == 5) {
                column.setPreferredWidth(75);
            } else if (i == 6) {
                column.setPreferredWidth(75);
            } else if (i == 7) {
                column.setPreferredWidth(70);
            } else if (i == 8) {
                column.setPreferredWidth(80);
            }
        }

        Tabel2.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte) 20).getKata(TKd));

        dlgKamar.bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgKamarInap")) {
                    if (dlgKamar.bangsal.getTable().getSelectedRow() != -1) {
                        BangsalCari.setText(dlgKamar.bangsal.getTable().getValueAt(dlgKamar.bangsal.getTable().getSelectedRow(), 1).toString());
                        kd_bangsal = dlgKamar.bangsal.getTable().getValueAt(dlgKamar.bangsal.getTable().getSelectedRow(), 0).toString();
                    }
                    BangsalCari.requestFocus();
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

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        BangsalCari = new widget.TextBox();
        btnBangsalCari = new widget.Button();
        jLabel22 = new widget.Label();
        KeywordCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        jLabel7 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        Tabel1 = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        Tabel2 = new widget.Table();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Hitung BOR ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(50, 23));

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));

        jLabel21.setText("Kamar :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));

        BangsalCari.setEditable(false);
        BangsalCari.setBackground(new java.awt.Color(255, 255, 204));
        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(180, 23));

        btnBangsalCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsalCari.setMnemonic('3');
        btnBangsalCari.setToolTipText("Alt+3");
        btnBangsalCari.setName("btnBangsalCari"); // NOI18N
        btnBangsalCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBangsalCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalCariActionPerformed(evt);
            }
        });

        jLabel22.setText("Keyword :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(50, 23));

        KeywordCari.setName("KeywordCari"); // NOI18N
        KeywordCari.setPreferredSize(new java.awt.Dimension(210, 23));
        KeywordCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeywordCariKeyPressed(evt);
            }
        });

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/checked.png"))); // NOI18N
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
        BtnAll.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/search_page.png"))); // NOI18N
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
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

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(130, 23));

        javax.swing.GroupLayout panelGlass5Layout = new javax.swing.GroupLayout(panelGlass5);
        panelGlass5.setLayout(panelGlass5Layout);
        panelGlass5Layout.setHorizontalGroup(
            panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(Tgl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(Tgl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(BangsalCari, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBangsalCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(KeywordCari, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(BtnCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(BtnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(BtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(BtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        panelGlass5Layout.setVerticalGroup(
            panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGlass5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGlass5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(panelGlass5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Tgl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Tgl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BangsalCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBangsalCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(KeywordCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        Tabel1.setName("Tabel1"); // NOI18N
        Tabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabel1MouseClicked(evt);
            }
        });
        Tabel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabel1KeyPressed(evt);
            }
        });
        Scroll.setViewportView(Tabel1);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Berdasar Tanggal Masuk", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        Tabel2.setName("Tabel2"); // NOI18N
        Tabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabel2MouseClicked(evt);
            }
        });
        Tabel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabel2KeyPressed(evt);
            }
        });
        Scroll1.setViewportView(Tabel2);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Berdasar Tanggal Keluar", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TabRawat.getSelectedIndex() == 0) {
            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            } else if (tabMode.getRowCount() != 0) {

                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("periode", Tgl1.getSelectedItem() + " s.d. " + Tgl2.getSelectedItem());
                param.put("tanggal", Tgl2.getDate());
                Sequel.queryu("delete from temporary");
                for (int r = 0; r < tabMode.getRowCount(); r++) {
                    if (!Tabel1.getValueAt(r, 0).toString().contains(">>")) {
                        Sequel.menyimpan("temporary", "'0','"
                                + tabMode.getValueAt(r, 0).toString() + "','"
                                + tabMode.getValueAt(r, 1).toString() + "','"
                                + tabMode.getValueAt(r, 2).toString() + "','"
                                + tabMode.getValueAt(r, 3).toString() + "','"
                                + tabMode.getValueAt(r, 4).toString() + "','"
                                + tabMode.getValueAt(r, 5).toString() + "','"
                                + tabMode.getValueAt(r, 6).toString() + "','"
                                + tabMode.getValueAt(r, 7).toString() + "','"
                                + tabMode.getValueAt(r, 8).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Rekap Nota Pembayaran");
                    }
                }

                Valid.MyReport("rptHitungBor.jasper", "report", "::[ Data Hitung BOR ]::", param);
            }
        } else if (TabRawat.getSelectedIndex() == 1) {
            if (tabMode2.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            } else if (tabMode2.getRowCount() != 0) {

                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("periode", Tgl1.getSelectedItem() + " s.d. " + Tgl2.getSelectedItem());
                param.put("tanggal", Tgl2.getDate());
                Sequel.queryu("delete from temporary");
                for (int r = 0; r < tabMode2.getRowCount(); r++) {
                    if (!Tabel2.getValueAt(r, 0).toString().contains(">>")) {
                        Sequel.menyimpan("temporary", "'0','"
                                + tabMode2.getValueAt(r, 0).toString() + "','"
                                + tabMode2.getValueAt(r, 1).toString() + "','"
                                + tabMode2.getValueAt(r, 2).toString() + "','"
                                + tabMode2.getValueAt(r, 3).toString() + "','"
                                + tabMode2.getValueAt(r, 4).toString() + "','"
                                + tabMode2.getValueAt(r, 5).toString() + "','"
                                + tabMode2.getValueAt(r, 6).toString() + "','"
                                + tabMode2.getValueAt(r, 7).toString() + "','"
                                + tabMode2.getValueAt(r, 8).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Rekap Nota Pembayaran");
                    }
                }

                Valid.MyReport("rptHitungBor.jasper", "report", "::[ Data Hitung BOR ]::", param);
            }
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
            Valid.pindah(evt, BtnKeluar, TKd);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void Tabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabel1MouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_Tabel1MouseClicked

    private void Tabel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabel1KeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_Tabel1KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    if (TabRawat.getSelectedIndex() == 0) {
        tampil();
    } else if (TabRawat.getSelectedIndex() == 1) {
        tampil2();
    }
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    } else {
        Valid.pindah(evt, TKd, BtnPrint);
    }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tampil();

    }//GEN-LAST:event_formWindowActivated

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void Tabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabel2MouseClicked

    private void Tabel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabel2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabel2KeyPressed

    private void btnBangsalCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalCariActionPerformed
        akses.setform("DlgKamarInap");
        dlgKamar.bangsal.isCek();
        dlgKamar.bangsal.emptTeks();
        dlgKamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dlgKamar.bangsal.setLocationRelativeTo(internalFrame1);
        dlgKamar.bangsal.setVisible(true);
    }//GEN-LAST:event_btnBangsalCariActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kd_bangsal = "";
        BangsalCari.setText("");
        KeywordCari.setText("");
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void KeywordCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeywordCariKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        }
        }
    }//GEN-LAST:event_KeywordCariKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgHitungBOR dialog = new DlgHitungBOR(new javax.swing.JFrame(), true);
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
    private widget.TextBox BangsalCari;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox KeywordCari;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TKd;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Table Tabel1;
    private widget.Table Tabel2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBangsalCari;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel7;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass5;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public void tampil() {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.tabelKosong(tabMode);
            if (kd_bangsal.equals("") && KeywordCari.getText().equals("")) {
                ps = koneksi.prepareStatement(
                        "select kamar_inap.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,"
                        + "kamar_inap.tgl_masuk, if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar, kamar_inap.lama, kamar_inap.stts_pulang,kamar.kelas "
                        + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal "
                        + "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                        + "where kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ? "
                        + "order by kamar_inap.tgl_masuk");
            } else {
                if (!kd_bangsal.equals("") && KeywordCari.getText().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select kamar_inap.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,"
                            + "kamar_inap.tgl_masuk, if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar, kamar_inap.lama, kamar_inap.stts_pulang,kamar.kelas "
                            + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal "
                            + "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                            + "where kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal like ? and bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ? "
                            + "order by kamar_inap.tgl_masuk");
                }else if (kd_bangsal.equals("") && !KeywordCari.getText().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select kamar_inap.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,"
                            + "kamar_inap.tgl_masuk, if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar, kamar_inap.lama, kamar_inap.stts_pulang,kamar.kelas "
                            + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal "
                            + "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                            + "where kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ? and reg_periksa.no_rkm_medis like ? or "
                            + "kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ? and kamar_inap.no_rawat like ? or "
                            + "kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ? and pasien.nm_pasien like ? or "
                            + "kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ? and kamar.kelas like ? "
                            + "order by kamar_inap.tgl_masuk");
                } else if (!kd_bangsal.equals("") && !KeywordCari.getText().equals("")) {
                    ps = koneksi.prepareStatement(
                            "select kamar_inap.no_rawat, reg_periksa.no_rkm_medis, pasien.nm_pasien, concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,"
                            + "kamar_inap.tgl_masuk, if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar, kamar_inap.lama, kamar_inap.stts_pulang,kamar.kelas "
                            + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal "
                            + "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                            + "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                            + "where kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal like ? and (bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ?) and reg_periksa.no_rkm_medis like ? or "
                            + "kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal like ? and (bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ?) and kamar_inap.no_rawat like ? or "
                            + "kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal like ? and (bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ?) and pasien.nm_pasien like ? or "
                            + "kamar_inap.tgl_masuk between ? and ? and bangsal.kd_bangsal like ? and (bangsal.kd_bangsal not like ? and bangsal.kd_bangsal not like ?) and kamar.kelas like ? "
                            + "order by kamar_inap.tgl_masuk");
                }
            }
            try {
                if (kd_bangsal.equals("") && KeywordCari.getText().equals("")) {
                    ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                    ps.setString(3, "B0063");//Baby
                    ps.setString(4, "B0062");//teratai
                } else {
                    if (!kd_bangsal.equals("") && KeywordCari.getText().equals("")) {
                        ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(3, "%" + kd_bangsal + "%");
                        ps.setString(4, "B0063");//Baby
                        ps.setString(5, "B0062");//teratai
                    }else if (kd_bangsal.equals("") && !KeywordCari.getText().equals("")) {
                        ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(3, "B0063");//Baby
                        ps.setString(4, "B0062");//teratai
                        ps.setString(5, "%" + KeywordCari.getText() + "%");
                        ps.setString(6, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(7, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(8, "B0063");//Baby
                        ps.setString(9, "B0062");//teratai
                        ps.setString(10, "%" + KeywordCari.getText() + "%");
                        ps.setString(11, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(12, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(13, "B0063");//Baby
                        ps.setString(14, "B0062");//teratai
                        ps.setString(15, "%" + KeywordCari.getText() + "%");
                        ps.setString(16, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(17, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(18, "B0063");//Baby
                        ps.setString(19, "B0062");//teratai
                        ps.setString(20, "%" + KeywordCari.getText() + "%");
                    } else if (!kd_bangsal.equals("") && !KeywordCari.getText().equals("")) {
                        ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(3, "%" + kd_bangsal + "%");
                        ps.setString(4, "B0063");//Baby
                        ps.setString(5, "B0062");//teratai
                        ps.setString(6, "%" + KeywordCari.getText() + "%");
                        ps.setString(7, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(8, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(9, "%" + kd_bangsal + "%");
                        ps.setString(10, "B0063");//Baby
                        ps.setString(11, "B0062");//teratai
                        ps.setString(12, "%" + KeywordCari.getText() + "%");
                        ps.setString(13, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(14, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(15, "%" + kd_bangsal + "%");
                        ps.setString(16, "B0063");//Baby
                        ps.setString(17, "B0062");//teratai
                        ps.setString(18, "%" + KeywordCari.getText() + "%");
                        ps.setString(19, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                        ps.setString(20, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                        ps.setString(21, "B0063");//Baby
                        ps.setString(22, "B0062");//teratai
                        ps.setString(23, "%" + KeywordCari.getText() + "%");
                    }
                }
                rs = ps.executeQuery();
                i = 1;
                hari = 0;
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        i, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("kamar"), rs.getString("kelas"), rs.getString("tgl_masuk"), rs.getString("tgl_keluar"),
                        rs.getString("lama"), rs.getString("stts_pulang")
                    });
                    hari = hari + rs.getDouble("lama");
                    i++;
                }
                if (hari > 0) {
                    if (BangsalCari.getText().equals("")) {
                        kamar = Sequel.cariInteger("select count(*) from kamar where statusdata='1'");
                    } else {
                        kamar = Sequel.cariInteger("select count(*) from kamar where statusdata='1' and kd_bangsal='" + kd_bangsal + "'");
                    }

                    jumlahhari = Sequel.cariInteger("select (to_days('" + Valid.SetTgl(Tgl2.getSelectedItem() + "") + "')-to_days('" + Valid.SetTgl(Tgl1.getSelectedItem() + "") + "'))");
                    tabMode.addRow(new Object[]{"", "", "", "Jumlah Hari Perawatan", ":", "", "","", hari, ""});
                    tabMode.addRow(new Object[]{"", "", "", "Jumlah Kamar", ":", "", "","", kamar, ""});
                    tabMode.addRow(new Object[]{"", "", "", "Jumlah Hari Dalam Periode", ":", "", "","", jumlahhari, ""});
                    tabMode.addRow(new Object[]{"", "", "", "Perhitungan BOR ", ": (" + hari + "/(" + kamar + " X " + jumlahhari + ")) X 100%", "", "","", Valid.SetAngka4((hari / (kamar * jumlahhari)) * 100) + " %", ""});
                }
            } catch (Exception e) {
                System.out.println("laporan.DlgHitungBOR.tampil() : " + e);
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
    public void tampil2() {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.tabelKosong(tabMode2);

            ps = koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,"
                    + "kamar_inap.tgl_masuk,if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar,kamar_inap.lama,kamar_inap.stts_pulang "
                    + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal "
                    + "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal  "
                    + "where kamar_inap.tgl_keluar between ? and ? and bangsal.nm_bangsal like ?  order by kamar_inap.tgl_keluar");

            try {
                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(3, "%" + BangsalCari.getText().trim() + "%");
                rs = ps.executeQuery();
                i = 1;
                hari = 0;
                while (rs.next()) {
                    tabMode2.addRow(new Object[]{
                        i, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("kamar"), rs.getString("tgl_masuk"), rs.getString("tgl_keluar"),
                        rs.getString("lama"), rs.getString("stts_pulang")
                    });
                    hari = hari + rs.getDouble("lama");
                    i++;
                }
                if (hari > 0) {
                    kamar = Sequel.cariInteger("select count(*) from kamar  where statusdata='1'");
                    jumlahhari = Sequel.cariInteger("select (to_days('" + Valid.SetTgl(Tgl2.getSelectedItem() + "") + "')-to_days('" + Valid.SetTgl(Tgl1.getSelectedItem() + "") + "'))") + 1;
                    tabMode2.addRow(new Object[]{"", "", "", "Jumlah Hari Perawatan", ":", "", "", hari, ""});
                    tabMode2.addRow(new Object[]{"", "", "", "Jumlah Kamar", ":", "", "", kamar, ""});
                    tabMode2.addRow(new Object[]{"", "", "", "Jumlah Hari Dalam Periode", ":", "", "", jumlahhari, ""});
                    tabMode2.addRow(new Object[]{"", "", "", "Perhitungan BOR ", ": (" + hari + "/(" + kamar + " X " + jumlahhari + ")) X 100%", "", "", Valid.SetAngka4((hari / (kamar * jumlahhari)) * 100) + " %", ""});
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
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

    private void getData() {
        int row = Tabel1.getSelectedRow();
        if (row != -1) {
            TKd.setText(tabMode.getValueAt(row, 0).toString());
        }
    }

}
