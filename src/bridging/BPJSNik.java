/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgKamar.java
 *
 * Created on May 23, 2010, 12:07:21 AM
 */

package bridging;

import fungsi.WarnaTable;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author dosen
 */
public final class BPJSNik extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private final Properties prop = new Properties();
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private BPJSCekNIK cekViaBPJS=new BPJSCekNIK();

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public BPJSNik(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 2; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(160);
            }else if(i==1){
                column.setPreferredWidth(400);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelGlass6 = new widget.panelisi();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Peserta BPJS Berdasarkan NIK VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnKeluar);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                tabMode.getValueAt(r,1).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Harian Pengadaan Ipsrs"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariBPJSNik.jasper","report","[ Pencarian Peserta BPJS Berdasarkan NIK/No.KTP ]",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSNik dialog = new BPJSNik(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ScrollPane Scroll;
    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    /**
     *
     * @param nik
     */
    public void tampil(String nik) {
        try {
            cekViaBPJS.tampil(nik);
            if(cekViaBPJS.informasi.equals("OK")){
                Valid.tabelKosong(tabMode);          
                tabMode.addRow(new Object[]{
                    "Nama",": "+cekViaBPJS.nama
                });
                tabMode.addRow(new Object[]{
                    "NIK",": "+cekViaBPJS.nik
                });
                tabMode.addRow(new Object[]{
                    "Nomor Kartu",": "+cekViaBPJS.noKartu
                });
                tabMode.addRow(new Object[]{
                    "Nomor MR",": "+cekViaBPJS.mrnoMR
                });
                tabMode.addRow(new Object[]{
                    "Nomor Telp",": "+cekViaBPJS.mrnoTelepon
                });
                tabMode.addRow(new Object[]{
                    "Pisa",": "+cekViaBPJS.pisa
                });
                tabMode.addRow(new Object[]{
                    "Jenis Kelamin",": "+cekViaBPJS.sex.replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")
                });
                tabMode.addRow(new Object[]{
                    "Status Peserta",":"
                });
                tabMode.addRow(new Object[]{
                    "       Keterangan",": "+cekViaBPJS.statusPesertaketerangan
                });
                tabMode.addRow(new Object[]{
                    "       Kode",": "+cekViaBPJS.statusPesertakode
                });
                tabMode.addRow(new Object[]{
                    "Jenis Peserta",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Jenis Peserta",": "+cekViaBPJS.jenisPesertakode
                });
                tabMode.addRow(new Object[]{
                    "       Nama Jenis Peserta",": "+cekViaBPJS.jenisPesertaketerangan
                });            
                tabMode.addRow(new Object[]{
                    "Kelas Tanggungan",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Kelas",": "+cekViaBPJS.hakKelaskode
                });
                tabMode.addRow(new Object[]{
                    "       Nama Kelas",": "+cekViaBPJS.hakKelasketerangan
                });
                tabMode.addRow(new Object[]{
                    "Provider Umum",":"
                });
                tabMode.addRow(new Object[]{
                    "       Kode Provider",": "+cekViaBPJS.provUmumkdProvider
                });                
                tabMode.addRow(new Object[]{
                    "       Nama Provider",": "+cekViaBPJS.provUmumnmProvider
                });
                tabMode.addRow(new Object[]{
                    "Tanggal Cetak Kartu",": "+cekViaBPJS.tglCetakKartu
                });
                tabMode.addRow(new Object[]{
                    "Tanggal Lahir",": "+cekViaBPJS.tglLahir
                });
                tabMode.addRow(new Object[]{
                    "Tanggal TAT",": "+cekViaBPJS.tglTAT
                });
                tabMode.addRow(new Object[]{
                    "Tanggal TMT",": "+cekViaBPJS.tglTMT
                });
                tabMode.addRow(new Object[]{
                    "Umur",":"
                });
                tabMode.addRow(new Object[]{
                    "       Umur Saat Pelayanan",": "+cekViaBPJS.umurumurSaatPelayanan
                });
                tabMode.addRow(new Object[]{
                    "       Umur Sekarang",": "+cekViaBPJS.umurumurSekarang.replaceAll("tahun ,","Th ").replaceAll("bulan ,","Bl ").replaceAll("hari","Hr")
                });
                tabMode.addRow(new Object[]{
                    "Informasi",":"
                });
                tabMode.addRow(new Object[]{
                    "       Dinsos",": "+cekViaBPJS.informasidinsos
                });
                tabMode.addRow(new Object[]{
                    "       No.SKTM",": "+cekViaBPJS.informasinoSKTM
                });
                tabMode.addRow(new Object[]{
                    "       Prolanis PRB",": "+cekViaBPJS.informasiprolanisPRB
                });
                tabMode.addRow(new Object[]{
                    "COB",":"
                });
                tabMode.addRow(new Object[]{
                    "       Nama Asuransi",": "+cekViaBPJS.cobnmAsuransi
                });
                tabMode.addRow(new Object[]{
                    "       No Asuransi",": "+cekViaBPJS.cobnoAsuransi
                });
                tabMode.addRow(new Object[]{
                    "       Tanggal TAT",": "+cekViaBPJS.cobtglTAT
                });
                tabMode.addRow(new Object[]{
                    "       Tanggal TMT",": "+cekViaBPJS.cobtglTMT
                });                
            }else {
                JOptionPane.showMessageDialog(null,cekViaBPJS.informasi);                
            }     
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
        }
    }    
 
}
