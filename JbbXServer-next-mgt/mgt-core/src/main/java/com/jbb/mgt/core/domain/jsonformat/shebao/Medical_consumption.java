package com.jbb.mgt.core.domain.jsonformat.shebao;

public class Medical_consumption {

    private int personal_account_spending;
    private String balance_date;
    private String medical_institution;
    private String type;

    public int getPersonal_account_spending() {
        return personal_account_spending;
    }

    public void setPersonal_account_spending(int personal_account_spending) {
        this.personal_account_spending = personal_account_spending;
    }

    public String getBalance_date() {
        return balance_date;
    }

    public void setBalance_date(String balance_date) {
        this.balance_date = balance_date;
    }

    public String getMedical_institution() {
        return medical_institution;
    }

    public void setMedical_institution(String medical_institution) {
        this.medical_institution = medical_institution;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
