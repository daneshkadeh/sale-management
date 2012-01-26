package com.s3s.ssm.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.operator.Stall;

@Entity
@Table(name = "s_basic_information")
public class BasicInformation extends AbstractCodeOLObject {
    // information of company
    private String companyName;
    private String agent;
    private String position;
    private UploadFile logo;
    private String companyAddress;
    private String tel;
    private String fax;
    private String website;
    private String email;
    // information of bank
    private BankAccount usdBankAccount;
    private BankAccount vndBankAccount;
    // private String bankName;
    // private String bankAddress;
    // private String usdAcctNumber;
    // private String vndAcctNumber;
    // private String beneficeName;
    // general parameter
    private SCurrency defCurrency; // default currency
    private Integer defDetailInvNum; // number of rows on a invoice
    private Integer defPageRowNum; // number of rows on a page
    private String defPaymentMethod;
    // private Integer enableChangeInvDate; //0: not accept, 1: accept when inserting, 0: accept when creating
    private Stall defStall;
    // rule of code generation
    private String orderInvCodeRule;
    private String salesInvCodeRule;
    private String salesRefundInvCodeRule;
    private String purInvCodeRule;
    private String purRefundInvCodeRule;
    private String sponContractCodeRule;
    private String movementInvCodeRule;
    private String exportInvCodeRule;
    private String importInvCodeRule;
    private String paymentBillCodeRule;
    private String receiptsCodeRule;
    private String promotionCodeRule;
    // the path backup data
    private String backupPath;
    // sold on credit
    private Integer sellOnCredit; // 0: cho phep ban am, 1: hoi neu ban am, 2: ko cho phep ban am
    // setting decimal format
    private Integer digitAfterCommaQuan = 5;
    private Integer digitAfterCommaPrice = 5;
    private Integer digitAfterCommaRate = 2;
    private String thousandsSeparator = ".";
    private String oddSeparator = ",";

