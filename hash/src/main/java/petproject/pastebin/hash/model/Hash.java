package petproject.pastebin.hash.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name="hash", indexes = @Index(name = "hash_and_busy_index", columnList = "value, is_busy"))
public class Hash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String value = null;

    Boolean isBusy = false;
    @CreationTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;

    public Hash() {

    }

    public Hash(String value, Boolean busy) {
        this.value = value;
        this.isBusy = busy;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Hash hash = (Hash) o;
        return getId() != null && Objects.equals(getId(), hash.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
