package com.ubb.cloud.conference.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "talks", schema = "conference")
public class Talk {
    private int id;
    private Article article;
    private Timestamp startTime;
    private Timestamp endTime;
    private Room room;
    private Collection<UserDetails> attendees;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id", nullable=false)
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Talk)) return false;
        Talk talk = (Talk) o;
        return getId() == talk.getId() &&
                Objects.equals(getArticle(), talk.getArticle()) &&
                Objects.equals(getStartTime(), talk.getStartTime()) &&
                Objects.equals(getEndTime(), talk.getEndTime()) &&
                Objects.equals(getRoom(), talk.getRoom()) &&
                Objects.equals(getAttendees(), talk.getAttendees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArticle(), getStartTime(), getEndTime(), getRoom(), getAttendees());
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "talks_participants",
            joinColumns = { @JoinColumn(name = "talk_id") },
            inverseJoinColumns = { @JoinColumn(name = "participant_id") }
    )
    public Collection<UserDetails> getAttendees() {
        return attendees;
    }

    public void setAttendees(Collection<UserDetails> attendees) {
        this.attendees = attendees;
    }

    public void addAttendee(UserDetails attendee) {
        if(attendees == null) {
            attendees = new ArrayList();
        }
        attendees.add(attendee);
    }

    public void removeAttendee(UserDetails attendee) {
        attendees.remove(attendee);
    }

    public TalkDTO toTalkDTO() {
        TalkDTO talkDTO = new TalkDTO();

        talkDTO.setId(getId());
        talkDTO.setTitle(getArticle().getTitle());
        talkDTO.setDomain(getArticle().getDomain());
        talkDTO.setAuthor(getArticle().getAuthor().toUserDTO());

        return talkDTO;
    }

    public TalkDetailDTO toTalkDetailDTO() {
        TalkDetailDTO talkDetailDTO = new TalkDetailDTO();
        talkDetailDTO.setId(getId());
        talkDetailDTO.setEndTime(getEndTime());
        talkDetailDTO.setStartTime(getStartTime());
        talkDetailDTO.setTitle(getArticle().getTitle());
        talkDetailDTO.setRoom(getRoom().toRoomDTO());
        return talkDetailDTO;
    }
}