package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//import org.example.frontend.ElementType;

@Getter
@Setter
@AllArgsConstructor

public class Card {
    /* IST NOCH EINE ÜBERLEGUNG
    @JsonAlias({"card_id"})
    @Setter(AccessLevel.PRIVATE)
    String card_id;
    @JsonAlias({"user_id"})
    @Setter(AccessLevel.PRIVATE)
    int user_id;
    @JsonAlias({"name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"damage"})
    @Setter(AccessLevel.PRIVATE)
    float damage;
    @JsonAlias({"element_type"})
    @Setter(AccessLevel.PRIVATE)
    int element_type;
    @JsonAlias({"isInDeck"})
    @Setter(AccessLevel.PRIVATE)
    boolean isInDeck = false;
*/
}
