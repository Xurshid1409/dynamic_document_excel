package uz.jurayev.dynamic_document_excel.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.io.Serializable;
import java.util.Map;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "excel_data")
public class ExcelData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Object, Object> data;

    @Lob
    private String text;
}
