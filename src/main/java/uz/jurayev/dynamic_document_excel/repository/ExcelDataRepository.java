package uz.jurayev.dynamic_document_excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jurayev.dynamic_document_excel.domain.ExcelData;

public interface ExcelDataRepository extends JpaRepository<ExcelData, Integer> {
}
