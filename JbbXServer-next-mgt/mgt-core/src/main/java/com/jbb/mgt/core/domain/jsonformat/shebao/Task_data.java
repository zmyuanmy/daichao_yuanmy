package com.jbb.mgt.core.domain.jsonformat.shebao;

import java.util.List;

public class Task_data {

    private User_info user_info;
    private List<Unemployment_insurance> unemployment_insurance;
    private List<Maternity_insurance> maternity_insurance;
    private List<Medical_insurance> medical_insurance;
    private List<Endowment_insurance> endowment_insurance;
    private List<Accident_insurance> accident_insurance;
    private List<Medical_consumption> medical_consumption;
    private Endowment_overview endowment_overview;

    public Endowment_overview getEndowment_overview() {
        return endowment_overview;
    }

    public void setEndowment_overview(Endowment_overview endowment_overview) {
        this.endowment_overview = endowment_overview;
    }

    public User_info getUser_info() {
        return user_info;
    }

    public void setUser_info(User_info user_info) {
        this.user_info = user_info;
    }

    public List<Unemployment_insurance> getUnemployment_insurance() {
        return unemployment_insurance;
    }

    public void setUnemployment_insurance(List<Unemployment_insurance> unemployment_insurance) {
        this.unemployment_insurance = unemployment_insurance;
    }

    public List<Maternity_insurance> getMaternity_insurance() {
        return maternity_insurance;
    }

    public void setMaternity_insurance(List<Maternity_insurance> maternity_insurance) {
        this.maternity_insurance = maternity_insurance;
    }

    public List<Medical_insurance> getMedical_insurance() {
        return medical_insurance;
    }

    public void setMedical_insurance(List<Medical_insurance> medical_insurance) {
        this.medical_insurance = medical_insurance;
    }

    public List<Endowment_insurance> getEndowment_insurance() {
        return endowment_insurance;
    }

    public void setEndowment_insurance(List<Endowment_insurance> endowment_insurance) {
        this.endowment_insurance = endowment_insurance;
    }

    public List<Accident_insurance> getAccident_insurance() {
        return accident_insurance;
    }

    public void setAccident_insurance(List<Accident_insurance> accident_insurance) {
        this.accident_insurance = accident_insurance;
    }

    public List<Medical_consumption> getMedical_consumption() {
        return medical_consumption;
    }

    public void setMedical_consumption(List<Medical_consumption> medical_consumption) {
        this.medical_consumption = medical_consumption;
    }
}