    @Column(name = "company_name", length = 250)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "agent", length = 250)
    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Column(name = "position", length = 100)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @ManyToOne
    @JoinColumn(name = "upload_file_id")
    public UploadFile getLogo() {
        return logo;
    }

    public void setLogo(UploadFile logo) {
        this.logo = logo;
    }

    @Column(name = "company_address", length = 250)
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Column(name = "tel", length = 20)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "fax", length = 20)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "website", length = 100)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "usd_bank_acct_id")
    public BankAccount getUsdBankAccount() {
        return usdBankAccount;
    }

    public void setUsdBankAccount(BankAccount usdBankAccount) {
        this.usdBankAccount = usdBankAccount;
    }

    @ManyToOne
    @JoinColumn(name = "vnd_bank_acct_id")
    public BankAccount getVndBankAccount() {
        return vndBankAccount;
    }

    public void setVndBankAccount(BankAccount vndBankAccount) {
        this.vndBankAccount = vndBankAccount;
    }

    @ManyToOne
    @JoinColumn(name = "def_currency_id", nullable = false)
    public SCurrency getDefCurrency() {
        return defCurrency;
    }

    public void setDefCurrency(SCurrency defCurrency) {
        this.defCurrency = defCurrency;
    }

    @Column(name = "def_detail_inv_num", length = 3)
    public Integer getDefDetailInvNum() {
        return defDetailInvNum;
    }

    public void setDefDetailInvNum(Integer defDetailInvNum) {
        this.defDetailInvNum = defDetailInvNum;
    }

    @Column(name = "def_page_row_num", length = 3)
    public Integer getDefPageRowNum() {
        return defPageRowNum;
    }

    public void setDefPageRowNum(Integer defPageRowNum) {
        this.defPageRowNum = defPageRowNum;
    }

    @Column(name = "def_payment_method", length = 3)
    public String getDefPaymentMethod() {
        return defPaymentMethod;
    }

    public void setDefPaymentMethod(String defPaymentMethod) {
        this.defPaymentMethod = defPaymentMethod;
    }

    @ManyToOne
    @JoinColumn(name = "def_stall_id")
    public Stall getDefStall() {
        return defStall;
    }

    public void setDefStall(Stall defStall) {
        this.defStall = defStall;
    }

    @Column(name = "order_inv_code_rule", length = 50)
    public String getOrderInvCodeRule() {
        return orderInvCodeRule;
    }

    public void setOrderInvCodeRule(String orderInvCodeRule) {
        this.orderInvCodeRule = orderInvCodeRule;
    }

    @Column(name = "sales_inv_code_rule", length = 50)
    public String getSalesInvCodeRule() {
        return salesInvCodeRule;
    }

    public void setSalesInvCodeRule(String salesInvCodeRule) {
        this.salesInvCodeRule = salesInvCodeRule;
    }

    @Column(name = "sales_refund_inv_code_rule", length = 50)
    public String getSalesRefundInvCodeRule() {
        return salesRefundInvCodeRule;
    }

    public void setSalesRefundInvCodeRule(String salesRefundInvCodeRule) {
        this.salesRefundInvCodeRule = salesRefundInvCodeRule;
    }

    @Column(name = "pur_inv_code_rule", length = 50)
    public String getPurInvCodeRule() {
        return purInvCodeRule;
    }

    public void setPurInvCodeRule(String purInvCodeRule) {
        this.purInvCodeRule = purInvCodeRule;
    }

    @Column(name = "pur_refund_inv_code_rule", length = 50)
    public String getPurRefundInvCodeRule() {
        return purRefundInvCodeRule;
    }

    public void setPurRefundInvCodeRule(String purRefundInvCodeRule) {
        this.purRefundInvCodeRule = purRefundInvCodeRule;
    }

    @Column(name = "spon_contract_code_rule", length = 50)
    public String getSponContractCodeRule() {
        return sponContractCodeRule;
    }

    public void setSponContractCodeRule(String sponContractCodeRule) {
        this.sponContractCodeRule = sponContractCodeRule;
    }

    @Column(name = "movement_inv_code_rule", length = 50)
    public String getMovementInvCodeRule() {
        return movementInvCodeRule;
    }

    public void setMovementInvCodeRule(String movementInvCodeRule) {
        this.movementInvCodeRule = movementInvCodeRule;
    }

    @Column(name = "export_inv_code_rule", length = 50)
    public String getExportInvCodeRule() {
        return exportInvCodeRule;
    }

    public void setExportInvCodeRule(String exportInvCodeRule) {
        this.exportInvCodeRule = exportInvCodeRule;
    }

    @Column(name = "import_inv_code_rule", length = 50)
    public String getImportInvCodeRule() {
        return importInvCodeRule;
    }

    public void setImportInvCodeRule(String importInvCodeRule) {
        this.importInvCodeRule = importInvCodeRule;
    }

    @Column(name = "payment_bill_code_rule", length = 50)
    public String getPaymentBillCodeRule() {
        return paymentBillCodeRule;
    }

    public void setPaymentBillCodeRule(String paymentBillCodeRule) {
        this.paymentBillCodeRule = paymentBillCodeRule;
    }

    @Column(name = "receipt_code_rule", length = 50)
    public String getReceiptsCodeRule() {
        return receiptsCodeRule;
    }

    public void setReceiptsCodeRule(String receiptsCodeRule) {
        this.receiptsCodeRule = receiptsCodeRule;
    }

    @Column(name = "promotion_code_rule", length = 50)
    public String getPromotionCodeRule() {
        return promotionCodeRule;
    }

    public void setPromotionCodeRule(String promotionCodeRule) {
        this.promotionCodeRule = promotionCodeRule;
    }

    @Column(name = "backup_path", length = 250)
    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    @Column(name = "sell_on_credit", length = 1)
    public Integer getSellOnCredit() {
        return sellOnCredit;
    }

    public void setSellOnCredit(Integer sellOnCredit) {
        this.sellOnCredit = sellOnCredit;
    }

    @Column(name = "digit_adter_comma_quan", length = 2)
    public Integer getDigitAfterCommaQuan() {
        return digitAfterCommaQuan;
    }

    public void setDigitAfterCommaQuan(Integer digitAfterCommaQuan) {
        this.digitAfterCommaQuan = digitAfterCommaQuan;
    }

    @Column(name = "digit_adter_comma_price", length = 2)
    public Integer getDigitAfterCommaPrice() {
        return digitAfterCommaPrice;
    }

    public void setDigitAfterCommaPrice(Integer digitAfterCommaPrice) {
        this.digitAfterCommaPrice = digitAfterCommaPrice;
    }

    @Column(name = "digit_adter_comma_rate", length = 2)
    public Integer getDigitAfterCommaRate() {
        return digitAfterCommaRate;
    }

    public void setDigitAfterCommaRate(Integer digitAfterCommaRate) {
        this.digitAfterCommaRate = digitAfterCommaRate;
    }

    @Column(name = "thousands_Separator", length = 1)
    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
    }

    @Column(name = "odd_Separator", length = 1)
    public String getOddSeparator() {
        return oddSeparator;
    }

    public void setOddSeparator(String oddSeparator) {
        this.oddSeparator = oddSeparator;
    }
}
