package ppvis.lab2s4.model;

public class Student {
    private FullName fullName;
    private String group;

    private int passBecauseOfDisease;
    private int passForOtherReason;
    private int passWithout;
    private int total;

    public Student(FullName snp, String group, int passBecauseOfDisease, int passForOtherReason, int passWithout, int total) {
        this.fullName = snp;
        this.group = group;
        this.passBecauseOfDisease = passBecauseOfDisease;
        this.passForOtherReason = passForOtherReason;
        this.passWithout = passWithout;
        this.total = this.passBecauseOfDisease + this.passForOtherReason + this.passWithout;

    }


    public FullName getSnp() {
        return fullName;
    }

    public String getSurname() {
        return fullName.getSurname();
    }

    public void setSnp(FullName snp) {
        this.fullName = snp;
    }

    public String getAlignSnp() {
        return fullName.getSurname() + " " + fullName.getName() + " " + fullName.getPatronym();
    }

    public void setAlignSnp(String alignSnp) {
        this.fullName = new FullName(alignSnp);
    }

    public String getGroup() {
        return group;
    }

    public void setPassBecauseOfDisease(int passBecauseOfDisease) {
        this.passBecauseOfDisease = passBecauseOfDisease;
    }

    public int getPassBecauseOfDisease() {
        return passBecauseOfDisease;
    }

    public void setPassForOtherReason(int passForOtherReason) {
        this.passForOtherReason = passForOtherReason;
    }

    public int getPassForOtherReason() {
        return passForOtherReason;
    }

    public int getPassWithout() {
        return passWithout;
    }

    public void setPassWithout(int passWithout) {
        this.passWithout = passWithout;
    }

    public int getTotal() {


        return total;
    }

    public void setTotal(int passWithout) {
        this.passWithout = passWithout;
    }

    public void setGroup(String group) {
        this.group = group;
    }


}
