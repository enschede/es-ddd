package nl.marcenschede.tests.elastic.base.domains;

public enum DomainEntityType {
    ORDER("order"),
    FACTUUR("factuur");

    private String type;

    DomainEntityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
