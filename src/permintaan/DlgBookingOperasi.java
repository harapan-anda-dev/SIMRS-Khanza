package permintaan;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import keuangan.DlgCariDaftarOperasi;
import rekammedis.RMRiwayatPerawatan;
import simrskhanza.DlgCariJenisBedah;
import simrskhanza.DlgKamarInap;
import simrskhanza.DlgTagihanOperasi;

/**
 *
 * @author dosen
 */
public class DlgBookingOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String status="",kamar="",diagnosa="",order="",kelas="",penjab="",norawatibu="",posisi="",norm="";
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariDaftarOperasi operasi=new DlgCariDaftarOperasi(null,false);
    private DlgCariJenisBedah jenis_bedah=new DlgCariJenisBedah(null,false);
    
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgBookingOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        DTPCari3.setDate(new Date());
        DTPCari4.setDate(new Date());
        DTPTgl.setDate(new Date());
        BtnOperasi.setVisible(false);
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.","No.Rawat","Nama Pasien","Umur","J.K.","Tanggal","Mulai",
                "Selesai","Status","Rujukan Dari","Diagnosa","Kode Bedah","Jenis Bedah",
                "Kode Operator","Operator","Order"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
//        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(28);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 1:
                    column.setPreferredWidth(105);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 2:
                    column.setPreferredWidth(170);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 3:
                    column.setPreferredWidth(50);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 4:
                    column.setPreferredWidth(30);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 5:
                    column.setPreferredWidth(65);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 6:
                    column.setPreferredWidth(50);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 7:
                    column.setPreferredWidth(50);
                    column.setCellRenderer(centerRenderer);
                    break;
                case 8:
                    column.setPreferredWidth(60);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 9:
                    column.setPreferredWidth(120);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 10:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 11:
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 12:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 13:
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 14:
                    column.setPreferredWidth(150);
                    column.setCellRenderer(leftRenderer);
                    break;
                case 15:
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                    column.setCellRenderer(leftRenderer);
                    break;
                default:
                    column.setCellRenderer(leftRenderer);
                    break;
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDokter.setDocument(new batasInput((byte)3).getKata(KdDokter));
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }
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
        
        operasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(operasi.getTable().getSelectedRow()!= -1){                    
                    KdOperasi.setText(operasi.getTable().getValueAt(operasi.getTable().getSelectedRow(),0).toString());
                    NmOperasi.setText(operasi.getTable().getValueAt(operasi.getTable().getSelectedRow(),1).toString());
                }
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
        
        jenis_bedah.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenis_bedah.getTable().getSelectedRow()!= -1){                    
                    KdOperasi.setText(jenis_bedah.getTable().getValueAt(jenis_bedah.getTable().getSelectedRow(),0).toString());
                    NmOperasi.setText(jenis_bedah.getTable().getValueAt(jenis_bedah.getTable().getSelectedRow(),1).toString());
                }
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
        MnKamarInap = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
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
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal1();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal1();
        R4 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        DTPCari3 = new widget.Tanggal1();
        jLabel25 = new widget.Label();
        DTPCari4 = new widget.Tanggal1();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmDokter = new widget.TextBox();
        TPasien = new widget.TextBox();
        Status = new widget.ComboBox();
        jLabel10 = new widget.Label();
        KdDokter = new widget.TextBox();
        BtnOperator = new widget.Button();
        Kamar = new widget.TextBox();
        jLabel35 = new widget.Label();
        JamMulai = new widget.ComboBox();
        MenitMulai = new widget.ComboBox();
        DetikMulai = new widget.ComboBox();
        DetikSelesai = new widget.ComboBox();
        MenitSelesai = new widget.ComboBox();
        JamSelesai = new widget.ComboBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel11 = new widget.Label();
        KdOperasi = new widget.TextBox();
        NmOperasi = new widget.TextBox();
        BtnOperasi = new widget.Button();
        DTPTgl = new widget.Tanggal1();
        BtnOperasi1 = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 254));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(new java.awt.Color(50, 50, 50));
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(180, 26));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnPermintaanLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab.setText("Permintaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPermintaanLab);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(50, 50, 50));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(180, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(180, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnOperasi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Jadwal Operasi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
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
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(645, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

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
        panelGlass10.add(BtnCari);

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
        panelGlass10.add(BtnAll);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Menunggu");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelCari.add(R1);

        buttonGroup1.add(R2);
        R2.setText("Tanggal :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(80, 23));
        panelCari.add(R2);

        DTPCari1.setName("DTPCari1"); // NOI18N
        panelCari.add(DTPCari1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(30, 23));
        panelCari.add(jLabel22);

        DTPCari2.setName("DTPCari2"); // NOI18N
        panelCari.add(DTPCari2);

        buttonGroup1.add(R4);
        R4.setText("Proses");
        R4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        R4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R4.setName("R4"); // NOI18N
        R4.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(R4);

        buttonGroup1.add(R3);
        R3.setText("Selesai :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(80, 23));
        panelCari.add(R3);

        DTPCari3.setName("DTPCari3"); // NOI18N
        panelCari.add(DTPCari3);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(30, 23));
        panelCari.add(jLabel25);

        DTPCari4.setName("DTPCari4"); // NOI18N
        panelCari.add(DTPCari4);

        jPanel3.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 126));
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
        FormInput.setPreferredSize(new java.awt.Dimension(190, 77));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(73, 10, 125, 23);

        jLabel9.setText("Operator :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 70, 70, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(155, 70, 190, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(200, 10, 358, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menunggu", "Proses Operasi", "Selesai" }));
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(673, 40, 130, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(73, 70, 80, 23);

        BtnOperator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator.setMnemonic('X');
        BtnOperator.setToolTipText("Alt+X");
        BtnOperator.setName("BtnOperator"); // NOI18N
        BtnOperator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperatorActionPerformed(evt);
            }
        });
        BtnOperator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOperatorKeyPressed(evt);
            }
        });
        FormInput.add(BtnOperator);
        BtnOperator.setBounds(347, 70, 28, 23);

        Kamar.setEditable(false);
        Kamar.setHighlighter(null);
        Kamar.setName("Kamar"); // NOI18N
        FormInput.add(Kamar);
        Kamar.setBounds(560, 10, 243, 23);

        jLabel35.setText("Mulai :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(200, 40, 40, 23);

        JamMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamMulai.setName("JamMulai"); // NOI18N
        JamMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMulaiKeyPressed(evt);
            }
        });
        FormInput.add(JamMulai);
        JamMulai.setBounds(240, 40, 50, 23);

        MenitMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        MenitMulai.setName("MenitMulai"); // NOI18N
        MenitMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitMulaiKeyPressed(evt);
            }
        });
        FormInput.add(MenitMulai);
        MenitMulai.setBounds(300, 40, 50, 23);

        DetikMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        DetikMulai.setName("DetikMulai"); // NOI18N
        DetikMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikMulaiKeyPressed(evt);
            }
        });
        FormInput.add(DetikMulai);
        DetikMulai.setBounds(360, 40, 50, 23);

        DetikSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        DetikSelesai.setName("DetikSelesai"); // NOI18N
        DetikSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(DetikSelesai);
        DetikSelesai.setBounds(570, 40, 50, 23);

        MenitSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        MenitSelesai.setName("MenitSelesai"); // NOI18N
        MenitSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(MenitSelesai);
        MenitSelesai.setBounds(510, 40, 50, 23);

        JamSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamSelesai.setName("JamSelesai"); // NOI18N
        JamSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(JamSelesai);
        JamSelesai.setBounds(450, 40, 50, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("s.d.");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(420, 40, 25, 23);

        jLabel37.setText("Status :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(620, 40, 50, 23);

        jLabel11.setText("Jenis Bedah :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(375, 70, 80, 23);

        KdOperasi.setEditable(false);
        KdOperasi.setHighlighter(null);
        KdOperasi.setName("KdOperasi"); // NOI18N
        FormInput.add(KdOperasi);
        KdOperasi.setBounds(460, 70, 80, 23);

        NmOperasi.setEditable(false);
        NmOperasi.setHighlighter(null);
        NmOperasi.setName("NmOperasi"); // NOI18N
        FormInput.add(NmOperasi);
        NmOperasi.setBounds(550, 70, 253, 23);

        BtnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperasi.setMnemonic('X');
        BtnOperasi.setToolTipText("Alt+X");
        BtnOperasi.setName("BtnOperasi"); // NOI18N
        BtnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperasiActionPerformed(evt);
            }
        });
        BtnOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOperasiKeyPressed(evt);
            }
        });
        FormInput.add(BtnOperasi);
        BtnOperasi.setBounds(850, 70, 28, 23);

        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setPreferredSize(new java.awt.Dimension(117, 23));
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(70, 40, 117, 23);

        BtnOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperasi1.setMnemonic('X');
        BtnOperasi1.setToolTipText("Alt+X");
        BtnOperasi1.setName("BtnOperasi1"); // NOI18N
        BtnOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperasi1ActionPerformed(evt);
            }
        });
        BtnOperasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOperasi1KeyPressed(evt);
            }
        });
        FormInput.add(BtnOperasi1);
        BtnOperasi1.setBounds(810, 70, 30, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,Status,KdDokter);
        
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Operator");
        }else if(NmOperasi.getText().trim().equals("")){
            Valid.textKosong(KdOperasi,"Operasi");
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                if(Sequel.menyimpantf("booking_operasi","?,?,?,?,?,?,?,?","data", 8,new String[]{
                    TNoRw.getText(),"-",Valid.SetDateToString(DTPTgl.getDate()),
                    JamMulai.getSelectedItem()+":"+MenitMulai.getSelectedItem()+":"+DetikMulai.getSelectedItem(),
                    JamSelesai.getSelectedItem()+":"+MenitSelesai.getSelectedItem()+":"+DetikSelesai.getSelectedItem(),
                    Status.getSelectedItem().toString(),KdDokter.getText(),KdOperasi.getText()
                    })==true){
                    tampil();
                    emptTeks();
                }
            }   
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnOperasi,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            DTPTgl.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from booking_operasi " +
                        "where no_rawat='"+TNoRw.getText()+"' " +
                        "and tanggal='"+Valid.SetDateToString(DTPTgl.getDate())+"' " +
                        "and jam_mulai='"+JamMulai.getSelectedItem()+":"+MenitMulai.getSelectedItem()+":"+DetikMulai.getSelectedItem()+"' " +
                        "and jam_selesai='"+JamSelesai.getSelectedItem()+":"+MenitSelesai.getSelectedItem()+":"+DetikSelesai.getSelectedItem()+"' "+
                        "and kd_dokter='"+KdDokter.getText()+"' "+
                        "and status='"+Status.getSelectedItem()+"' "+
                        "and kode_bedah='"+KdOperasi.getText()+"'");
                tampil();
                emptTeks();
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
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
            
            Sequel.queryu("truncate table temporary");
            for(i=0;i<tabMode.getRowCount();i++){ 
                Sequel.menyimpan("temporary","'0','"+
                    tabMode.getValueAt(i,0).toString()+"','"+
                    tabMode.getValueAt(i,1).toString()+"','"+
                    tabMode.getValueAt(i,2).toString()+"','"+
                    tabMode.getValueAt(i,3).toString()+"','"+
                    tabMode.getValueAt(i,4).toString()+"','"+
                    tabMode.getValueAt(i,5).toString()+"','"+
                    tabMode.getValueAt(i,6).toString()+"','"+
                    tabMode.getValueAt(i,7).toString()+"','"+
                    tabMode.getValueAt(i,8).toString()+"','"+
                    tabMode.getValueAt(i,9).toString()+"','"+
                    tabMode.getValueAt(i,10).toString()+"','"+
                    tabMode.getValueAt(i,11).toString()+"','"+
                    tabMode.getValueAt(i,12).toString()+"','"+
                    tabMode.getValueAt(i,13).toString()+"','"+
                    tabMode.getValueAt(i,14).toString()+"','','','','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
            }
             
            Valid.MyReport("rptJadwalOperasi.jasper","report","::[ Laporan Daftar Jadwal Operasi ]::",param);
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

private void BtnOperatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperatorActionPerformed
    dokter.isCek();
    dokter.TCari.requestFocus();
    dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    dokter.setLocationRelativeTo(internalFrame1);
    dokter.setVisible(true);
}//GEN-LAST:event_BtnOperatorActionPerformed

