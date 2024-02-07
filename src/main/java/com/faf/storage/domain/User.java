package com.faf.storage.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 191)
    private String email;

    @Column(name = "image_url", nullable = false, length = 256)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean activated;

    @Column(name = "lang_key", nullable = false, length = 10)
    private String langKey;

    @Column(name = "activation_key", nullable = false, length = 20)
    private String activationKey;

    @Column(name = "reset_key", nullable = false, length = 20)
    private String resetKey;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "reset_date", nullable = false)
    private Instant resetDate;

    @Column(name = "last_modified_by", nullable = false, length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date", nullable = false)
    private Instant lastModifiedDate;

    @OneToOne(cascade = CascadeType.ALL)
    private UserReservation userReservation;

    @OneToMany(cascade = CascadeType.ALL)
    private List<StorageFile> storageFiles;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_name")
    )
    private Set<Authority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(passwordHash, user.passwordHash) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(imageUrl, user.imageUrl) && Objects.equals(activated, user.activated) && Objects.equals(langKey, user.langKey) && Objects.equals(activationKey, user.activationKey) && Objects.equals(resetKey, user.resetKey) && Objects.equals(createdBy, user.createdBy) && Objects.equals(createdDate, user.createdDate) && Objects.equals(resetDate, user.resetDate) && Objects.equals(lastModifiedBy, user.lastModifiedBy) && Objects.equals(lastModifiedDate, user.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, passwordHash, firstName, lastName, email, imageUrl, activated, langKey, activationKey, resetKey, createdBy, createdDate, resetDate, lastModifiedBy, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activated=" + activated +
                ", langKey='" + langKey + '\'' +
                ", activationKey='" + activationKey + '\'' +
                ", resetKey='" + resetKey + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", resetDate=" + resetDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
