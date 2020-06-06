package com.ubb.cloud.conference.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "rooms", schema = "conference")
public class Room {
    private Integer id;
    private String name;
    private Integer places;
    private Collection<Talk> talks;

    public Room() {}

    public Room(Integer id, String name, Integer places) {
        this.id = id;
        this.name = name;
        this.places = places;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "places", nullable = true)
    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL
    )
    public Collection<Talk> getTalks() {
        return talks;
    }

    public void setTalks(Collection<Talk> talks) {
        this.talks = talks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(getId(), room.getId()) &&
                Objects.equals(getName(), room.getName()) &&
                Objects.equals(getPlaces(), room.getPlaces()) &&
                Objects.equals(getTalks(), room.getTalks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPlaces(), getTalks());
    }

    public RoomDTO toRoomDTO() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(getName());
        roomDTO.setPlaces(getPlaces());
        return roomDTO;
    }
}