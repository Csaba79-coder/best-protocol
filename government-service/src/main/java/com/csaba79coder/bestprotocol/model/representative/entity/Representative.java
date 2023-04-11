package com.csaba79coder.bestprotocol.model.representative.entity;

import com.csaba79coder.bestprotocol.model.base.entity.Auditable;
import com.csaba79coder.bestprotocol.model.government.entity.Government;
import com.csaba79coder.bestprotocol.model.value.Availability;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "representative")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "availability != 'DELETED'")
public class Representative extends Auditable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "government_id")
    private Government government;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "representative")
    private List<RepresentativeTranslation> fieldTranslations = new ArrayList<>();

    // TODO create a previous job title (store in a set for avoiding the duplication!)
    /*@Column(name= "previous_job_title")
    private List<String> previousJobTitles = new ArrayList<>();*/

    // TODO refactor logic to remove the langShortName from here!
    @Column(name = "language_short_name")
    private String languageShortName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "image",length = Integer.MAX_VALUE, nullable = false)
    private byte[] image;


    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability = Availability.AVAILABLE;
}
