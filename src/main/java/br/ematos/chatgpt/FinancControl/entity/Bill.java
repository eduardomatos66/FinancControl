package br.ematos.chatgpt.FinancControl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_who")
    private Date documentDate;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(name = "tags")
    @ManyToMany
    private List<Tag> tags;

    @Column(name = "items")
    @ManyToMany
    private List<BillItem> items;

    @Column(name = "total")
    private Float total;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "picture")
    private byte[] billPicture;
}
