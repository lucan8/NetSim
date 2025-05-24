package models_data;
public abstract class Entity {
    public Integer id;

    public Entity() {
        this.id = null;
    }

    public Entity(Integer id) {
        this.id = id;
    }

    // Maybe in the future
    // public abstract void print();
}