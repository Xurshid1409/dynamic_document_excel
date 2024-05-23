package uz.jurayev.dynamic_document_excel.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.jurayev.dynamic_document_excel.service.ExcelService;
import java.io.IOException;

@RestController
@RequestMapping("/api/excel/")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @PostMapping(value = "importFromExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(description = "Импорт записей с excel")
    public String excelToObjects(@RequestParam("file") MultipartFile file) throws IOException {
        return excelService.excelToObjects(file.getInputStream());
    }

    @PostMapping(value = "exportToExcel")
    @Operation(description = "Экспорт записей в excel")
    public ResponseEntity<Resource> objectsToExcel(Object[] objects) {

        String filename = "report.xlsx";
        InputStreamResource resource = new InputStreamResource(excelService.objectsToExcel(objects));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource);
    }
}