private void BtnOperatorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOperatorKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_SPACE){
        BtnOperatorActionPerformed(null);
    }else{
        Valid.pindah(evt,Status,BtnOperasi);
    }        
}//GEN-LAST:event_BtnOperatorKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Operator");
        }else if(NmOperasi.getText().trim().equals("")){
            Valid.textKosong(KdOperasi,"Operasi");
        }else{
            if(Sequel.mengedittf("booking_operasi",
                    "no_rawat=? and tanggal=? and jam_mulai=? and jam_selesai=? and status=? and kd_dokter=? and kode_bedah=?",
                    "no_rawat=?,tanggal=?,jam_mulai=?,jam_selesai=?,status=?,kd_dokter=?,kode_bedah=?",14,new String[]{
                    TNoRw.getText(),Valid.SetDateToString(DTPTgl.getDate()),
                    JamMulai.getSelectedItem()+":"+MenitMulai.getSelectedItem()+":"+DetikMulai.getSelectedItem(),
                    JamSelesai.getSelectedItem()+":"+MenitSelesai.getSelectedItem()+":"+DetikSelesai.getSelectedItem(),
                    Status.getSelectedItem().toString(),KdDokter.getText(),KdOperasi.getText(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),1).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),6).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),7).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),8).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),13).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()
                 })==true){
                tampil();
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        Valid.pindah(evt,DetikSelesai,BtnOperator);
    }//GEN-LAST:event_StatusKeyPressed

    private void BtnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperasiActionPerformed
        penjab=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",TNoRw.getText());        
        if(posisi.equals("Ranap")){
            norawatibu=Sequel.cariIsi("select no_rawat from ranap_gabung where no_rawat2=?",TNoRw.getText());
        
            if(!norawatibu.equals("")){
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and (stts_pulang='-' or stts_pulang='AKTIF') order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norawatibu);
            }else{
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and (stts_pulang='-' or stts_pulang='AKTIF') order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            } 
        }else if(posisi.equals("Ralan")){
            kelas="Rawat Jalan";
        }
    
        operasi.setBayar(penjab, kelas);
        operasi.tampil();
        operasi.isCek();
        operasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        operasi.setLocationRelativeTo(internalFrame1);
        operasi.setVisible(true);
    }//GEN-LAST:event_BtnOperasiActionPerformed

    private void BtnOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOperasiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnOperasiActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnOperator,BtnSimpan);
        }  
    }//GEN-LAST:event_BtnOperasiKeyPressed

    private void JamMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMulaiKeyPressed
        Valid.pindah(evt,DTPTgl,MenitMulai);
    }//GEN-LAST:event_JamMulaiKeyPressed

    private void MenitMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitMulaiKeyPressed
        Valid.pindah(evt,JamMulai,DetikMulai);
    }//GEN-LAST:event_MenitMulaiKeyPressed

    private void DetikMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikMulaiKeyPressed
        Valid.pindah(evt,MenitMulai,JamSelesai);
    }//GEN-LAST:event_DetikMulaiKeyPressed

    private void JamSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamSelesaiKeyPressed
        Valid.pindah(evt,DetikMulai,MenitSelesai);
    }//GEN-LAST:event_JamSelesaiKeyPressed

    private void MenitSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitSelesaiKeyPressed
        Valid.pindah(evt,JamSelesai,DetikSelesai);
    }//GEN-LAST:event_MenitSelesaiKeyPressed

    private void DetikSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikSelesaiKeyPressed
        Valid.pindah(evt,MenitSelesai,Status);
    }//GEN-LAST:event_DetikSelesaiKeyPressed

    private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbObat.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi..!!");
            }else{
                akses.setstatus(true);
                DlgKamarInap kamarinap=new DlgKamarInap(null,false);
                kamarinap.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                kamarinap.setLocationRelativeTo(internalFrame1);
                kamarinap.emptTeks();
                kamarinap.isCek();
                norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?",TNoRw.getText());
                kamarinap.setNoRm(TNoRw.getText(),norm,TPasien.getText());
                kamarinap.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnKamarInapActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{ 
                DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
                dlgro.SetCariOperasi(KdOperasi.getText(),KdDokter.getText(),NmDokter.getText());
                dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.setNoRm(TNoRw.getText(),TPasien.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
                dlgro.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMRiwayatPerawatan resume=new RMRiwayatPerawatan(null,true);
            resume.setNoRm(Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()),tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLabActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
            dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),Sequel.cariIsi("select status_lanjut from reg_periksa where no_rawat=?",TNoRw.getText()));
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void BtnOperasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperasi1ActionPerformed
        // TODO add your handling code here:
        jenis_bedah.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenis_bedah.setLocationRelativeTo(internalFrame1);
        jenis_bedah.setVisible(true);
    }//GEN-LAST:event_BtnOperasi1ActionPerformed

    private void BtnOperasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOperasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnOperasi1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBookingOperasi dialog = new DlgBookingOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnOperasi;
    private widget.Button BtnOperasi1;
    private widget.Button BtnOperator;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal1 DTPCari1;
    private widget.Tanggal1 DTPCari2;
    private widget.Tanggal1 DTPCari3;
    private widget.Tanggal1 DTPCari4;
    private widget.Tanggal1 DTPTgl;
    private widget.ComboBox DetikMulai;
    private widget.ComboBox DetikSelesai;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox JamMulai;
    private widget.ComboBox JamSelesai;
    private widget.TextBox Kamar;
    private widget.TextBox KdDokter;
    private widget.TextBox KdOperasi;
    private widget.Label LCount;
    private widget.ComboBox MenitMulai;
    private widget.ComboBox MenitSelesai;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPermintaanLab;
    private widget.TextBox NmDokter;
    private widget.TextBox NmOperasi;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton R4;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel22;
    private widget.Label jLabel25;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppRiwayat;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    public void tampil() {     
        if(R1.isSelected()==true){
            status=" booking_operasi.status='Menunggu' ";
        }else if(R4.isSelected()==true){
            status=" booking_operasi.status='Proses Operasi' ";
        }else if(R2.isSelected()==true){
            status=" booking_operasi.tanggal between '"+Valid.SetDateToString(DTPCari1.getDate())+"' and '"+Valid.SetDateToString(DTPCari2.getDate())+"' ";
        }else if(R3.isSelected()==true){
            status=" booking_operasi.status='Selesai' and booking_operasi.tanggal between '"+Valid.SetDateToString(DTPCari3.getDate())+"' and '"+Valid.SetDateToString(DTPCari4.getDate())+"' ";           
        }
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
                    "select booking_operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,booking_operasi.tanggal,"+
                    "booking_operasi.jam_mulai,booking_operasi.jam_selesai,booking_operasi.status,booking_operasi.kd_dokter,"+
                    "dokter.nm_dokter,booking_operasi.kode_paket,booking_operasi.kode_bedah,jenis_bedah.nama,paket_operasi.nm_perawatan,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk,poliklinik.nm_poli "+
                    "from booking_operasi "
                            + "inner join reg_periksa "
                            + "inner join pasien "
                            + "inner join dokter "+
                    "inner join poliklinik "
                            + "inner join jenis_bedah "
                            + " INNER JOIN paket_operasi "
                            + "on booking_operasi.no_rawat=reg_periksa.no_rawat "
                            + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and booking_operasi.kd_dokter=dokter.kd_dokter "+
                    "and reg_periksa.kd_poli=poliklinik.kd_poli "
                            + " AND booking_operasi.kode_paket=paket_operasi.kode_paket "
                    + "and booking_operasi.kode_bedah=jenis_bedah.kode_bedah "
                    + "where "+status+" and booking_operasi.no_rawat like ? or "+
                    status+" and reg_periksa.no_rkm_medis like ? or "+status+" and pasien.nm_pasien like ? or "+
                    status+" and booking_operasi.status like ? or "+status+" and dokter.nm_dokter like ? or "+
                    status+" and paket_operasi.nm_perawatan like ? order by booking_operasi.tanggal,booking_operasi.jam_mulai");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                while(rs.next()){
                    order="Ranap";
                    kamar=Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal "+
                            " and kamar_inap.kd_kamar=kamar.kd_kamar where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1 ",rs.getString("no_rawat"));  
                    if(kamar.equals("")){
                        kamar=rs.getString("nm_poli");
                        order="Ralan";
                    }
                    
                    diagnosa=Sequel.cariIsi("select concat(diagnosa_pasien.kd_penyakit,' ',penyakit.nm_penyakit) from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.no_rawat=? limit 1",rs.getString("no_rawat"));
                    
                    tabMode.addRow(new Object[]{
                        i+".",rs.getString("no_rawat"),rs.getString("nm_pasien"),rs.getString("umur"),
                        rs.getString("jk"),rs.getString("tanggal"),rs.getString("jam_mulai"),rs.getString("jam_selesai"),
                        rs.getString("status"),kamar,diagnosa,rs.getString("kode_bedah"),rs.getString("nama"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),order
                    });
                    i++;
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        LCount.setText(""+tabMode.getRowCount());
    }

    /**
     *
     */
    public void emptTeks() {
        KdDokter.setText("");
        NmDokter.setText("");
        KdOperasi.setText("");
        NmOperasi.setText("");
        JamMulai.setSelectedItem("00");
        MenitMulai.setSelectedItem("00");
        DetikMulai.setSelectedItem("00");
        JamSelesai.setSelectedItem("00");
        MenitSelesai.setSelectedItem("00");
        DetikSelesai.setSelectedItem("00");
        DTPTgl.setDate(new Date());
        DTPTgl.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()); 
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            JamMulai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(0,2));
            MenitMulai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(3,5));
            DetikMulai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString().substring(6,8));
            JamSelesai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(0,2));
            DetikSelesai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(3,5));
            DetikSelesai.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(6,8));
            Status.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Kamar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KdOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NmOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Valid.SetTgl(DTPTgl,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    /**
     *
     * @param norwt
     * @param norm
     * @param nama
     * @param lokasi
     * @param posisi
     */
    public void setNoRm(String norwt,String norm,String nama,String lokasi,String posisi) {
        TNoRw.setText(norwt);
        TPasien.setText(norm+" "+nama);
        Kamar.setText(lokasi);
        this.posisi=posisi;
        TCari.setText(norwt);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,126));
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
        BtnSimpan.setEnabled(akses.getbooking_operasi());
        BtnHapus.setEnabled(akses.getbooking_operasi());
        BtnPrint.setEnabled(akses.getbooking_operasi());
        MnKamarInap.setEnabled(akses.getkamar_inap());
        ppRiwayat.setEnabled(akses.getresume_pasien());
        MnOperasi.setEnabled(akses.getoperasi());
        BtnEdit.setEnabled(akses.getbooking_operasi());
    }

    
}
