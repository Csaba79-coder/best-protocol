package com.csaba79coder.bestprotocol.model.representative.entity;

import com.csaba79coder.bestprotocol.model.base.entity.Auditable;
import com.csaba79coder.bestprotocol.model.value.Availability;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "representative")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "availability != 'DELETED'")
public class Representative extends Auditable {

    @Column(name = "name")
    private String name;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "image",length = Integer.MAX_VALUE, nullable = false)
    private byte[] image;

    @Column(name = "note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability = Availability.AVAILABLE;
}
