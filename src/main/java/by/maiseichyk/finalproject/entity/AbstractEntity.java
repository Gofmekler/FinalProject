package by.maiseichyk.finalproject.entity;

public abstract class AbstractEntity {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return id == abstractEntity.id;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result * 31 + Long.hashCode(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=id").append("}");
        return sb.toString();
    }
}

