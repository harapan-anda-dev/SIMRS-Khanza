package model;


import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author RSUI HA
 */
public class BookingRegistrasi {
private Date tanggal_booking;
private String jam_booking;
private String no_rkm_medis;
private Date tanggal_periksa;
private String kd_dokter;
private String kd_poli;
private String no_reg;
private String kd_pj;
private String limit_reg;
private DateTime waktu_kunjungan;
private String status;

    /**
     *
     * @return
     */
    public Date getTanggal_booking() {
        return tanggal_booking;
    }

    /**
     *
     * @param tanggal_booking
     */
    public void setTanggal_booking(Date tanggal_booking) {
        this.tanggal_booking = tanggal_booking;
    }

    /**
     *
     * @return
     */
    public String getJam_booking() {
        return jam_booking;
    }

    /**
     *
     * @param jam_booking
     */
    public void setJam_booking(String jam_booking) {
        this.jam_booking = jam_booking;
    }

    /**
     *
     * @return
     */
    public String getNo_rkm_medis() {
        return no_rkm_medis;
    }

    /**
     *
     * @param no_rkm_medis
     */
    public void setNo_rkm_medis(String no_rkm_medis) {
        this.no_rkm_medis = no_rkm_medis;
    }

    /**
     *
     * @return
     */
    public Date getTanggal_periksa() {
        return tanggal_periksa;
    }

    /**
     *
     * @param tanggal_periksa
     */
    public void setTanggal_periksa(Date tanggal_periksa) {
        this.tanggal_periksa = tanggal_periksa;
    }

    /**
     *
     * @return
     */
    public String getKd_dokter() {
        return kd_dokter;
    }

    /**
     *
     * @param kd_dokter
     */
    public void setKd_dokter(String kd_dokter) {
        this.kd_dokter = kd_dokter;
    }

    /**
     *
     * @return
     */
    public String getKd_poli() {
        return kd_poli;
    }

    /**
     *
     * @param kd_poli
     */
    public void setKd_poli(String kd_poli) {
        this.kd_poli = kd_poli;
    }

    /**
     *
     * @return
     */
    public String getNo_reg() {
        return no_reg;
    }

    /**
     *
     * @param no_reg
     */
    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    /**
     *
     * @return
     */
    public String getKd_pj() {
        return kd_pj;
    }

    /**
     *
     * @param kd_pj
     */
    public void setKd_pj(String kd_pj) {
        this.kd_pj = kd_pj;
    }

    /**
     *
     * @return
     */
    public String getLimit_reg() {
        return limit_reg;
    }

    /**
     *
     * @param limit_reg
     */
    public void setLimit_reg(String limit_reg) {
        this.limit_reg = limit_reg;
    }

    /**
     *
     * @return
     */
    public DateTime getWaktu_kunjungan() {
        return waktu_kunjungan;
    }

    /**
     *
     * @param waktu_kunjungan
     */
    public void setWaktu_kunjungan(DateTime waktu_kunjungan) {
        this.waktu_kunjungan = waktu_kunjungan;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


}