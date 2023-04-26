package com.csaba79coder.bestprotocol.model.government.entity;

import com.csaba79coder.bestprotocol.model.base.entity.IdentifierLong;
import com.csaba79coder.bestprotocol.model.representative.entity.Representative;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "government")
public class Government extends IdentifierLong {

    // https://kormany.hu/miniszteriumok

    @OneToMany(mappedBy = "government", cascade = CascadeType.ALL, orphanRemoval = true)
    // @OrderBy("name")
    // @OrderBy("government.id")
    private Set<Representative> representatives = new HashSet<>();
}
