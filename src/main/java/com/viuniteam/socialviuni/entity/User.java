package com.viuniteam.socialviuni.entity;

import lombok.Data;

import javax.persistence.*;
import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
public class User extends BaseEntity{

    @Column(unique = true, columnDefinition = "varchar(15)",updatable = false)
    private String username;

    @Column(columnDefinition = "nvarchar(255)")
    private String password;

    @Column(unique = true, columnDefinition = "nvarchar(50)")
    private String email;

    @Column
    private boolean active;

    @Column
    private boolean gender;

    @Column(columnDefinition = "nvarchar(25)")
    private String lastName;

    @Column(columnDefinition = "nvarchar(15)")
    private String firstName;

    @Column
    private LocalDate dob;

    @Column(columnDefinition = "nvarchar(160)")
    private String bio;

    @OneToOne
    @JoinTable(name = "user_hometown",joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "address_id",nullable = false))
    private Address homeTown;


    @OneToOne
    @JoinTable(name = "user_currentcity",joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "address_id",nullable = false))
    private Address currentCity;


    @ManyToMany
    @JoinTable(name = "user_avatar",joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> avatarImage = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "user_cover",joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id",nullable = false))
    private List<Image> coverImage = new ArrayList<>();


    @OneToMany
    private List<Friend> friends = new ArrayList<>();

    @OneToMany
    private List<FriendRequest> friendRequests = new ArrayList<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "permission",
            joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id",nullable = false))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    private List<Follower> followers = new ArrayList<>();

    @OneToMany
    private List<Following> followings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Share> shares= new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<GroupMessage> groupMessageList = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();


    @OneToMany(mappedBy = "userSource")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Browser> browsers = new ArrayList<>();
}
