package com.ascending.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.codec.digest.DigestUtils;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    public User(){}
    public User(String name, String password, String firstName, String lastName, String email){
        this.name = name;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    @JsonIgnore
    private List<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Image> images;

    public Long getId(){return this.id;}

    public void setId(Long id) {this.id = id;}

    public String getName(){return this.name;}

    public void setName(String name){this.name = name;}

    public String getPassword(){return this.password;}

    public void setPassword(String password){this.password = DigestUtils.md5Hex(password.trim());}

    public String getFirstName(){return this.firstName;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return this.lastName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public void setRole(List<Role> roles){this.roles = roles;}

    public List<Role> getRole(){return this.roles;}

    public Set<Image> getImages(){
        try {
            int size = images.size();
        }catch (Exception e){
            return null;
        }
        return this.images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
