package br.com.taian.deal.model;

import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.util.GeneratorIdentifierEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="deal")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
public class Deal extends GeneratorIdentifierEntity<Long> implements Serializable {


    @Column
    @NotNull
    private String model;


    @Column
    @NotNull
    private String make;


    @Column
    @NotNull
    private Double price;

    @Column
    private DealStatus dealStatus = DealStatus.ACTIVE;

}
